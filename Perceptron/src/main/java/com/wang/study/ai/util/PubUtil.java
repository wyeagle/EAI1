package com.wang.study.ai.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class PubUtil {
	private static Random random = null;
	static {
		random = new Random(System.currentTimeMillis());
	}

	public static void random(List list){


	}

	public static boolean isEmpty(String str){
		if(str == null || str.trim().equals("")){
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection cls){
		if(cls == null || cls.size() == 0){
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Map map){
		return isEmpty(map,false);
	}

	/**
	 * 检查内容是否为空
	 * @param map
	 * @param valueFlag
	 * @return
	 */
	public static boolean isEmpty(Map map,boolean valueFlag){
		if(map == null || map.size() == 0){
			return true;
		}
		if(valueFlag == false){
			return false;
		}

		Iterator iter = map.values().iterator();
		while(iter.hasNext()){
			Object value = iter.next();
			if(value instanceof Collection){
				if(((Collection)value).size() > 0){
					return false;
				}
			}else if(value != null){
				return false;
			}
		}

		return true;
	}

	public static void addList(List list, Object obj){
		if(list == null){
			return ;
		}
		if(!list.contains(obj)){
			list.add(obj);
		}
	}

	public static void addAllList(List list, List subList){
		if(list == null){
			return ;
		}
		for(Object obj:subList){
			if(!list.contains(obj)){
				list.add(obj);
			}
		}
	}

	public static int str2Int(String str,int defaultValue){
		if(str == null || str.trim().equals("")){
			return defaultValue;
		}
		int result = 0;
		try{
			result = Integer.parseInt(str);
		}catch(Exception e){
			result = defaultValue;
		}
		return result;
	}


	/**
	 * 如果返回null,则返回默认值
	 * @param map
	 * @param key
	 * @return
	 */
	public static Object getMapData(Map map,Object key){
		return getMapData(map,key,null);
	}

	/**
	 * 如果返回null,则返回默认值
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object getMapData2Boolean(Map map,Object key,Boolean defaultValue){
		if(map == null){
			return defaultValue;
		}
		Object value = map.get(key);
		if(value == null){
			value = defaultValue;
		}
		if(!(value instanceof Boolean)){
			try{
				return Boolean.valueOf(value.toString());
			}catch(Exception e){
				return defaultValue;
			}
		}
		return value;
	}

	/**
	 * 如果返回null,则返回默认值
	 * @param map
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object getMapData(Map map,Object key,Object defaultValue){
		if(map == null){
			return defaultValue;
		}
		Object value = map.get(key);
		if(value == null){
			value = defaultValue;
		}
		return value;
	}

	public static Date getCurrentDateTime() {
		return new Date(System.currentTimeMillis());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static int randomInt(int n) {
		return random.nextInt(n);
	}

	public static int randomInt() {
		return randomInt(100000);
	}




	public static String print(Object[] os){
		if(os == null || os.length == 0){
			return "";
		}
		String s = "";
		for(Object o:os){

			String str = "";
			if(o instanceof double[]){
				str = "["+print((double[])o)+"]";
			}else{
				str = String.valueOf(o);
			}
			s += str+",";
		}
		s = s.substring(0,s.length()-1);
		return s;
	}

	public static String print(int[] os){
		if(os == null || os.length == 0){
			return "";
		}
		String s = "";
		for(Object o:os){
			s += String.valueOf(o)+",";
		}
		s = s.substring(0,s.length()-1);
		return s;
	}

	public static String print(double[] os){
		if(os == null || os.length == 0){
			return "";
		}
		String s = "";
		for(double o:os){
			s += print(o)+",";
		}
		s = s.substring(0,s.length()-1);
		return s;
	}

	public static String print(double d){

		BigDecimal b = new BigDecimal(Double.MAX_VALUE);

		try{
			b = new BigDecimal(d);
		}catch(Exception e){
			System.out.println(e);
		}
		b = b.setScale(6,BigDecimal.ROUND_HALF_DOWN);
		return b.toString();
	}

	public static void main(String[] args){
		Object[] objs = new Object[2];
		objs[0] =1d;
		objs[1] =new double[] {1,2,3};
		System.out.println(PubUtil.print(objs));
	}
}
