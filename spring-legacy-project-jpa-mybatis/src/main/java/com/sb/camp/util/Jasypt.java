package com.sb.camp.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Jasypt {
	public static void main(String[] args) {
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        
        jasypt.setAlgorithm("PBEWithMD5AndDES");
        jasypt.setPassword("password");
        
        String encryptedPwd = jasypt.encrypt("1234");
        
        System.out.println(encryptedPwd);
        
        String decryptedPwd = jasypt.decrypt(encryptedPwd);
        
        System.out.println(decryptedPwd);
	}
}
