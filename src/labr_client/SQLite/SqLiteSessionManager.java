/* 
 * Copyright (C) 2016 De Mey Matthias
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package labr_client.SQLite;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import labr_client.Public.*;

/**
 *
 * @author De Mey Matthias
 */
public class SqLiteSessionManager {

    public boolean logInAttempt(String userName, String md5Pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PublicVars.setMd5pass(MD5(md5Pass));

        List<String[]> lines = PublicVars.getQueries().selectUserInfoPass((md5Pass), userName);
//        try {
//            encryption.encode(MD5("test"));
//        } catch (GeneralSecurityException ex) {
//
//            Logger.getLogger(SqLiteSessionManager.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (!lines.isEmpty()) {
            try {
                if (encryption.decodeBase64Aes(lines.get(0)[10]).equals(PublicVars.getMd5pass())) {
                    for (String[] values : lines) {
                        PublicVars.setUsername(values[0]);
                        PublicVars.setUserID(values[1]);
                    }
                    PublicVars.setUserData(lines.get(0));
                    return true;
                } else {
                    return false;
                }
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(SqLiteSessionManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } else {
            return false;
        }

    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String MD5(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }
    

}
