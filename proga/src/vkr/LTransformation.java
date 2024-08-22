package vkr;

public class LTransformation {
	
	public static  byte[] LTransform(final byte[] a ) {
		/*byte[] temp = new byte[64];
		for(int i = 0; i < 8; i++) {
			byte[] temp1 = {a[i], a[i+1], a[i+2], a[i+3], a[i+4], a[i+5], a[i+6], a[i+7]};
			byte[] temp2 = lT(temp1);
			for(int j = 0; j < 8; j++) {
				temp[i*8 + j] = temp2[j];
			}
		}
		return temp;*/
		
		byte[] result = new byte[64];
        for(int i = 0; i < 8; i++)
        {
            for(int k = 0; k < 8; k++)
            {
                if((a[i*8+k] & (0x80)) != 0)
                {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+0][l];
                	}
                }
                if((a[i*8+k] & (0x40)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+1][l];
                	}
                    
                }
                if((a[i*8+k] & (0x20)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+2][l];
                	}
                }
                if((a[i*8+k] & (0x10)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+3][l];
                	}
                }
                if((a[i*8+k] & (0x8)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+4][l];
                	}
                }
                if((a[i*8+k] & (0x4)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+5][l];
                	}
                }
                if((a[i*8+k] & (0x2)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+6][l];
                	}
                }
                if((a[i*8+k] & (0x1)) != 0)
                    {
                	for(int l = 0; l < 8; l++) {
                		result[8*i+l] ^= Constants.A[k*8+7][l];
                	}
                }
            }
        }
        return result;
	}
	
	/*private static byte[] lT(final byte[] b) {
		byte[] c = new byte[8];
		boolean flag = true;
		for(int i = 0; i < 8; i++) {
			byte temp = (byte)0x80;
			for(int j = 0; j < 8; j++) {
				if((b[i] & (temp >> j)) != 0){
					if(flag) {
						c = Constants.A[i*8 + j];
						flag = false;
					}
					else {
						//c ^= A[i*8 + j]
						for(int k = 0; k < 8; k++) {
							c[k] ^= Constants.A[i*8 + j][k];
						}
					}
					
				}
			}
		}
		return c;
	}*/

}
