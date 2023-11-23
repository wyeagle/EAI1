package com.wang.study.ai.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static void main(String[] args){
		System.out.println(StringUtil.split("abc", ",").size());
		System.out.println(StringUtil.split("abc,b,c", ",").size());
	}
	
	public static String concat(String... str){
		return concatBySeparator("",str);
	}
	
	/**
	 * 返回第一个不为空的字符串
	 * @param strs str
	 * @return
	 */
	public static String firstString(String... strs){
		if(strs == null){
			return "";
		}
		for(String s:strs){
			if(!PubUtil.isEmpty(s))
				return s;
		}
		return "";
	}
	
	public static String concatBySeparator(String sep, String... str){
		StringBuffer buffer = new StringBuffer();
		
		for(String s:str){
			if(!PubUtil.isEmpty(s))
				buffer.append(s).append(sep);
		}
		
		if(buffer.length()>sep.length()){
			return buffer.substring(0, buffer.length()-sep.length());
		}
		return buffer.toString();
	}
	
	public static String concatBySeparator(String sep, List<String> strs){
		StringBuffer buffer = new StringBuffer();
		
		for(String s:strs){
			if(!PubUtil.isEmpty(s))
				buffer.append(s).append(sep);
		}
		
		if(buffer.length()>sep.length()){
			return buffer.substring(0, buffer.length()-sep.length());
		}
		return buffer.toString();
	}
	
	/*
	 * 查找str按Pattern分隔之后strs，检查word是否匹配strs
	 */
	public static boolean isMatch(String str,String word,String sign){
		if(PubUtil.isEmpty(str) || PubUtil.isEmpty(word)){
			return false;
		}
		List<String> strList = null;
		if(PubUtil.isEmpty(sign)){
			strList = new ArrayList<String>();
			strList.add(str);
		}else{
			strList = split(str, sign);
		}
		
		for(String pattern:strList) {
			if(match(word,pattern)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean match(String str,String pattern){
	   
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(str);
		
		return m.find();
	}
	
	
	/**
	 * 检查指定的字符串(pattern是分隔符)是否含有word字符
	 * @param str
	 * @param word
	 * @param pattern
	 * @return
	 */
	public static boolean isIn(String str,String word,String pattern){
		if(PubUtil.isEmpty(str) || PubUtil.isEmpty(word)){
			return false;
		}
		List<String> strList = null;
		if(PubUtil.isEmpty(pattern)){
			strList = new ArrayList<String>();
			strList.add(str);
		}else{
			strList = split(str, pattern);
		}
		
		return isIn(word,strList);
	}
	
	public static boolean isIn(String str,List<String> strList){
		if(PubUtil.isEmpty(strList) || PubUtil.isEmpty(str)){
			return false;
		}
		
		str = str.trim();
		for(String subStr:strList){
			subStr = subStr.trim();
			if(str.equals(subStr)){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 检查字符串是否含有指定word单词
	 * @param str
	 * @param word
	 * @return
	 */
	public static boolean isWord(String str,String word){
		String pattern = "\\b"+word+"\\b";
	
		Pattern p = Pattern.compile(pattern);

		Matcher m = p.matcher(str);

		return m.find();
	}
	
	public static List<String> split(String str,String pattern){
		if(PubUtil.isEmpty(str)) {
			return new ArrayList<String>();
		}

		List<String> splitList = new ArrayList<String>();
	   
		String[] strs = str.split(pattern);

		for(String s:strs){
			s = s.trim();
			splitList.add(s);
		}
		
		return splitList;
	}
	
	/**
	 * 一般性比较检查，即大小写不敏感，且trim
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean generalEqual(String str1,String str2){
		return generalEqual(str1,str2,false);
	}
	
	
	/**
	 * 一般性比较检查，即大小写不敏感，且trim
	 * @param str1
	 * @param str2
	 * @param sensitive 是否大小写敏感， true-敏感，false-不敏感
	 * @return
	 */
	public static boolean generalEqual(String str1,String str2,boolean sensitive){
		if(str1 == null || str2 == null){
			return false;
		}
		if(!sensitive){
			str1 = str1.toLowerCase();
			str2 = str2.toLowerCase();
		}
		str1 = str1.trim();
		str2 = str2.trim();
		
		return str1.equals(str2);
	}

	public static String substring(String str, int size){
		if(str.length() < size){
			return str;
		}
		return str.substring(0,size);
	}


	public static String int2Str(int... is){
		String s = "";
		for(int i=0;i<is.length;i++){
			if(i!= is.length-1){
				s += i+":";
			}else{
				s += i;
			}
		}
		return s;

	}
}
