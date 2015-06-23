package com.wtyt.util.publicclass.edacoder;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.ec.ECKeyFactory;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

public class EccCoderAgl {
	
	 /** 
	   * 解密<br> 
	   * 用私钥解密 
	   *  
	   * @param data 
	   * @param key 
	   * @return 
	   * @throws Exception 
	   */  
	    public static byte[] decrypt(byte[] data, String key) throws Exception {  
	        // 对密钥解密  
	        byte[] keyBytes = decryptBASE64(key);  	  
	        // 取得私钥  
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = ECKeyFactory.INSTANCE;  
	        ECPrivateKey priKey = (ECPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec); 	  
	        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(),  
	               priKey.getParams());  
	  
	       // 对数据解密  
	        // TODO Chipher不支持EC算法 未能实现  
	        Cipher cipher = new NullCipher();  
	        // Cipher.getInstance(ALGORITHM, keyFactory.getProvider());  
	        cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams()); 
	        return cipher.doFinal(data);  
	    }  
	  
	    /** 
	     * 加密<br> 
	     * 用公钥加密 
	     *  
	     * @param data 
         * @param privateKey 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] encrypt(byte[] data, String publicKey)  
	            throws Exception {  
	        // 对公钥解密  
	        byte[] keyBytes = decryptBASE64(publicKey); 	  
	        // 取得公钥  
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
	        KeyFactory keyFactory = ECKeyFactory.INSTANCE; 	 
	        ECPublicKey pubKey = (ECPublicKey) keyFactory.generatePublic(x509KeySpec); 	 
	        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(),pubKey.getParams());  	  
	        // 对数据加密  
	        // 不支持EC算法 未能实现  
	        Cipher cipher = new NullCipher();      
	        
	        cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams()); 
	        return cipher.doFinal(data);  
	   }  
	 
	    /** 
	     * 取得私钥 
	     *  
	     * @param keyMap 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String getPrivateKey(Map<String, Object> keyMap)  
	            throws Exception {  
	        Key key = (Key) keyMap.get(EdaCoderConstants.ECC_PRIVATE_KEY);	  
	        return encryptBASE64(key.getEncoded());  
	    }  
	  
	    /** 
	     * 取得公钥 
	     *  
	     * @param keyMap 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String getPublicKey(Map<String, Object> keyMap)  
	            throws Exception {  
	        Key key = (Key) keyMap.get(EdaCoderConstants.ECC_PUBLIC_KEY); 	  
	        return encryptBASE64(key.getEncoded());  
	    }  
	    
	    
	    /** 
	     * 取得公钥 
	     *  
	     * @param keyMap 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String getPublicKey(Map<String, Object> keyMap,String publicKey)  
	            throws Exception {  
	        Key key = (Key) keyMap.get(EdaCoderConstants.ECC_PUBLIC_KEY); 	  
	        return encryptBASE64(key.getEncoded());  
	    }  
	  
	    /** 
	     * 初始化密钥 
	     *  
	     * @return 
	     * @throws Exception 
	     */  
	    public static Map<String, Object> initKey() throws Exception {  
	    	BigInteger x1 = new BigInteger(
					"2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8", 16);
			BigInteger x2 = new BigInteger(
					"289070fb05d38ff58321f2e800536d538ccdaa3d9", 16);

			ECPoint g = new ECPoint(x1, x2);

			// the order of generator
			BigInteger n = new BigInteger(
					"5846006549323611672814741753598448348329118574063", 10);
			// the cofactor
			int h = 2;
			int m = 163;
			int[] ks = { 7, 6, 3 };
			ECFieldF2m ecField = new ECFieldF2m(m, ks);
			// y^2+xy=x^3+x^2+1
			BigInteger a = new BigInteger("1", 2);
			BigInteger b = new BigInteger("1", 2);
			EllipticCurve ellipticCurve = new EllipticCurve(ecField, a, b);
			ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, g,n, h);
			// 公钥
			ECPublicKey publicKey = new ECPublicKeyImpl(g, ecParameterSpec);
			BigInteger s = new BigInteger("1234006549323611672814741753598448348329118574063", 10);
			// 私钥
			ECPrivateKey privateKey = new ECPrivateKeyImpl(s, ecParameterSpec);
			Map<String, Object> keyMap = new HashMap<String, Object>(2);
			keyMap.put(EdaCoderConstants.ECC_PUBLIC_KEY, publicKey);
			keyMap.put(EdaCoderConstants.ECC_PRIVATE_KEY, privateKey);
			return keyMap;
	    }  

	    
	    /**
	     * BASE64解密
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptBASE64(String key) throws Exception{
	        return (new BASE64Decoder()).decodeBuffer(key);
	    }
	     
	    /**
	     * BASE64加密
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static String encryptBASE64(byte[] key)throws Exception{
	        return (new BASE64Encoder()).encodeBuffer(key);
	    }

	    
	    
	    

	    /**
	     * 测试ecc加密算法
	     * @throws Exception
	     */
	    public void test() throws Exception {
	         String inputStr = "abc";
	         byte[] data = inputStr.getBytes();
	         Map<String, Object> keyMap = initKey(); 
	         String publicKey = getPublicKey(keyMap);
	         String privateKey = getPrivateKey(keyMap);
	         System.err.println("公钥: \n" + publicKey); 
	         System.err.println("私钥： \n" + privateKey);
	         byte[] encodedData = encrypt(data, publicKey);
	         byte[] decodedData = decrypt(encodedData, privateKey);
	         String outputStr = new String(decodedData);  
             System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
	     } 

	    
	    public static void main(String[] args) throws Exception {
	    	 String inputStr = "abc";
	         byte[] data = inputStr.getBytes();
	         Map<String, Object> keyMap = initKey(); 
	         String publicKey = getPublicKey(keyMap);
	         String privateKey = getPrivateKey(keyMap);
	         System.err.println("公钥: \n" + publicKey); 
	         System.err.println("私钥： \n" + privateKey);
	         byte[] encodedData = encrypt(data, publicKey);
	         byte[] decodedData = decrypt(encodedData, privateKey);
	         
	         System.err.println("加密前: " + new String(decodedData) + "\n\r" + "解密后: " + new String(decodedData));
	         
	         String outputStr = new String(decodedData);  
             System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
             
             System.err.println(encryptEcc("aaasdas"));
		}
	    
	    /**
	     * 加密方法
	     * @param word
	     * @param publicKey
	     * @return
	     * @throws Exception
	     */
	    public  static String encryptEcc(String word) throws Exception{
	    	  Map<String, Object> keyMap = initKey(); 
	    	  String publicKey = getPublicKey(keyMap);
	    	  System.err.println("公钥: \n" + publicKey); 
	    	  byte[] data = word.getBytes();
	    	  byte[] encodedData = encrypt(data, publicKey);
	    	  return new String(encodedData);  
	    } 
}
