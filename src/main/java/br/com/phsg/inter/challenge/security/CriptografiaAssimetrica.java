/**
 * 
 */
package br.com.phsg.inter.challenge.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
public class CriptografiaAssimetrica {

	private static final String beginPublicKeyMarker = "-----BEGIN PUBLIC KEY-----";
	private static final String endPublicKeyMarker = "-----END PUBLIC KEY-----";
	private static final String beginPrivateKeyMarker = "-----BEGIN PRIVATE KEY-----";
	private static final String endPrivateKeyMarker = "-----END PRIVATE KEY-----";
	private static final String beginPrivateKeyRSAMarker = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String endPrivateKeyRSAMarker = "-----END RSA PRIVATE KEY-----";
	private static final String enterMarker = "\n";
	private static final String tabMarker = "\t";
	private static final String spaceMarker = " ";
	private static final String enterLinuxMarker = "\r\n";
	
	public static final String internalPublicKey = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt7PbmmgOHpHw0eUdq3mX\r\n"
			+ "LvKZnm/+aiR4l+DJQG/TNUhsuPwml71cFYK6VxIEjH6hjCN4ewznM80jJOtwAFMV\r\n"
			+ "RzFTsTDdKLB23/WnYutABhaXhEyGNdxX4pVNAAllkSYEGNh/mCl3B0XC3uwRUjOs\r\n"
			+ "srC8w/1396Lk83S0kW0W7tgnphDQKW9tS9AD96qWRszasY+xQjbrak6mkS7T1drr\r\n"
			+ "frrRBRQynjDYszDKcjago0+YqDusjR70pQpUZwPImAWkX7nBue2NPOy+Roj8butJ\r\n"
			+ "il86+zQPQ+wLq1k0iGhfnW//1T4CmIKLT4UwFhg//yzqLjiMunEzw6j0xENtdxxP\r\n" + "rwIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

	public static final String internalPrivateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEowIBAAKCAQEAt7PbmmgOHpHw0eUdq3mXLvKZnm/+aiR4l+DJQG/TNUhsuPwm\r\n"
			+ "l71cFYK6VxIEjH6hjCN4ewznM80jJOtwAFMVRzFTsTDdKLB23/WnYutABhaXhEyG\r\n"
			+ "NdxX4pVNAAllkSYEGNh/mCl3B0XC3uwRUjOssrC8w/1396Lk83S0kW0W7tgnphDQ\r\n"
			+ "KW9tS9AD96qWRszasY+xQjbrak6mkS7T1drrfrrRBRQynjDYszDKcjago0+YqDus\r\n"
			+ "jR70pQpUZwPImAWkX7nBue2NPOy+Roj8butJil86+zQPQ+wLq1k0iGhfnW//1T4C\r\n"
			+ "mIKLT4UwFhg//yzqLjiMunEzw6j0xENtdxxPrwIDAQABAoIBAECgddGMaes3TJGL\r\n"
			+ "GTUKQbWyg3jJzadpzUMhQav5q0RDhnxNuQYJCCxJIWUgYZlLs2mp0Oj5UR1YPdTv\r\n"
			+ "PyfeEs5I/DiKCEQYULnZolmaiZuatxNEGLM6IXEFNWGd7K1zwl07TSgq7kUQtc3X\r\n"
			+ "HwDcTRVQOBh7Q4lLNWCw3hWLW1YK+3wvaRqkod6hrag3gfbg+kHproGhJiFLiLBq\r\n"
			+ "ENG3/LsYDIw2AneCKkSb8Iw+R6KgmRu75acatAj791lEp6I/7e5Z2giOxDHjjpjq\r\n"
			+ "CBB7l1ATO3Fjd/hBG8F1r0Sw8HXT9+KjPQB5dEQrZ+SRQkxsLPKWH/zw/UbLor9r\r\n"
			+ "c9oYygECgYEA94PIrAeFprIF5C7pdMghaY/MIfa8jIfeQ+qzdsy6twxEha/9DDUb\r\n"
			+ "289CxKaJtX4KIgQ7AxosrxzMHnHhgRBLA7nV7Dn9LTMzvlWoM+FNAfSmPxAVnTUu\r\n"
			+ "hEwFGuUJOc8WVgE1mtCB+d1jToY5kulnrvt5BTHtzrfQRKDqolEGsl8CgYEAvgAN\r\n"
			+ "GeUAu19NRjmvbhTuwqSmxmt1qlxKnJxTMyfwnFc6VwKhYT3cS98Qbipg1uIGzH0K\r\n"
			+ "auGfAXMaJKq9t42vbuyCkF2N0CFxONRbiCAkf/MNPfReeZEJsrEIlsTyMDZoFCt+\r\n"
			+ "MbusITHTOVLVLydgCrZbIEgxBvsMR431oEW+hLECgYA1/AJKsyFNaEqsqCa3F+dh\r\n"
			+ "tMFbLwuUwxWRTqFx6dlXj09qsSXKAdNfr+bqKUCj2yeun6mXVaAlWWyTv1bl5X3X\r\n"
			+ "BDNKYKN9tks02k3jcuJu6MNRwdpU/YJ1zHbYlNfcSfSfMYhVieCvdL/yIohwKIQ6\r\n"
			+ "wBJ3NZqemazNKyPB31ZMlQKBgQCa4JPNXdzg3+ivq+oSw8F0rJ69826w9hZj3xH7\r\n"
			+ "Cz2ZU4lcz+oYyEJBHo3YH3dc8lY0bmEFivEAT4EqDnwjmb09oFE1uO0WkYwnlQMf\r\n"
			+ "zYOzQL60l/RDCDVtkd28eMIrBs6EUofFpoPDc0gI3Dqm+2PQl6FkiTRdylrSISfu\r\n"
			+ "A6wvoQKBgGgPpDedLSEiPGlsNQsF9irrzpOmOv/1X3wpG4lWe4fzEyoMXGil0T/Q\r\n"
			+ "w6QbE2v6VjZTszuPW22CAi+DCpY9UbMrVFahTM8hB6ZYC8iGB+yYjt7wuguIgLqx\r\n"
			+ "G4KpWo0efqo6ZytfJ855ll1Vh/3e31r3q5xbP3GsDLTTaKJlFOzs\r\n" + "-----END RSA PRIVATE KEY-----";

	public static final String ALGORITHM = "RSA";

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private Cipher cipher;

	public CriptografiaAssimetrica(PublicKey publicKey, PrivateKey privateKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());	
		
		this.publicKey = publicKey;
		this.privateKey = privateKey;

		this.cipher = Cipher.getInstance(ALGORITHM);
	}

	public static KeyPair genKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
		generator.initialize(2048);
		KeyPair keyPair = generator.generateKeyPair();

		return keyPair;
	}

	public static PublicKey genPublicKey(String publicKeyContent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		publicKeyContent = cleanMarkersKey(publicKeyContent);

		byte[] buffer = Base64.decodeBase64(publicKeyContent);
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM);

		X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(buffer);
		RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

		return pubKey;
	}

	/**
	 * @param keyContent
	 * @return
	 */
	private static String cleanMarkersKey(String keyContent) {
		keyContent = keyContent.replaceAll(enterLinuxMarker, "").replace(enterMarker, "").replace(tabMarker, "")
				.replace(beginPublicKeyMarker, "").replace(beginPrivateKeyRSAMarker, "").replace(beginPrivateKeyMarker, "")
				.replace(endPublicKeyMarker, "").replace(endPrivateKeyRSAMarker, "").replace(endPrivateKeyMarker, "");
		return keyContent;
	}

	public static PrivateKey genPrivateKey(String privateKeyContent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		privateKeyContent = cleanMarkersKey(privateKeyContent);

		byte[] buffer = Base64.decodeBase64(privateKeyContent);
		KeyFactory kf = KeyFactory.getInstance(ALGORITHM);

		PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(buffer, ALGORITHM);
		PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

		return privKey;
	}

	public String criptografar(String msg)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		this.cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
	}

	public String descriptografar(String msg)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		this.cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
	}

	public static void main(String[] args) {
		PrivateKey privatekey;
		try {
			privatekey = CriptografiaAssimetrica.genPrivateKey(internalPrivateKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível gerar chave privada!");
		}

		PublicKey publickey;
		try {
			publickey = CriptografiaAssimetrica.genPublicKey(internalPublicKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível gerar chave publica!");
		}

		try {
			KeyPair keyPair = CriptografiaAssimetrica.genKeyPair();
//			privatekey = keyPair.getPrivate();
//			publickey = keyPair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível gerar chaves!");
		}

		CriptografiaAssimetrica cript;
		try {
			cript = new CriptografiaAssimetrica(publickey, privatekey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível criar criptografia!");
		}
		String criptpgrafado, descriptpgrafado = "";
		try {
			criptpgrafado = cript.criptografar("Pedro!!!");
			descriptpgrafado = cript.descriptografar(criptpgrafado);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("Não possível criptpgrafar dados!");
		}
		System.out.println("Texto Original: Pedro!!!");
		System.out.println("Texto Criptografado: " + criptpgrafado);
		System.out.println("Texto Descriptpgrafado: " + descriptpgrafado);
	}

}
