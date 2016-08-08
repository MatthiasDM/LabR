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
        PublicVars.setMd5pass(stringToMD5(md5Pass));
       
//        String statement = "Select * from Gebruikers WHERE Wachtwoord LIKE '" + stringToMD5(md5Pass) + "' AND Gebruikersnaam LIKE '" + userName + "'";
//        String[] attributes = {"Gebruikersnaam", "ID", "firstname","lastname","inss","nihii","keystore", "ehealthpass"};
        List<String[]> lines = PublicVars.getQueries().selectUserInfoPass(stringToMD5(md5Pass), userName);
        
        if (!lines.isEmpty()) {
            for (String[] values : lines) {
                PublicVars.setUsername(values[0]);
                PublicVars.setUserID(values[1]);
            }
            PublicVars.setUserData(lines.get(0));
            return true;
        } else {
            return false;
        }
        
    }
    
    public static String stringToMD5(String string) throws NoSuchAlgorithmException {
        
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(string.getBytes(Charset.forName("UTF-8")), 0, string.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }
    
}
