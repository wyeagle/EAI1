package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.BaseFunction;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.util.NumUtil;
import com.wang.study.ai.util.PubUtil;

import java.math.BigDecimal;

/**
 * 只支持4维
 */
public abstract class Tensor {
    int[] _shape;


    public int numDimensions(){
        return _shape.length;
    }

    public int[] shape(){
        return _shape;
    }

    public double getValue(int... loc){
        return getValue(new Location(loc));
    }

    /**
     * 不指定位置就返回第一个
     * @return
     */
    public double getValue(){
        int[] loc = new int[_shape.length];
        for(int i=0;i<_shape.length;i++){
            loc[i] = 0;
        }
        return getValue(loc);
    }

    public void setValue(double value){
        int[] loc = new int[_shape.length];
        for(int i=0;i<_shape.length;i++){
            loc[i] = 0;
        }
        setValue(new Location(loc),value);
    }


    public int width(){return numOfD(_shape.length-1);}

    public int height(){return numOfD(_shape.length-2);}

    public int depth(){return numOfD(_shape.length-3);}

    /**
     * 指定维度的数量
     * @param index
     * @return
     */
    public int numOfD(int index){
        if(index < 0){
            return -1;
        }
        if(_shape.length > index){
            return _shape[index];
        }
        return -1;
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
     * 对指定位置赋值，loc必须是精确位置，即N维张量，位置也是设置N维
     * @param loc
     * @param value
     */
    public void setValue(Location loc,double value){
        checkLocation(loc);
        setValue1(loc,value);
    }

    /**
     * 返回指定位置值，loc必须是精确位置
     * @param loc
     * @return
     */
    public double getValue(Location loc){
        checkLocation(loc);
        return getValue1(loc);
    }

    protected void checkLocation(Location location){
        int[] loc = location.loc();
        if(loc.length != _shape.length){
            throw new EAIException("Locaiton size must be equal the length of shape!");
        }
        if(loc.length == 0){
            return;
        }
        for(int i=0;i<loc.length;i++){
            if(loc[i] > _shape[i] || loc[i] < 0){
                throw new EAIException("location is out of size. loc/shape:"+loc[i]+"/"+_shape[i]);
            }
        }
    }

    protected void unsupoort(){
        throw new EAIException("Unsupport method!");
    }

    public void add(Tensor t){
        if(NumUtil.isEqual(_shape,t._shape)==false){
            throw new EAIException("Tensor must be same shape! "+ PubUtil.print(_shape)+":"+PubUtil.print(t._shape));
        }
        add1(t);
    }

    public void subtract(Tensor t){
        if(NumUtil.isEqual(_shape,t._shape)==false){
            throw new EAIException("Tensor must be same shape! "+ PubUtil.print(_shape)+":"+PubUtil.print(t._shape));
        }
        subtract1(t);
    }

    public void multiply(Tensor t){
        if(NumUtil.isEqual(_shape,t._shape)==false){
            throw new EAIException("Tensor must be same shape! "+ PubUtil.print(_shape)+":"+PubUtil.print(t._shape));
        }
        multiply1(t);
    }

    public void divide(Tensor t){
        if(NumUtil.isEqual(_shape,t._shape)==false){
            throw new EAIException("Tensor must be same shape! "+ PubUtil.print(_shape)+":"+PubUtil.print(t._shape));
        }
        divide1(t);
    }

    public void subtract(double d){
        add(-d);
    }



    public void divide(double d){
        multiply(1/d);
    }



    public Tensor flat2D() {
        int[] flatShape = new int[]{numValues(),1};
        if(NumUtil.isEqual(this.shape(),flatShape)){
            return this;
        }
        Tensor2 tensor = (Tensor2)Tensors.create(new int[]{numValues(),1},0);
        double[] all = flat1D();

        for (int i = 0; i < all.length; i++) {
            tensor._value[i][0] = all[i];
        }

        return tensor;
    }

    protected int[] mixShape(Tensor t, Tensor subT){
        int[] nShape = new int[t._shape.length];
        nShape[0] = t._shape[0];
        for(int i=0;i<subT._shape.length;i++){
            nShape[i+1] = subT._shape[i];
        }
        return nShape;
    }

    /////////////////////////////////////////////////

    protected abstract void setValue1(Location loc,double value);

    /**
     * 返回指定位置值，loc必须是精确位置
     * @param loc
     * @return
     */
    protected abstract double getValue1(Location loc);

    /**
     * 取下一个维度指定位置数据, 如果是0维，返回null
     * 比如原Tensor是4维，即是多个三维Tesnor的数组，loc即是数组的位置
     * @param loc
     * @return
     */
    public abstract Tensor sliceSubD(int loc);

    public abstract double max();

    /**
     * 按最后两个维度分割(高和宽),即按矩阵分割。分割之后Tensor维度不变，只是对最后两个维度切割
     * @param startH
     * @param startW
     * @param endH
     * @param endW
     * @return
     */
    public abstract Tensor slice2D(int startH,int startW,int endH,int endW);

    public Tensor sliceWidth(int startW,int stride){
        return slice2D(0,startW,height()-1,startW+stride);
    }

    public Tensor sliceHeight(int startH,int stride){
        return slice2D(startH,0,startH+stride,width()-1);
    }

    protected abstract void init();

    public abstract void padding(int padNum,double value);

    /**
     * 原tensor和目标tensor必须有相同shape,否则不能相加
     * @param t
     */
    protected abstract void add1(Tensor t);



    /**
     * 对于N*1结尾的可以加入double[](包含N数据，for softmax函数使用). 如果非结构，actualValue
     * af.f(double actualValue, double[] allOutputs)
     * @param af
     * @param isFunc true-正常函数， false-求导
     *
     */
    public abstract void func(BaseFunction af, boolean isFunc);

    /**
     * 按shape生成数据结构,并按[min,max]区间随机赋值
     */
    abstract void createData(double min, double max);

    /**
     * 卷积操作
     * @param t
     * @return
     */
    public abstract Tensor conv(Tensor t);

    /**
     * 对最后两个维度(matrix)做乘积， [M,N]*[N,K] = [M,K]
     * @param t
     * @return
     */
    public abstract Tensor matmul(Tensor t);

    /**
     * 按一维打平，即数组
     * @return
     */
    public abstract double[] flat1D();

    public void insertWidth(int width,Tensor tensor){
        unsupoort();
    }

    public void insertHeight(int height,Tensor tensor){
        unsupoort();
    }

    protected abstract void subtract1(Tensor t);
    protected abstract void multiply1(Tensor t);
    protected abstract void divide1(Tensor t);

    public abstract void add(double d);

    public abstract void multiply(double d);

    public Tensor transpose(){unsupoort(); return null;}

    @Override
    public Object clone() {
        Tensor cloneTensor = null;
        try {
            cloneTensor = (Tensor) super.clone();
            cloneTensor._shape = NumUtil.clone(_shape);
        }catch(Exception e){
            throw new EAIException(this.getClass().getSimpleName()+" clone error:"+e.getMessage());
        }
        return cloneTensor;
    }
}
