/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labr_client.Public;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Random;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.*;
import org.bouncycastle.crypto.params.*;

 /**
         *
         * @author labbl
         */

public class encryption {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final int AES_NIVBITS = 128; // CBC Initialization Vector (same as cipher block size) [16 bytes]
    //private final int nKeyBits;
    private static KeyParameter aesKey; // computed as needed

    public static String encode(String dec) throws GeneralSecurityException {
        // Generate 128 bits of random data for use as the IV. It is important to use a different IV for
        // each encrypted block of text, to ensure that the same string encrypted by two different people
        // does not give the same encrypted text string - that leads to obvious attack possibilities. Note
        // however that the IV does not need to be kept secret; it is a little bit like a 'salt' for a
        // password, which improves security even when the salt is stored in plaintext in a database or
        // prefixed to the encrypted file.
        byte[] ivData = new byte[AES_NIVBITS / 8]; //Hoe groot is deze array -> 128/8 = 16
        Random r = new Random(); // Note: no  seed here, ie these values are truly random
        r.nextBytes(ivData);

        // Select encryption algorithm and padding : AES with CBC and PCKS#7
        BlockCipherPadding padding = new PKCS7Padding();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), padding);

        // Encrypt the input string using key + iv
        KeyParameter keyParam = getAesKey();
        CipherParameters params = new ParametersWithIV(keyParam, ivData);

        cipher.reset();
        cipher.init(true, params); // first param = encode/decode

        byte[] bytesDec = dec.getBytes(UTF8); // data to decode

        byte[] bytesEnc;
        try {
            int buflen = cipher.getOutputSize(bytesDec.length);
            bytesEnc = new byte[buflen];
            int nBytesEnc = cipher.processBytes(bytesDec, 0, bytesDec.length, bytesEnc, 0);
            nBytesEnc += cipher.doFinal(bytesEnc, nBytesEnc);

            if (nBytesEnc != bytesEnc.length) {
                throw new IllegalStateException("Unexpected behaviour : getOutputSize value incorrect");
            }
        } catch (InvalidCipherTextException e) {
            throw new GeneralSecurityException("encryption failed");
        } catch (RuntimeException e) {
            throw new GeneralSecurityException("encryption failed");
        }

        // Return a base-64-encoded string containing IV + encrypted input string
        byte[] bytesAll = new byte[ivData.length + bytesEnc.length];
        System.arraycopy(ivData, 0, bytesAll, 0, ivData.length);
        System.arraycopy(bytesEnc, 0, bytesAll, ivData.length, bytesEnc.length);
        return new String(Base64.encodeBase64(bytesAll), UTF8);
    }

    /**
     * Decode a string which has first been encrypted with AES, and then
     * base64-encoded.
     */
    public static String decodeBase64Aes(String enc) throws GeneralSecurityException {
        byte[] bytesEnc = Base64.decodeBase64(enc.getBytes(UTF8));

        // Extract the IV, which is stored in the next N bytes
        int nIvBytes = AES_NIVBITS / 8;
        byte[] ivBytes = new byte[nIvBytes];
        System.arraycopy(bytesEnc, 0, ivBytes, 0, nIvBytes);

        // Select encryption algorithm and padding : AES with CBC and PCKS#7.
        // Note that the "encryption strength" (128 or 256 bit key) is set by the KeyParameter object.
        KeyParameter keyParam = getAesKey();
        CipherParameters params = new ParametersWithIV(keyParam, ivBytes);
        BlockCipherPadding padding = new PKCS7Padding();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), padding);

        // Decrypt all bytes that follow the IV
        cipher.reset();
        cipher.init(false, params); // first param = encode/decode

        byte[] bytesDec;

        try {
            int buflen = cipher.getOutputSize(bytesEnc.length - nIvBytes);
            byte[] workingBuffer = new byte[buflen];
            int len = cipher.processBytes(bytesEnc, nIvBytes, bytesEnc.length - nIvBytes, workingBuffer, 0);
            len += cipher.doFinal(workingBuffer, len);

            // Note that getOutputSize returns a number which includes space for "padding" bytes to be stored in.
            // However we don't want these padding bytes; the "len" variable contains the length of the *real* data
            // (which is always less than the return value of getOutputSize.
            bytesDec = new byte[len];
            System.arraycopy(workingBuffer, 0, bytesDec, 0, len);
        } catch (InvalidCipherTextException e) {
            throw new GeneralSecurityException("decode failed");
        } catch (RuntimeException e) {
            throw new GeneralSecurityException("encryption failed");
        }

        // And convert the result to a string
        return new String(bytesDec, UTF8);
    }

    private static KeyParameter getAesKey() throws GeneralSecurityException {
        if (aesKey != null) {
            return aesKey;
        }

        byte[] rawKeyData =  PublicVars.getMd5pass().getBytes(); // somehow obtain the raw bytes of the key
                // Wrap the key data in an appropriate holder type 
        aesKey = new KeyParameter(rawKeyData);
        return aesKey;
    }

}