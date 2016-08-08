/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author labbl
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
