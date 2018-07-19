package com.b2w.project.utils;

import java.net.URLDecoder;

public class URLDecode {
	
	public static String decodeParam(String param) {
		try {
			return URLDecoder.decode(param,"UTF-8");
		}catch(Exception e) {
			return "";
		}
	}
	
}
