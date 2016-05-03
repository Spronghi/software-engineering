package com.carloan.service.util_service;

import com.carloan.business.model.Operator;
import com.carloan.integration.dao.DAOFactory;
import com.carloan.presentation.factory.FXAlert;
import com.carloan.service.check.CheckerFactory;
import com.carloan.service.control.ServiceControl;
import com.carloan.exception.UpdateModelException;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

class PasswordService implements PasswordServiceInt{
    public void updatePassword(Operator operator) throws UpdateModelException{
        CheckerFactory.getInstance(ServiceControl.PASSWORD_SECURITY).checkUpdate(operator);
        operator.setPassword(generatePasswordHash(operator.getPassword()));
        DAOFactory.getInstance(Operator.class).update(operator);
    }
	public String generatePasswordHash(String password) {
	    try{
	    	int iterations = 1000;
	        char[] chars = password.toCharArray();
	        byte[] salt = getSalt().getBytes();
	         
	        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
	        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        byte[] hash = skf.generateSecret(spec).getEncoded();
	        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			FXAlert.warning(e, "Password Encrypting Exception");
		}
		return null;
    }
    public boolean validatePassword(String originalPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt;
		try {
			salt = fromHex(parts[1]);
			byte[] hash = fromHex(parts[2]);
		 
		    PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		    byte[] testHash = skf.generateSecret(spec).getEncoded();
		     
		    int diff = hash.length ^ testHash.length;
		    for(int i = 0; i < hash.length && i < testHash.length; i++) {
		        diff |= hash[i] ^ testHash[i];
		    }
		    return diff == 0;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            FXAlert.warning(e, "Password Encrypting Exception");
		}
		return false;
    }
    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return sr.toString();
    }
    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
    private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++) {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
