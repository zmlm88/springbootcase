package com.zm.web.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加解密类
 * 
 * @author zhumin
 *
 */
public class AesUtil {
	private static final String AES = "AES";
	public static final String UTF8 = "UTF-8";
	static KeyGenerator kgen = null;
	static {
		try {
			kgen = KeyGenerator.getInstance(AES);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @param content:
	 * 
	 * @param password:
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			// 使用静态代码块来生成KeyGenerator对象
			// KeyGenerator kgen = KeyGenerator.getInstance(AES);
			// 使用128 位
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] encodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(AES);
			// 加密内容进行编码
			byte[] byteContent = content.getBytes(UTF8);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 正式执行加密操作
			byte[] result = cipher.doFinal(byteContent);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @param content:
	 * 
	 * @param password:
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {// 使用静态代码块来生成KeyGenerator对象
				// KeyGenerator kgen = KeyGenerator.getInstance(AES);
				// 使用128 位
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] encodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(AES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 正式执行解密操作
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 二进制--》十六进制转化
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 十六进制--》二进制转化
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}

		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		long begin = new Date().getTime();
		String content = "aaades加密测试";
		String password = "12345678dd";
		// 加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		System.out.println("加密后：" + encryptResultStr);
		// 解密
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		byte[] decryptResult = decrypt(decryptFrom, password);
		// 解密内容进行解码
		String result = new String(decryptResult, UTF8);
		System.out.println("解密后：" + result);
		long end = new Date().getTime();
		System.out.println(end - begin);
	}

}
