package com.mashup.nnaa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

    public static String encryptPasswordFromPlaintextToLocal(String plaintext) {
        return encryptPhase1(plaintext);
    }

    public static String encryptPasswordFromPlaintextToSignIn(String plaintext) {
        return encryptPhase2(encryptPhase1(plaintext));
    }

    public static String encryptPasswordFromLocalToSignIn(String autoLoginEncryptPassword) {
        return encryptPhase2(autoLoginEncryptPassword);
    }

    /**
     * Encrypt given string. Phase1 = MD5 + SHA256
     * @param src
     * @return Encrypted MD5+Sha256 string
     */
    public static String encryptPhase1(String src) {
        // md5 + sha256
        String md5 = null;
        String md5sha = src;

        try {
            md5 = getMd5Str(src);
            md5sha = getSha256Str(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5sha;
    }

    /**
     * Encrypt given string. Phase2 = SHA + 256
     * @param src
     * @return Encrypted Sha256+MD5 string
     */
    public static String encryptPhase2(String src) {
        // sha256 + md5
        String sha = null;
        String shamd5 = src;
        try {
            sha = getSha256Str(src);
            shamd5 = getMd5Str(sha);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return shamd5;
    }

    private static String getMd5Str(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(str.getBytes());

        byte[] byteData = md5.digest();
        StringBuilder sb = new StringBuilder();

        for (byte byteDatum : byteData) {
            sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private static String getSha256Str(String str) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(str.getBytes());
        byte[] byteData = sha.digest();

        StringBuilder sb = new StringBuilder();

        for (byte byteDatum : byteData) {
            sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
