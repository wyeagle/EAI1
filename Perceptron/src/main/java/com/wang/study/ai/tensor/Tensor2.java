package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.util.NumUtil;

public class Tensor2 extends Tensor{

    double[][] _value;

    @Override
    protected void setValue1(Location loc, double value) {
        int[] indexes = loc.loc();
        _value[indexes[0]][indexes[1]] = value;
    }

    @Override
    protected double getValue1(Location loc) {
        int[] indexes = loc.loc();
        return _value[indexes[0]][indexes[1]];
    }

    @Override
    public Tensor sliceSubD(int loc) {
        Tensor1 tensor = (Tensor1)Tensors.create(new int[]{_shape[1]});
        tensor._value = NumUtil.clone(_value[loc]);
        return tensor;
    }

    @Override
    public double max() {
        return NumUtil.max(_value);
    }

    @Override
    public Tensor slice2D(int startH, int startW, int endH, int endW) {
        if(endH < startH || endW < startW || startH < 0 || startW <0 || endH < 0 || endW < 0){
            throw new EAIException("param error :startH/startW/endH/endW = "+startH+"/"+startW+"/"+endH+"/"+endW);
        }
        if(endH >= height() || endW >= width()){
            throw new EAIException("param error :endH/height/endW/width = "+endH+"/"+height()+"/"+endW+"/"+width());
        }

        int newH = endH-startH+1;
        int newW = endW-startW+1;
        Tensor2 tensor = (Tensor2)Tensors.create(new int[]{newH,newW},0);
        for(int i=0;i<newH;i++){
            for(int j=0;j<newW;j++){
                tensor._value[i][j] = _value[i+startH][j+startW];
            }
        }
        return tensor;
    }


    @Override
    public void padding(int padNum, double value) {
        double[][] newValue = new double[_value.length+2*padNum][_value[0].length+2*padNum];
        int h = height();
        int w = width();
        for(int i=0;i<newValue.length;i++) {
            for (int j = 0; j < newValue[0].length; j++) {
                if (i < padNum || i >= h + padNum || j < padNum || j >= w + padNum) {
                    newValue[i][j] = value;
                    continue;
                }
                newValue[i][j] = _value[i - padNum][j - padNum];
            }
        }

        _value = newValue;
        _shape[0] += 2*padNum;
        _shape[1] += 2*padNum;
    }

    @Override
    protected void add1(Tensor t) {
        Tensor2 t2 = (Tensor2)t;
        for(int i=0;i<height();i++) {
            for (int j = 0; j < width(); j++) {
                _value[i][j] += t2._value[i][j];
            }
        }
    }

    @Override
    public void func(BaseFunction af, boolean isFunc) {
        double[] alls = null;
        if(width() == 1){
            alls = flat1D();
        }
        for(int i=0;i<height();i++) {
            for (int j = 0; j < width(); j++) {
                if(isFunc)
                    _value[i][j] = af.f(_value[i][j], alls);
                else
                    _value[i][j] = af.df(_value[i][j], alls);
            }
        }
    }

    @Override
    void createData(double min, double max) {

        for(int i=0;i<height();i++) {
            for (int j = 0; j < width(); j++) {
                _value[i][j] = NumUtil.random(min,max);
            }
        }
    }

    @Override
    protected void init() {
        _value = new double[height()][width()];
    }

    @Override
    public Tensor conv(Tensor t) {
        Tensor2 t2 = (Tensor2)t;

        Tensor0 nt0 = (Tensor0)Tensors.create(new int[0]);
        double value = 0;
        for(int i=0;i<height();i++) {
            for (int j = 0; j < width(); j++) {
                value += _value[i][j]*t2._value[i][j];
            }
        }
        nt0._value = value;
        return nt0;
    }

    @Override
    public Tensor matmul(Tensor t) {
        Tensor2 t2 = (Tensor2)t;
        Tensor2 nt2 = (Tensor2)Tensors.create(new int[]{height(),t2.width()});

        nt2._value = matmul(_value,t2._value);

        return nt2;
    }

    @Override
    public double[] flat1D() {
        double[] values = new double[numValues()];
        int index = 0;
        for(int i=0;i<height();i++) {
            for (int j = 0; j < width(); j++) {
                values[index++] = _value[i][j];
            }
        }
        return values;
    }


    private static double[][] matmul(double[][] m1, double[][] m2) {
        if (m1[0].length != m2.length) {
            throw new EAIException(
                    "The number of cols in the left matrix must equal to the number of rows in the right matrix! ");
        }
        int row = m1.length;
        int col = m2[0].length;
        double[][] result = new double[row][col];
        double c = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 求C的元素值
                for (int k = 0; k < m1[0].length; k++) {
                    c += m1[i][k] * m2[k][j];
                }
                result[i][j] = c;
                c = 0;
            }
        }

        return result;

    }

    protected void subtract1(Tensor t){
        Tensor2 t2 = (Tensor2)t;
        for(int i=0;i<height();i++) {
            for (int j = 0; j < width(); j++) {
                _value[i][j] -= t2._value[i][j];
            }
        }
    }
    protected void multiply1(Tensor t){
    	Tensor2 t2 = (Tensor2)t;
    	for(int i=0;i<height();i++) {
    		for (int j = 0; j < width(); j++) {
    			_value[i][j] *= t2._value[i][j];
    		}
    	}
    }
    protected void divide1(Tensor t){
    	Tensor2 t2 = (Tensor2)t;
    	for(int i=0;i<height();i++) {
    		for (int j = 0; j < width(); j++) {
    			_value[i][j] /= t2._value[i][j];
    		}
    	}
    }

    public void add(double d){
    	for(int i=0;i<_value.length;i++){
    		for(int j=0;j<_value[0].length;j++){
    			_value[i][j] += d;
    		}
    	}
    }

    public void multiply(double d){
    	for(int i=0;i<_value.length;i++){
    		for(int j=0;j<_value[0].length;j++){
    			_value[i][j] *= d;
    		}
    	}
    }

    @Override
    public Object clone() {
        Tensor2 cloneTensor = (Tensor2)super.clone();
        cloneTensor._value = NumUtil.clone(_value);
        return cloneTensor;
    }

    @Override
    public Tensor transpose(){
        Tensor2 nt = (Tensor2)Tensors.create(new int[]{_shape[1],_shape[0]});

        double[][] result = new double[_shape[1]][_shape[0]];
        for (int i = 0; i < _shape[0]; i++) {
            for (int j = 0; j < _shape[1]; j++) {
                result[j][i] = _value[i][j];
            }
        }

        return nt;
    }
}
