package com.wang.study.ai.tensor;

import com.wang.study.ai.function.activation.ActivationFunction;

/**
 * 只支持4维
 */
public abstract class Tensor {
    private int[] _shape;


    public int numDimensions(){
        return _shape.length;
    }

    public int[] shape(){
        return _shape;
    }

    public Tensor dot(Tensor t){return null;}

    public Tensor product(Tensor t){return null;}

    /**
     * 取下一个维度指定位置数据, 如果是0维，返回null
     * 比如原Tensor是4维，即是多个三维Tesnor的数组，loc即是数组的位置
     * @param loc
     * @return
     */
    public Tensor sliceSubD(int loc){
        return null;
    }

    public double max(){
        return 0d;
    }

    /**
     * 按最后两个维度分割(高和宽),即按矩阵分割。分割之后Tensor维度不变，只是对最后两个维度切割
     * @param startH
     * @param startW
     * @param endH
     * @param endW
     * @return
     */
    public Tensor slice2D(int startH,int startW,int endH,int endW){
        return null;
    }

    /**
     * 按高度打平，即变成N*1矩阵
     * @return
     */
    public Tensor flat2Height(){return null;}

    public int width(){return 0;}

    public int height(){return 0;}

    public int depth(){return 0;}

    public void padding(int padNum,double value){}

    /**
     * 原tensor和目标tensor必须有相同shape,否则不能相加
     * @param t
     */
    public void add(Tensor t){

    }

    public void setValue(Location loc,double value){}

    public double getValue(Location loc){
        return 0d;
    }

    public double getValue(int... loc){
        return getValue(new Location(loc));
    }

    /**
     * 返回Tensor有多少值
     * @return
     */
    public int numValues(){
        int size = 1;
        for(int i=0;i<_shape.length;i++){
            size *= _shape[i];
        }
        return size;
    }

    /**
     * 对于N*1结尾的可以加入double[](包含N数据，for softmax函数使用). 如果非结构，actualValue
     * af.f(double actualValue, double[] allOutputs)
     * @param af
     */
    public void func(ActivationFunction af){}
}
