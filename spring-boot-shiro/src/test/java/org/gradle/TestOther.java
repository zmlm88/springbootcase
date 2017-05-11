package org.gradle;

import java.security.Key;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class TestOther {

	@Test
	public void testShirMd5() {
		DefaultHashService hashService = new DefaultHashService();
		HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5")
				.setSource(ByteSource.Util.bytes("hello")).setIterations(2)
				.build();
		String hex = hashService.computeHash(request).toHex();
		System.out.println(hex);
	}

	@Test
	public void testThread() throws Exception {
		String password="zhumin";
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
		Key key = factory.generateSecret(pbeKeySpec);
		
		
//		// 加密
//		String encText = aesCipherService.encrypt(text.getBytes(),
//				"mzhuisking".getBytes()).toHex();
//		System.out.println("encText:" + encText);
//		// 解密
//		String decText = new String(aesCipherService.decrypt(
//				Hex.decode(encText), "mzhuisking".getBytes()).getBytes());
//		System.out.println("decText:" + decText);

	}
	@Test
	public void test001(){
		int arr[] = {1,1,1,2,2,3,4,4,5,6,6,7,8,9,10,10,11,11,11};
		System.out.println("length:"+arr.length);
		int seat = 0,loop=0;
		while(loop <arr.length){
			seat = loop;
			for(int j=loop;j<arr.length;j++){
				if(arr[seat] ==  arr[j]){
					loop = j;
				}
			}
			System.out.println(arr[loop]);
			loop++;
		}
	}
}
