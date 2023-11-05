/*
 * @(#)NumFunc.java 1.0 2006-8-7
 * Copyright 2006 VandaGroup, Inc. All rights reserved.
 */
package com.wang.study.ai.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数字计算工具类. <br>
 * 
 * @author eagle<br>
 * @version Version 1.00<br>
 */
public class NumUtil {
	
	private static final int SCALE = 4;
	private static final BigDecimal MINVALUE = new BigDecimal("0.00000001");
	
	public static boolean isInt(BigDecimal num){
		return Math.abs(num.doubleValue() - num.intValue()) < MINVALUE.doubleValue();
	}
	
	public static boolean isInt(double num){
		return Math.abs(num - Math.round(num)) < MINVALUE.doubleValue();
	}
	
	public static Integer max(Integer n1, Integer n2) {
		if(n1 > n2) {
			return n1;
		}
		return n2;
	}
	
	public static Integer min(List<Integer> valueList,int minValue) {
		if(valueList == null || valueList.size() == 0) {
			return null;
		}
		Integer min = Integer.MAX_VALUE;
		for(Integer value:valueList) {
			if(value < min)
				min = value;
		}
		
		if(min < minValue) {
			min = minValue;
		}
		return min;
	}
	
	public static Integer min(List<Integer> valueList) {
		if(valueList == null || valueList.size() == 0) {
			return null;
		}
		Integer min = Integer.MAX_VALUE;
		for(Integer value:valueList) {
			if(value < min)
				min = value;
		}
		
		
		return min;
	}

	
	public static Integer min(Integer n1,Integer n2) {
		
		if(n1 <= n2) {
			return n1;
		}
		
		return n2;
	}
	
	public static Integer min(Integer n1,Integer n2, int minValue) {
		int min = n2;
		if(n1 <= n2) {
			min = n1;
		}
		if(min < minValue) {
			min = minValue;
		}
		
		return min;
	}
	
	public static Integer add(Integer n1,Integer n2) {
		if(n1 <= Integer.MAX_VALUE/2 && n2 <= Integer.MAX_VALUE/2) {
			return n1+n2;
		}
		
		return Integer.MAX_VALUE;
		
	}
	
	public static String num2Str(List<Integer> valueList) {
		if(valueList == null || valueList.size() == 0) {
			return null;
		}
		StringBuffer sValue = new StringBuffer();
		for(Integer value:valueList) {
			sValue.append(value).append(",");
		}
		sValue.deleteCharAt(sValue.length()-1);
		
		return sValue.toString();
	}

	public static double[] insertArray(double[] x, int index,int x0){
		double[] newx = new double[x.length+1];
		newx[index] = (double)x0;

		for(int i=0;i<x.length;i++) {

			if(i<index)
				newx[i] = x[i];
			else
				newx[i + 1] = x[i];
		}
		return newx;
	}

	public static double[] clone(double[] d){
		double[] cd = new double[d.length];
		for(int i=0;i<d.length;i++)
			cd[i] = d[i];

		return cd;
	}

	/**
	 * sValue是num2Str(valueList)格式，即"1,2,3"，数字由","分隔
	 * @param sValue
	 * @return
	 */
	public static int min(String sValue,int defaultInt) {
		if(sValue == null) {
			return defaultInt;
		}
		int min = Integer.MAX_VALUE;
		try {
			List<String> valueList = StringUtil.split(sValue, ",");
			for(String value:valueList) {
				int iValue = Integer.parseInt(value.trim());
				if(iValue < min) {
					min = iValue;
				}
			}
		}catch(Exception e) {
			return defaultInt;
		}
		
		return min;
	}
	
	public static BigDecimal min(BigDecimal... b){
		if(b == null || b.length == 0){
			return null;
		}
		
		BigDecimal min = b[0];
		for(int i=1;i<b.length;i++){
			if(compareTo(min,b[i]) > 0){
				min = b[i];
			}
		}
		
		return min;
	}
	
	public static BigDecimal max(BigDecimal... b){
		if(b == null || b.length == 0){
			return null;
		}
		
		BigDecimal max = b[0];
		for(int i=1;i<b.length;i++){
			if(compareTo(max,b[i]) < 0){
				max = b[i];
			}
		}
		
		return max;
	}
	/**
	 * added by eagle 20130304 V1.0.8 CQDF00031201 
	 * 比较时如果误差在指定范围内(Minvalue), 也认为是相同的.
	 * @param b1
	 * @param b2
	 * @param tolerance 误差
	 * @return
	 */
	public static int compareTo(BigDecimal b1, BigDecimal b2, BigDecimal tolerance){
		BigDecimal diff = b1.subtract(b2);
		if(diff.doubleValue() < 0){
			diff = diff.negate();
		}
		if(diff.compareTo(tolerance) <= 0){
			return 0;
		}
		
		return b1.compareTo(b2);
		
	}
	
	public static int compareTo(BigDecimal b1, BigDecimal b2){
		return compareTo(b1,b2,MINVALUE);
		
	}
	
	/**
	 * 乘法,默认二位小数点
	 * @param m1 乘数1
	 * @param m2 乘数2
	 * @return m1*m2结果
	 */
	public static BigDecimal multiply(BigDecimal m1,BigDecimal m2){
		return multiply(m1,m2,SCALE);
	}
	
	public static BigDecimal setScale(BigDecimal bg,int scale,int round){
		return bg.setScale(scale, round);
	}
	
	public static BigDecimal setScale(BigDecimal bg,int scale){
		return setScale(bg,scale,BigDecimal.ROUND_HALF_DOWN);
	}

	public static BigDecimal multiply(BigDecimal... m){
		BigDecimal sum = BigDecimal.ONE;

		for(BigDecimal m1:m){
			sum = NumUtil.multiply(sum,m1);
		}
		return sum;
	}

	/**
	 * 乘法,指定小数点
	 * @param m1 乘数1
	 * @param m2 乘数2
	 * @param scale 小数点位数
	 * @return m1*m2结果
	 */
	public static BigDecimal multiply(BigDecimal m1,BigDecimal m2,int scale){
		return m1.multiply(m2).setScale(scale,BigDecimal.ROUND_HALF_DOWN);
	}
	
	public static BigDecimal multiply(BigDecimal m1,BigDecimal m2,int scale,int round){
		return m1.multiply(m2).setScale(scale,round);
	}
	
	/**
	 * 除法,默认二位小数点
	 * @param m1 除数
	 * @param m2 被除数
	 * @return m1/m2结果
	 */
	public static BigDecimal divide(BigDecimal m1,BigDecimal m2){
		return divide(m1,m2,SCALE);
	}
	
	/**
	 * 除法,指定小数点
	 * @param m1 除数
	 * @param m2 被除数
	 * @param scale 小数点位数
	 * @return m1/m2结果
	 */
	public static BigDecimal divide(BigDecimal m1,BigDecimal m2,int scale){
		return m1.divide(m2,scale,BigDecimal.ROUND_HALF_DOWN);
	}
	
	public static BigDecimal divide(BigDecimal m1,BigDecimal m2,int scale,int round){
		return m1.divide(m2,scale,round);
	}
	
	/**
	 * 减法,默认二位小数点
	 * @param m1 被减数
	 * @param m2 减数
	 * @return m1-m2结果
	 */
	public static BigDecimal subtract(BigDecimal m1,BigDecimal m2){
		return subtract(m1,m2,SCALE);
	}
	
	/**
	 * 减法,指定小数点
	 * @param m1 被减数
	 * @param m2 减数2
	 * @param scale 小数点位数
	 * @return m1-m2结果
	 */
	public static BigDecimal subtract(BigDecimal m1,BigDecimal m2,int scale){
		return m1.subtract(m2).setScale(scale,BigDecimal.ROUND_HALF_DOWN);
	}
	
	/**
	 * 加法,默认二位小数点
	 * @param m1 加数1
	 * @param m2 加数2
	 * @return m1+m2结果
	 */
	public static BigDecimal add(BigDecimal m1,BigDecimal m2){
		return add(m1,m2,SCALE);
	}
	/**
	 * 加法,指定小数点
	 * @param m1 加数1
	 * @param m2 加数2
	 * @param scale 小数点位数
	 * @return m1+m2结果
	 */
	public static BigDecimal add(BigDecimal m1,BigDecimal m2,int scale){
		return m1.add(m2).setScale(scale,BigDecimal.ROUND_HALF_DOWN);
	}
	
	public static BigDecimal divide(int i1, int i2) {
		return divide(new BigDecimal(i1),new BigDecimal(i2),2);
		
	}
	
	public static int multiply(BigDecimal m1, int i2) {
		BigDecimal result = m1.multiply(new BigDecimal(i2)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
		return result.intValue();
	}
	
	public static boolean isEmptyNumber(Long obj){
		if(obj == null){
			return true;
		}
		
		if(0 == obj.intValue()){
			return true;
		}
		
		return false;
	}

	public static double matrixMultiply(double[] b1, double[] b2){
		double sum = 0;
		for(int i=0;i<b1.length;i++){
			sum += b1[i]*b2[i];
		}
		return sum;
	}

	public static String format(double s,int scale){
		BigDecimal ds = BigDecimal.valueOf(s);
		return ds.setScale(scale,BigDecimal.ROUND_HALF_DOWN).toString();
	}

	/**
	 * 对数组b每个元素取一个随机数x, 且start<=x<=end
	 * @param b
	 * @param start
	 * @param end
	 */
	public static void random(double[] b, double start, double end){
		for(int i=0;i<b.length;i++){
			b[i] = random(start,end);
		}
	}

	/**
	 * 对数组b每个元素取一个随机数x, 且start<=x<=end
	 * @param start
	 * @param end
	 */
	public static double random(double start, double end){
		return start+Math.random()*(end-start);
	}

	public static double[] array2double(List<String> strList){
		double[] results = new double[strList.size()];
		for(int i=0;i<strList.size();i++){
			results[i] = Double.parseDouble(strList.get(i));
		}

		return results;
	}

	/**
	 * d1 = d1-d2
	 * @param d1
	 * @param d2
	 * @param delta
	 * @return
	 */
	public static double[] diff(double[] d1, double[] d2,double delta){
		double[] diffs = new double[d1.length];
		boolean isDiff = false;

		for(int i=0;i<d1.length;i++){
			if(Math.abs(d1[i]-d2[i]) > delta){
				diffs[i] = d1[i]-d2[i];
				isDiff = true;
			}else{
				diffs[i] = 0d;
			}
		}
		if(!isDiff){
			return null;
		}
		return diffs;
	}

	/**
	 * d1+d2->d1
	 * @param d1
	 * @param d2
	 */
	public static void add(double[] d1, double[] d2){
		for(int i=0;i<d1.length;i++){
			d1[i] += d2[i];
		}
	}

	public static double[] createArray(int size, double def){
		double[] ds = new double[size];
		for(int i=0;i<ds.length;i++){
			ds[i] = def;
		}

		return ds;
	}

	public static double[][] createArray(int row,int col, double def){
		double[][] ds = new double[row][col];
		for(int i=0;i<ds.length;i++){
			for(int j=0;j<ds[i].length;j++){
				ds[i][j] = def;
			}
		}
		return ds;
	}

	public static int statLargeDelta(double[] ds, double delta){
		int count = 0;
		for(double d:ds){
			if(Math.abs(d) > delta){
				count++;
			}
		}
		return count;
	}
}
