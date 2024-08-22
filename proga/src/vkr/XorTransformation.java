package vkr;
public class XorTransformation { 
	public static  byte[] XTransform(final byte[] a, final byte[] b) {
		byte[] temp = new byte[64];
		for(int i = 0; i < 64; i++) {
			temp[i] = (byte)(a[i] ^ b[i]);
		}
		return temp;
	}
}
