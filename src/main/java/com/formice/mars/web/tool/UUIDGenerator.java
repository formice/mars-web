package com.formice.mars.web.tool;

import java.util.UUID;

public class UUIDGenerator {  
    public UUIDGenerator() {  
    }  
 
    public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return temp;  
    }  
    
    /**
	 * 获取uuid，并截取number
	 * @param number 要截取的位数 
	 * @return 截取后的随机数
	 */
	public static String getUUID(int number) {

		String uuidstr;

		try {
			UUID uuid = UUID.randomUUID();

			uuidstr = uuid.toString();

			uuidstr = uuidstr.substring(0, 8) + uuidstr.substring(9, 13) + uuidstr.substring(14, 18) + uuidstr.substring(19, 23) + uuidstr.substring(24);

			if (uuidstr.length() > number) {
				uuidstr = uuidstr.substring(0, number);
			}
			// 去掉"-"符号

		} catch (Exception e) {
			//LOGGER.e(TAG, "get uuid exception : ", e);
			return null;
		}

		return uuidstr;

	}
    
    
 
    public static void main(String[] args) {  
//        String[] ss = getUUID(10);  
//        for (int i = 0; i < ss.length; i++) {  
//            System.out.println("ss["+i+"]====="+ss[i]);  
//        }  
    	System.out.println(getUUID());
    }  
}