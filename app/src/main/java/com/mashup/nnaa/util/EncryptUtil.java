package com.mashup.nnaa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

    public static String encrypt(String src) {
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

    public static String doubleEncryptForSignIn(String encryptedStr) {
        // sha256 + md5
        String sha = null;
        String shamd5 = encryptedStr;
        try {
            sha = getSha256Str(encryptedStr);
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
