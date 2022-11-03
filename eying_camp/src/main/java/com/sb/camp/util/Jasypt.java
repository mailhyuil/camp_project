package com.sb.camp.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Jasypt {
	public static void main(String[] args) {

        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        
        jasypt.setAlgorithm("PBEWithMD5AndDES");
        jasypt.setPassword("password");
        
        String encryptedUsername = jasypt.encrypt("root");
        String encryptedPwd = jasypt.encrypt("1234");
        
        System.out.println(encryptedUsername);
        System.out.println(encryptedPwd);
        
        String decryptedUsername = jasypt.decrypt(encryptedUsername);
        String decryptedPwd = jasypt.decrypt(encryptedPwd);
        
        System.out.println(decryptedUsername);
        System.out.println(decryptedPwd);
	}
}
