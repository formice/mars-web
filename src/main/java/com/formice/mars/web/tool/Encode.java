package com.formice.mars.web.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',

        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 静态加密方法
	 * 
	 * @param codeType
	 *            传入加密方式
	 * @param content
	 *            传入加密的内容
	 * @return 返回加密结果
	 */
	public static String getEncode(String codeType, String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance(codeType);// 获取一个实例，并传入加密方式
			digest.reset();// 清空一下
			digest.update(content.getBytes());// 写入内容,可以指定编码方式content.getBytes("utf-8");
			StringBuilder builder = new StringBuilder();
			for (byte b : digest.digest()) {
				builder.append(Integer.toHexString((b >> 4) & 0xf));
				builder.append(Integer.toHexString(b & 0xf));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static final String MD5(String s) {

		try {

			byte strTemp[] = s.getBytes("UTF-8");

			MessageDigest mdTemp = MessageDigest.getInstance("MD5");

			mdTemp.update(strTemp);

			byte md[] = mdTemp.digest();

			int j = md.length;

			char str[] = new char[j * 2];

			int k = 0;

			for (int i = 0; i < j; i++) {

				byte byte0 = md[i];

				str[k++] = hexDigits[byte0 >>> 4 & 15];

				str[k++] = hexDigits[byte0 & 15];

			}

			return new String(str);

		} catch (Exception e) {

			return null;

		}

	}


	
	
//
//	public static void main(String[] args) {
//		String str = Encode.getEncode("MD5", "hello world!");// 用MD5方式加密
//		System.out.println(str);
//		// fc3ff98e8c6a0d3087d515c0473f8677
//		String str1 = Encode.getEncode("SHA", "hello world!");// 用SHA方式加密
//		System.out.println(str1);
//		// 430ce34d020724ed75a196dfc2ad67c77772d169
//	}
	
	public static void main(String[] args){
		System.out.println("21313123424234,32423423423423334,343242342342342342345353");
		System.out.println(MD5("000000"));
	}
}
