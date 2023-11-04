package com.wang.study.ai.util;


import com.wang.study.ai.function.BaseFunction;


public class MatrixUtil {
	private static final int ADD = 1;
	private static final int SUBTRACT = 2;
	private static final int MULTIPLY = 3;
	private static final int DIVIDE = 4;

	/**
	 * 只能对两个相同row和col的矩阵做加减乘除运算，即相同位置做运算。 如果使用矩阵意义的乘法，务必用multiply2方法
	 * @param m1
	 * @param m2
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	private static Matrix operator(Matrix m1,Matrix m2, int flag) throws Exception{
		if (m1.row != m2.row || m1.col != m2.col) {
			throw new Exception(
					"The two matrices must be identical");
		}
		double[][] result = new double[m1.row][m1.col];
		for (int i = 0; i < m1.row; i++) {
			for (int j = 0; j < m1.col; j++) {
				switch (flag) {
					case ADD:
						result[i][j] = m1.value[i][j] + m2.value[i][j];
					case SUBTRACT:
						result[i][j] = m1.value[i][j] - m2.value[i][j];
					case MULTIPLY:
						result[i][j] = m1.value[i][j] * m2.value[i][j];
					case DIVIDE:
						result[i][j] = m1.value[i][j] / m2.value[i][j];
				}
			}
		}
		Matrix resultMatrix = new Matrix(m1.row, m1.col, result);
		return resultMatrix;
	}

	public static Matrix add(Matrix m1,Matrix m2) throws Exception {
		return operator(m1,m2,ADD);
	}

	public static Matrix subtract(Matrix m1,Matrix m2) throws Exception {
		return operator(m1,m2,SUBTRACT);
	}

	public static Matrix divide(Matrix m1,Matrix m2) throws Exception {
		return operator(m1,m2,DIVIDE);
	}

	public static Matrix multiply(Matrix m1,Matrix m2) throws Exception {
		return operator(m1,m2,MULTIPLY);
	}

	public static Matrix multiply(Matrix m1,double d) {
		double[][] result = new double[m1.row][m1.col];
		for (int i = 0; i < m1.row; i++) {
			for (int j = 0; j < m1.col; j++) {
				result[i][j] = d * m1.value[i][j];
			}
		}
		Matrix multiplication2 = new Matrix(m1.row, m1.col, result);
		return multiplication2;
	}

	/**
	 * 矩阵意义的乘法， M1[m*n] * M2[n*c] = M3[m*c]
	 * @param m1
	 * @param m2
	 * @return
	 * @throws Exception
	 */
	public static Matrix multiply2(Matrix m1,Matrix m2) throws Exception {
		if (m1.col != m2.row) {
			throw new Exception(
					"The number of cols in the left matrix must equal to the number of rows in the right matrix! "
							+ "(乘法运算时左边矩阵的列数必须等于右边矩阵的行数!)");
		} else {
			double[][] result = new double[m1.row][m2.col];
			double c = 0;
			for (int i = 0; i < m1.row; i++) {
				for (int j = 0; j < m2.col; j++) {
					// 求C的元素值
					for (int k = 0; k < m1.col; k++) {
						c += m1.value[i][k] * m2.value[k][j];
					}
					result[i][j] = c;
					c = 0;
				}
			}
			Matrix multiplication1 = new Matrix(m1.row, m2.col, result);
			return multiplication1;
		}
	}

	public static Matrix transpose(Matrix m1) {
		double[][] result = new double[m1.row][m1.col];
		for (int i = 0; i < m1.row; i++) {
			for (int j = 0; j < m1.col; j++) {
				result[j][i] = m1.value[i][j];
			}
		}
		Matrix transposed = new Matrix(m1.col, m1.row, result);
		return transposed;
	}

	/**
	 * Matrix每个值函数化处理
	 * @param matrix
	 * @param func
	 */
	public static void func(Matrix matrix, BaseFunction func){
		for(int i=0;i< matrix.row;i++){
			for(int j=0;j<matrix.col;j++){
				matrix.value[i][j] = func.f(matrix.value[i][j]);
			}
		}
	}
}
