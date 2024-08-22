package vkr;

public class STransformation {
	
	
	public static byte[] STransform(final byte[] a) {
		byte[] temp = new byte[64];
		//System.out.print("s\n");
		for(int i = 0; i < 64; i++) {
			temp[i] = Constants.Pi[a[i] & 0xff];
			//System.out.print(i + " " + (char)(Constants.Pi[(a[i]& 0xff)]));
		}
		/*System.out.print("\ntemp\n");
		for(int i = 0; i < temp.length; i++) {
			System.out.print((char) temp[i]);
		}
		System.out.print("\n");*/
		return temp;
	}

}
