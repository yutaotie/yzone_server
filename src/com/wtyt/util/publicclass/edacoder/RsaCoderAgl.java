package com.wtyt.util.publicclass.edacoder;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * rsa加密解密算法
 * @author Administrator
 *
 */
public class RsaCoderAgl {
	
	 public static final String KEY_SHA="SHA";
	 
	 public static final String KEY_MD5="MD5";
	     
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
	     * MD5加密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptMD5(byte[] data)throws Exception{
	        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
	        md5.update(data);
	        return md5.digest();
	    }
	     
	    /**
	     * SHA加密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptSHA(byte[] data)throws Exception{
	        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
	        sha.update(data);
	        return sha.digest();
	    }
	    
	    
	    
	    /**
	     * 初始化密钥
	     * @return
	     * @throws Exception
	     */
	    public static Map<String,Object> initKey()throws Exception{
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	        keyPairGenerator.initialize(1024);
	        KeyPair keyPair = keyPairGenerator.generateKeyPair();
	         
	        //公钥
	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
	        //私钥
	        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
	         
	        Map<String,Object> keyMap = new HashMap<String, Object>(2);
	        keyMap.put(EdaCoderConstants.RSA_PUBLIC_KEY, publicKey);
	        keyMap.put(EdaCoderConstants.RSA_PRIVATE_KEY, privateKey);
	         
	        return keyMap;
	    }

	    
	    /**
	     * 取得公钥，并转化为String类型
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
	        Key key = (Key) keyMap.get(EdaCoderConstants.RSA_PUBLIC_KEY);  
	        return encryptBASE64(key.getEncoded());     
	    }
	 
	    /**
	     * 取得私钥，并转化为String类型
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
	        Key key = (Key) keyMap.get(EdaCoderConstants.RSA_PRIVATE_KEY);  
	        return encryptBASE64(key.getEncoded());     
	    }



	       /**
	         * 用私钥加密
	         * @param data  加密数据
	         * @param key   密钥
	         * @return
	         * @throws Exception
	         */
	        public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
	            //解密密钥
	            byte[] keyBytes = decryptBASE64(key);
	            //取私钥
	            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	            KeyFactory keyFactory = KeyFactory.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	            Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	             
	            //对数据加密
	            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	             
	            return cipher.doFinal(data);
	        }
	     

	  
	     
	       /**
	         * 用私钥解密<
             * @param data  加密数据
	         * @param key   密钥
	         * @return
	         * @throws Exception
	         */
	        public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
	            //对私钥解密
	            byte[] keyBytes = decryptBASE64(key);
	             
	            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	            KeyFactory keyFactory = KeyFactory.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	            Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	            //对数据解密
	            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	            cipher.init(Cipher.DECRYPT_MODE, privateKey);
	             
	            return cipher.doFinal(data);
	        }
	     
	     
	       /**
	         * 用公钥加密
	         * @param data  加密数据
	         * @param key   密钥
	         * @return
	         * @throws Exception
	         */
	        public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
	            //对公钥解密
	            byte[] keyBytes = decryptBASE64(key);
	            //取公钥
	            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	            KeyFactory keyFactory = KeyFactory.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	            Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	             
	            //对数据解密
	            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	             
	            return cipher.doFinal(data);
	        }
	     
	      

	   
	     
	       /**
	         * 用公钥解密
	         * @param data  加密数据
	         * @param key   密钥
	         * @return
	         * @throws Exception
	         */
	        public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
	            //对私钥解密
	            byte[] keyBytes = decryptBASE64(key);
	            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	            KeyFactory keyFactory = KeyFactory.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	            Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	             
	            //对数据解密
	            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	            cipher.init(Cipher.DECRYPT_MODE, publicKey);
	             
	            return cipher.doFinal(data);
	        }
	     
	        /**
	         * 用私钥对信息生成数字签名
	         * @param data  //加密数据
	         * @param privateKey    //私钥
	         * @return
	         * @throws Exception
	         */
	        public static String sign(byte[] data,String privateKey)throws Exception{
	            //解密私钥
	            byte[] keyBytes = decryptBASE64(privateKey);
	            //构造PKCS8EncodedKeySpec对象
	            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	            //指定加密算法
	            KeyFactory keyFactory = KeyFactory.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	            //取私钥匙对象
	            PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	            //用私钥对信息生成数字签名
	            Signature signature = Signature.getInstance(EdaCoderConstants.RSA_SIGNATURE_ALGORITHM);
	            signature.initSign(privateKey2);
	            signature.update(data);
	             
	            return encryptBASE64(signature.sign());
	        }

	        /**
	         * 校验数字签名
	         * @param data  加密数据
	         * @param publicKey 公钥
	         * @param sign  数字签名
	         * @return
	         * @throws Exception
	         */
	        public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
	            //解密公钥
	            byte[] keyBytes = decryptBASE64(publicKey);
	            //构造X509EncodedKeySpec对象
	            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	            //指定加密算法
	            KeyFactory keyFactory = KeyFactory.getInstance(EdaCoderConstants.RSA_KEY_ALGORTHM);
	            //取公钥匙对象
	            PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
	             
	            Signature signature = Signature.getInstance(EdaCoderConstants.RSA_SIGNATURE_ALGORITHM);
	            signature.initVerify(publicKey2);
	            signature.update(data);
	            //验证签名是否正常
	            return signature.verify(decryptBASE64(sign));
	             
	        }

	        
	        
	        
	        
	        public static void main(String[] args) throws Exception {
	        	System.err.println("私钥加密-公钥解密");  
    	        String inputStr = "地方 哦i哦斯蒂芬i哦哦";   
    	        
    	        Map<String, Object> keyMap = initKey();  
	    	    String publicKey = getPublicKey(keyMap);
	    	    String privateKey = getPrivateKey(keyMap);
    	        byte[] encodedData = encryptByPublicKey(inputStr.getBytes(),publicKey);   	        
    	        System.err.println("公钥:"+publicKey);    
    	        System.err.println("私钥:"+privateKey);        	    
        	    String sign = sign(encodedData,privateKey);        	    
        	    System.err.println("签名:"+sign);        	    
    	        String outputStr =new String(decryptByPrivateKey(encodedData, privateKey));   	        
    	        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);    	       
    	        System.err.println("私钥签名——公钥验证签名");     	       
    	        // 验证签名  
    	        boolean status = verify(encodedData, publicKey, sign);  
    	        System.err.println("状态:\r" + status);  
    	        
    	        
    	        
    	        System.err.println("公钥加密-私钥解密");  
    	        inputStr = "sigdsfadsfafvcvcbvbgfnghnjhgjghjhgjhgj";//
    	        	//adggdgfdggggggggggggggggggfhg图uiaduhashasdfshdf地方 哦i哦斯蒂芬i哦哦is据iof山东if岁地方哦is的机构is大家佛is地方iopgdofgopfdgpofdi拍摄风景哦i的感觉哦的fig今后地方jo苹果破地方几个破发点兼公平破溃破地方kgipod法国破解航空股份合计你们离开集会颇感开盘跌幅攻破对方进攻破坏景观开发和家乐福给客户哦jo凭感觉破解哈珀儿童哟扑儿童节快乐还将面临的罚款和家乐福狂欢节破空间活泼点附近横盘的佛教和国会尽快肉品还要加快人体黄金分割破坏击破耳机音频儿童建立覆盖到了客户感觉紧迫的佛教和破发点机会平等环境突然红了眼眶和家乐福的软件和破点附近横盘的复活节n2";   
    	        
    	      
    	        encodedData = encryptByPrivateKey(inputStr.getBytes(),privateKey);   	        
    	        System.err.println("公钥:"+publicKey);    
    	        System.err.println("私钥:"+privateKey);        	    
        	    sign = sign(encodedData,privateKey);        	    
        	    System.err.println("签名:"+sign);        	    
    	        outputStr =new String(decryptByPublicKey(encodedData, publicKey));   	        
    	        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);    	       
    	        System.err.println("私钥签名——公钥验证签名");     	       
    	        // 验证签名  
    	        status = verify(encodedData, publicKey, sign);  
    	        System.err.println("状态:\r" + status);  
    	      
			}
	      
}
