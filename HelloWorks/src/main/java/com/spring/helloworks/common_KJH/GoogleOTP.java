package com.spring.helloworks.common_KJH;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
public class GoogleOTP {

	/*
	public static void main(String[] args) {
		
		GoogleOTP otp = new GoogleOTP();
		HashMap<String, String> map = otp.generate("name", "host");
		String otpkey = map.get("encodedKey");
		String url = map.get("url");
		System.out.println(otpkey);
		
		// 아래의 결과는 당연히 압도적인 확률로 false가 나온다.
		// 우선 위의 과정으로 생성된 키/url을 otp앱에 등록하고나서 표시되는 번호와 생성된 키를 넣어주면 true가 나올 것이다.
		boolean check = otp.checkCode("123123", otpkey);
		System.out.println(check);
		
	}
	*/
	
	public HashMap<String, String> generate(String userName, String hostName) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		byte[] buffer = new byte[5 + 5 * 5];
		new Random().nextBytes(buffer);
		Base32 codec = new Base32();
		byte[] secretKey = Arrays.copyOf(buffer, 10);
		byte[] bEncodedKey = codec.encode(secretKey);

		String encodedKey = new String(bEncodedKey);
		String url = getQRBarcodeURL(userName, hostName, encodedKey);
		// Google OTP 앱에 userName@hostName 으로 저장됨
		// key를 입력하거나 생성된 QR코드를 바코드 스캔하여 등록

		map.put("encodedKey", encodedKey);
		map.put("url", url);
		return map;
	
	}

	public boolean checkCode(String userCode, String otpkey) {
		
		long otpnum = Integer.parseInt(userCode); // Google OTP 앱에 표시되는 6자리 숫자
		long wave = new Date().getTime() / 30000; // Google OTP의 주기는 30초
		boolean result = false;
		try {
			Base32 codec = new Base32();
			byte[] decodedKey = codec.decode(otpkey);
			int window = 3;
			for (int i = -window; i <= window; ++i) {
				long hash = verify_code(decodedKey, wave + i);
				if (hash == otpnum) result = true;
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
		
	}

	private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}

		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);

		int offset = hash[20 - 1] & 0xF;

		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			truncatedHash |= (hash[offset + i] & 0xFF);
		}

		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;

		return (int) truncatedHash;
		
	}

	public static String getQRBarcodeURL(String user, String host, String secret) {
		
		// QR코드 주소 생성
		String format2 = "http://chart.apis.google.com/chart?cht=qr&chs=200x200&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s&chld=H|0";
		return String.format(format2, user, host, secret);
		
	}


	/*
	// 최초 개인키 생성 시 사용하는 메소드
    public String getSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    // OTP검증 요청 때마다 개인키로 OTP 생성하는 메소드
    public String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    // 개인키, 계정명(유저ID), 발급자(회사명)을 받아서 구글OTP 인증용 링크를 생성하는 메소드
    public String getGoogleOTPAuthURL(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    // url, 파일생성할경로, 높이px, 폭px을 받아서 QR코드 이미지를 생성하는 메소드
    public void getQRImage(String googleOTPAuthURL, String filePath, int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(googleOTPAuthURL, BarcodeFormat.QR_CODE, width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }
	*/
	
}
