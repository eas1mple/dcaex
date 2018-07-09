package com.dcaex.spbc.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ID生成工具类
 * @author wb
 * @date 2017-9-13
 */
public class UUID {
	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 9999;

	public static synchronized long next() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$ty%1$tm%1$td%1$tH%1$tM%1$tS%2$04d",date, seq++);
	
		return Long.parseLong(str);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			System.out.println(next());
		}
		System.out.println(System.currentTimeMillis());
		
		System.out.println(String.format("%1$04d",23));

		
	}
	
	
	public static SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");
}
