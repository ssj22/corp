package net.corp.auth.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptionUtil {
	public static void main(String[] args) {
		String rawPassword = "test";
		System.out.println(encodePassword(rawPassword));
	}
	
	public static String encodePassword(String rawPassword) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(rawPassword);
	}
	
}
