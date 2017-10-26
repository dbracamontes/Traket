/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traket.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * http://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
 *
 * @author daniel
 * @version 1.0
 */
public class PasswordEncryptionService {

    /**
     *
     * @param attemptedPassword
     * @param encryptedPassword
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) {
        // Encrypt the clear-text password using the same salt that was used to      
        // encrypt the original password
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);

        // Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    /**
     *
     * @param password
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public byte[] getEncryptedPassword(String password, byte[] salt) {
        // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
        byte[] encryptepPassword = null;

        try {

            // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
            String algorithm = "PBKDF2WithHmacSHA1";
            // SHA-1 generates 160 bit hashes, so that's what makes sense here

            int derivedKeyLength = 160;
            // Pick an iteration count that works for you. The NIST recommends at

            // least 1,000 iterations:
            // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
            // iOS 4.x reportedly uses 10,000:
            // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
            int iterations = 20000;

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
            SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

            encryptepPassword = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e.getMessage());
        }

        return encryptepPassword;
    }

    /**
     *
     * @return @throws NoSuchAlgorithmException
     */
    public byte[] generateSalt() {
        byte[] salt = null;

        try {
            // VERY important to use SecureRandom instead of just Random
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
            salt = new byte[8];
            random.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return salt;
    }
}
