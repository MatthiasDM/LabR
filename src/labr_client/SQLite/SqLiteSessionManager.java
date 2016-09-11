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
import java.security.*;
import static java.security.MessageDigest.getInstance;
import java.util.List;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import labr_client.Public.*;
import static labr_client.Public.PublicVars.getMd5pass;
import static labr_client.Public.PublicVars.setMd5pass;
import static labr_client.Public.PublicVars.setUserData;
import static labr_client.Public.PublicVars.setUserID;
import static labr_client.Public.PublicVars.setUsername;
import static labr_client.Public.encryption.decodeBase64Aes;

/**
 *
 * @author De Mey Matthias
 */
public class SqLiteSessionManager {

    public boolean logInAttempt(String userName, String md5Pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        setMd5pass(MD5(md5Pass));

        List<String[]> lines = PublicVars.getQueries().selectUserInfoPass((md5Pass), userName);
//        try {
//            encryption.encode(MD5("test"));
//        } catch (GeneralSecurityException ex) {
//
//            Logger.getLogger(SqLiteSessionManager.class.getName()).log(Level.SEVERE, null, ex);
//        }

        if (!lines.isEmpty()) {
            try {
                if (decodeBase64Aes(lines.get(0)[10]).equals(getMd5pass())) {
                    for (String[] values : lines) {
                        setUsername(values[0]);
                        setUserID(values[1]);
                    }
                    setUserData(lines.get(0));
                    return true;
                } else {
                    return false;
                }
            } catch (GeneralSecurityException ex) {
                getLogger(SqLiteSessionManager.class.getName()).log(SEVERE, null, ex);
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
        md = getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }
    

}
