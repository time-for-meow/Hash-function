package vkr;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HashFunctionStrebogApp {
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ввод названия файла
		Scanner scanner = new Scanner(System.in);
		
		/*for(int i = 0; i < Constants.M2.length; i++) {
    		System.out.print((char) (Constants.M2[i]));
    	}
		System.out.print("\n");
		for(int i = 0; i < Constants.H2.length; i++) {
    		System.out.print((char) (Constants.H2[i] &0xff));
    	}*/
		
		System.out.print("Введите название файла: ");
		String inputString = scanner.nextLine();
        System.out.println("Вы ввели: " + inputString);
        System.out.print("Введите название файла с хешом: ");
		String outputString = scanner.nextLine();
        System.out.println("Вы ввели: " + outputString);
        System.out.print("Введите длину хеша.(256 или 512, по умолчанию 512)");
        boolean flag256 = false;
        try {
        	
        	int num = scanner.nextInt(); //необходимо добавить обработку исключения, если введено не число
        	System.out.print(num);
        	if(num == 256) {
            	flag256 = true;
            }else {
            	flag256 = false;
            }
        	
        } catch(InputMismatchException e){
        	e.printStackTrace();
        	flag256 = false;
        }
        scanner.close();
        long start = System.nanoTime(); //для измерения времени
        //инициализация переменных
        byte[] h = new byte[64]; //инициализационный вектор
        if(flag256) {
        	Arrays.fill(h, (byte)0x01);
        }else
        {
        	Arrays.fill(h, (byte)0x00);
        }
        /*for(int i = 0; i < h.length; i++) {
            System.out.println("h[" + i + "] = " + h[i]); // выводим значения элементов массива на экран
        }*/
        //Arrays.fill(h, (byte)0xFF); 
        /*if(flag256) {
        	System.out.print("!\n");
        	for(int i = 0; i < h.length; i++) {
        		h[i] = (byte)0x01;
        	}
        } else {
        	Arrays.fill(h, (byte)0x00);
        }*/
        /*System.out.println();
        for(int i = 0; i < h.length; i++) {
			System.out.print(Integer.toHexString(h[i] & 0xff));
		}
        System.out.println();*/
        byte[] N = new byte[64];
        Arrays.fill(N, (byte)0x00);
        
        byte[] Sigma = new byte[64];//контрольная сумма
        Arrays.fill(Sigma, (byte)0x00);
        
        byte[] v_0 = new byte[64];//нулевой вектор для функции сжатия в третьем этапе
        Arrays.fill(v_0, (byte)0x00);
        
        byte[] v_512 = new byte[64];//число 512 
        Arrays.fill(v_512, (byte)0x00);
        v_512[62] = (byte)0x02;
        
        byte[] message = new byte[64];
        int len = 0;
        //чтение файла
		FileInputStream inputStream = null;
		
		try {
            inputStream = new FileInputStream(inputString);

            //int currentByte;
            byte[] buffer = new byte[64];
            int numBytesRead;
            while ((numBytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
            	System.out.print("\nСчитанный блок:\n");
            	System.out.println(numBytesRead + " bytes");
                // используем массив buffer для обработки считанных данных
            	for(int i = 0; i < buffer.length; i++) {
            		System.out.print((char) (buffer[i]));
            	}
            	System.out.print("\nСчитанный блок hex:\n");
            	for(int i = 0; i < buffer.length; i++) {
        			System.out.print(Integer.toHexString(buffer[i] & 0xff) + " ");
        		}
            	System.out.println();
            	invertByteArray(buffer);
            	/*System.out.println();
            	for(int i = 0; i < buffer.length; i++) {
            		System.out.print((char) (buffer[i]));
            	}
            	System.out.print("\n");
            	for(int i = 0; i < buffer.length; i++) {
        			System.out.print(Integer.toHexString(buffer[i] & 0xff));
        		}*/
            	//buffer = Constants.M2;
            	//String utf8String = new String(buffer, StandardCharsets.UTF_8);
                //System.out.println(utf8String);
            	
            	//System.out.print("\nблок");
            	
            	if(numBytesRead == 64) {
            		//System.out.print("\n"+ buffer.length +"\n");
            		h = FunG.G(N, buffer, h);
                    N = Add512(N, v_512);
                    Sigma = Add512(Sigma, buffer);
                    System.out.print("Промежуточный хеш:\n");
            		//System.out.print("\n"+ h.length +"\n");
            		/*for(int i = 0; i < h.length; i++) {
                		System.out.print((char) (h[i] & 0xff));
                	}*/
            		//System.out.println(javax.xml.bind.DatatypeConverter.printHexBinary(h);
            		//utf8String = new String(h, StandardCharsets.UTF_8);
            		//System.out.println(utf8String);
            		//System.out.println();
            		for(int i = 0; i < h.length; i++) {
            			System.out.print(Integer.toHexString(h[i] & 0xff) + " ");
            		}
                	//System.out.print("\nпромежуточный хэш\n");
            	}else {//начало 3 этапа
            		/*System.out.print("\nbuffer\n");
                	for(int i = 0; i < buffer.length; i++) {
            			System.out.print(Integer.toHexString(buffer[i] & 0xff));
            		}
            		*/
            		byte[] tmpMessage = new byte[64];
                    Arrays.fill(tmpMessage, (byte) 0x00);
                    //numBytesRead = 8;
                    len = numBytesRead * 8;
                    tmpMessage[64 - numBytesRead - 1] = (byte) 0x01;
                    //buffer = new byte[]{(byte)0xfb, (byte)0xe2, (byte)0xe5, (byte)0xf0, (byte)0xee, (byte)0xe3, (byte)0xc8, (byte)0x20};
                    for(int i = 64 - numBytesRead; i < 64; i++) {
                    	tmpMessage[i] = buffer[i /*+ numBytesRead - 64*/];
                    	//System.out.print(Integer.toHexString(buffer[i + numBytesRead - 64] & 0xff));
                    }
                                            	
                    message = tmpMessage;
                    /*System.out.println("message");
                    for(int i = 0; i < message.length; i++) {
            			System.out.print(Integer.toHexString(message[i] & 0xff));
            		}*/
                    }
            	Arrays.fill(buffer, (byte)0x00);
            	
            }
            //3 этап
            System.out.println("Третий этап.");
            System.out.println("Дополненный последний блок:");
            for(int i = 0; i < message.length; i++) {
    			System.out.print(Integer.toHexString(message[i] & 0xff) + " ");
    		}
            h = FunG.G(N, message, h);
            /*for(int i = 0; i < h.length; i++) {
        		System.out.print((char) (h[i] & 0xff));
        	}*/
            System.out.println();
            System.out.print("Промежуточный хеш:\n");
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff) + " ");
    		}
        	//System.out.print("\nпромежуточный хэш\n");
            //длина сообщения в N
        	byte[] NM = new byte[64];
            Arrays.fill(NM, (byte) 0x00);
            int inc = 0;
            
            //System.out.print("\nдлина" + len + " \n");
            while(len > 0)
            {
                NM[63 - inc] = (byte) (len & 0xff);
                len >>= 8;
                inc++;
            }
            
            /*
            while(numBytesRead > 0)
            {
                NM[63 - inc] = (byte) (numBytesRead & 0xff);
                numBytesRead >>= 8;
                inc++;
            }*/
            
            N = Add512(N, NM);
            
            Sigma = Add512(Sigma, message);
            h = FunG.G(v_0, N, h);
            System.out.println();
            System.out.print("Промежуточный хеш(g0(N,h)):\n");
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff) + " ");
    		}
            h = FunG.G(v_0, Sigma, h);
            System.out.println();
            System.out.print("Промежуточный хеш(g0(Sigma,h))):\n");
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff) + " ");
    		}
            byte[] h1;
            if(flag256) {
            	h1 = Arrays.copyOf(h, 32);
            }else {
            	h1 = h;
            }
            System.out.println();
            System.out.print("Итоговый хеш:\n");
            for(int i = 0; i < h1.length; i++) {
        		System.out.print((char) (h1[i] & 0xff));
        	}
            System.out.println();
            for(int i = 0; i < h1.length; i++) {
    			System.out.print(Integer.toHexString(h1[i] & 0xff)+ " ");
    		}
            long finish = System.nanoTime();
            long elapsed = finish - start;
            System.out.println("\nПрошло времени, мс: " + elapsed / 1000000);
            FileOutputStream output = new FileOutputStream(new File(outputString));
            //IOUtils.write(h1, output);
            output.write(h1);
        	//System.out.print("\nитоговый хэш\n");
            
        	
        	//
        	/*Arrays.fill(h, (byte)0x01);
            Arrays.fill(N, (byte)0x00);
            Arrays.fill(Sigma, (byte)0x00);
            
        	
        	buffer = Constants.M2;
        	h = FunG.G(N, buffer, h);
        	System.out.println();
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff));
    		}
            System.out.println(N.length);
            for(int i = 0; i < N.length; i++) {
    			System.out.print(Integer.toHexString(N[i] & 0xff));
    		}
            System.out.println(v_512.length);
            for(int i = 0; i < v_512.length; i++) {
    			System.out.print(Integer.toHexString(v_512[i] & 0xff));
    		}
            N = Add512(N, v_512);
            System.out.println(N.length);
            for(int i = 0; i < N.length; i++) {
    			System.out.print(Integer.toHexString(N[i] & 0xff));
    		}
            Sigma = Add512(Sigma, buffer);
            
            byte[] tmpMessage = new byte[64];
            Arrays.fill(tmpMessage, (byte) 0x00);
            numBytesRead = 8;
            tmpMessage[64 - numBytesRead - 1] = (byte) 0x01;
            buffer = new byte[]{(byte)0xfb, (byte)0xe2, (byte)0xe5, (byte)0xf0, (byte)0xee, (byte)0xe3, (byte)0xc8, (byte)0x20};
            for(int i = 64 - numBytesRead; i < 64; i++)
                tmpMessage[i] = buffer[i + numBytesRead - 64];
            message = tmpMessage;
            System.out.println();
            for(int i = 0; i < message.length; i++) {
    			System.out.print(Integer.toHexString(message[i] & 0xff));
    		}
            
            System.out.println();
            for(int i = 0; i < Sigma.length; i++) {
    			System.out.print(Integer.toHexString(Sigma[i] & 0xff));
    		}
            h = FunG.G(N, message, h);
            System.out.println();
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff));
    		}
            NM = new byte[64];
            Arrays.fill(NM, (byte) 0x00);
            
            inc = 0;
            int len = numBytesRead * 8;
            while(len > 0)
            {
                NM[63 - inc] = (byte) (len & 0xff);
                len >>= 8;
                inc++;
            }
            System.out.println("\n NM");
            for(int i = 0; i < N.length; i++) {
    			System.out.print(Integer.toHexString(NM[i] & 0xff));
    		}
            System.out.println("\n N");
            for(int i = 0; i < N.length; i++) {
    			System.out.print(Integer.toHexString(N[i] & 0xff));
    		}
            System.out.println("\n N");
            N = Add512(N, NM);
            for(int i = 0; i < N.length; i++) {
    			System.out.print(Integer.toHexString(N[i] & 0xff));
    		}
            System.out.println("\n Sigma");
            Sigma = Add512(Sigma, message);
            for(int i = 0; i < Sigma.length; i++) {
    			System.out.print(Integer.toHexString(Sigma[i] & 0xff));
    		}
            System.out.println("\n h");
            h = FunG.G(v_0, N, h);
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff));
    		}
            System.out.println("\n h");
            h = FunG.G(v_0, Sigma, h);
            for(int i = 0; i < h.length; i++) {
    			System.out.print(Integer.toHexString(h[i] & 0xff));
    		}
            if(flag256) {
            	h1 = Arrays.copyOf(h, 32);
            }else {
            	h1 = h;
            }
            for(int i = 0; i < h1.length; i++) {
        		System.out.print((char) (h1[i] & 0xff));
        	}
            System.out.println();
            for(int i = 0; i < h1.length; i++) {
    			System.out.print(Integer.toHexString(h1[i] & 0xff));
    		}
        	System.out.print("\nитоговый хэш\n");
        	*/
        	//
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		
	}

	
	//добавить mod512
	private static byte[] Add512(byte[] a, byte[] b) {
		byte[] result = new byte[64];
		int temp = 0;
        for(int i = 63; i >= 0; i--)
        {
            temp = (a[i] & 0xff) + (b[i] & 0xff)  + (temp >> 8);
            result[i] = (byte) (temp & 0xff);
        }
		return result;
	}
	
	private static void invertByteArray(byte[] array) {
	    int i = 0;
	    int j = array.length - 1;
	    while (i < j) {
	        byte temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	        i++;
	        j--;
	    }
	}
	
	
	
	
}
