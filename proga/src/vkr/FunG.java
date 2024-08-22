package vkr;

public class FunG {
	private static byte[] GetKey(byte[] K, int i)
    {
		
        K = XorTransformation.XTransform(K, Constants.C[i]);
        K = STransformation.STransform(K);
        K = PTransformation.PTransform(K);
        K = LTransformation.LTransform(K);
        
        return K;
    }
	
	private static byte[] FunE(byte[] K, byte[] m)
    {
        byte[] temp;
        /*System.out.print("\nm\n");
        for(int i = 0; i < m.length; i++) {
		System.out.print(Integer.toHexString(m[i] & 0xff));
		}*/
        temp = XorTransformation.XTransform(K, m);
        /*System.out.print("\nKm\n");
        for(int i = 0; i < temp.length; i++) {
		System.out.print(Integer.toHexString(temp[i] & 0xff));
		}*/
        for(int i = 0; i < 12; i++)
        {
        	temp = STransformation.STransform(temp);
        	temp = PTransformation.PTransform(temp);
            temp = LTransformation.LTransform(temp);
            K = GetKey(K, i);
            /*System.out.print("\nKC\n");
            
            for(int j = 0; j < K.length; j++) {
    		System.out.print(Integer.toHexString(K[j] & 0xff));
    		}
            */
            temp = XorTransformation.XTransform(temp, K);
        }
        
        return temp;
    }
	
	public static byte[] G(byte [] N, byte[] m, byte[] h)
    {
        
        byte[] K;
        K = XorTransformation.XTransform(h, N);
        /*System.out.print("K\n");
        for(int i = 0; i < K.length; i++) {
		System.out.print(Integer.toHexString(K[i] & 0xff));
		}*/
        K = STransformation.STransform(K);
        /*System.out.print("K\n");
        for(int i = 0; i < K.length; i++) {
		System.out.print(Integer.toHexString(K[i] & 0xff));
		}*/
        K = PTransformation.PTransform(K);
        /*System.out.print("K\n");
        for(int i = 0; i < K.length; i++) {
		System.out.print(Integer.toHexString(K[i] & 0xff));
		}*/
        K = LTransformation.LTransform(K);
        /*System.out.print("K\n");
        for(int i = 0; i < K.length; i++) {
		System.out.print(Integer.toHexString(K[i] & 0xff));
		}*/
        byte[] temp = FunE(K, m);
        temp = XorTransformation.XTransform(h, temp);
        /*System.out.print("temp\n");
        for(int i = 0; i < temp.length; i++) {
    		System.out.print((char) temp[i]);
    	}*/
        byte[] result = XorTransformation.XTransform(temp, m);
        /*System.out.print("res\n");
        for(int i = 0; i < result.length; i++) {
    		System.out.print((char) (result[i] & 0xff));
    	}*/   
        return result;
    }
}
