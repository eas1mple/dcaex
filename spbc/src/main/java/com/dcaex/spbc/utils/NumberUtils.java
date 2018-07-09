package com.dcaex.spbc.utils;

import java.util.regex.Pattern;

public class NumberUtils {

	public static boolean isNumber(String str){
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches(); 
	}
	
}
