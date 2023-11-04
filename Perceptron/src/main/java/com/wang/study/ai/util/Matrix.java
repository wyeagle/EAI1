package com.wang.study.ai.util;

import java.text.DecimalFormat;

/**
 * 实现矩阵的运算
 *
 */
public class Matrix {
    int row;
    int col;
    //a阶数，即min(row,col)
    int order;
    double value[][];

    public Matrix() {
    }

    public Matrix(int row, int col, double[][] value) {
        this.row = row;
        this.col = col;
        calcOrder();
        this.value = value;
    }

    public static Matrix build(double[][] value){
        return new Matrix(value.length,value[0].length,value);
    }

    /**
     * 可以用build建一个空的矩阵，然后再填充数据
     * @param rowIndex
     * @param rowDatas
     */
    public void fillRowData(int rowIndex, double[] rowDatas){
        if(rowDatas.length != col){
            return;
        }
        for(int i=0;i<rowDatas.length;i++){
            value[rowIndex][i] = rowDatas[i];
        }
    }

    public void fillColData(int colIndex, double[] colDatas){
        if(colDatas.length != row){
            return;
        }
        for(int i=0;i<colDatas.length;i++){
            value[i][colIndex] = colDatas[i];
        }
    }

    public int rowNumber() {
        return row;
    }

    public int colNumber() {
        return col;
    }

    /**
     * 计算阶数
     */
    void calcOrder() {
        order = NumUtil.min(row, col-1);
    }



    public String toString() {
        DecimalFormat df = new DecimalFormat("0.##");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.row; i++) {
            sb.append("|");
            for (int j = 0; j < this.col; j++) {
                if (j == this.col - 1) {
                    sb.append(df.format(this.value[i][j]) );
                } else {
                    sb.append(df.format(this.value[i][j]) + ",");
                }
            }
            sb.append("|\r\n");
        }

        return sb.toString();
    }

    public double[] getRowData(int index){
        return value[index];
    }

    public double[] getColData(int index){
        double[] colData = new double[row];
        for(int i=0;i<row;i++){
            colData[i] = value[i][index];
        }
        return colData;
    }


}
