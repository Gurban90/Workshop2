/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Gerben
 */
public class PasswordHasher {

    private SecureRandom random;
    private String password;
    private String encryptedPass;
    private String encryptedTotal;

    private byte[] generateSalt() {
        this.random = new SecureRandom();
        byte bytes[] = new byte[20];
        this.random.nextBytes(bytes);
        return bytes;
    }

    public String makeSaltedPasswordHash(String password) {
        byte[] salt = generateSalt();
        String saltString = new String(salt);
        this.password = password + saltString;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(this.password.getBytes());
            this.encryptedPass = new String(messageDigest.digest());
            this.encryptedTotal = this.encryptedPass + saltString;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return this.encryptedTotal;
    }

    public String hasher(String password) {
        this.password = password;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            this.encryptedPass = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return this.encryptedPass;
    }

    /*
    public static void main(String[] args) {
        PasswordHasher tester = new PasswordHasher();
        String wachtwoord = tester.makeSaltedPasswordHash("password");
        String wachtwoord2 = tester.hasher("password");

    }
    */
}
