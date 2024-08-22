package vkr;

public class PTransformation {

	public static byte[] PTransform(final byte[] a) {
		byte[] temp = new byte[64];
		for(int i = 0; i < 64; i++) {
			temp[i] = a[Constants.Tau[i] & 0xff];
		}
		return temp;
	}
}
