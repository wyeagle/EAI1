package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;

public class Tensors {

    private static final String TENSOR_NAME = Tensor.class.getName();

    private static Tensor newInstance(int numOfD){
        String clsName = TENSOR_NAME+numOfD;
        Tensor tensor = null;
        try {
            tensor = (Tensor)Class.forName(clsName).newInstance();
        }catch(Exception e){
            throw new EAIException(clsName+" not found!");
        }
        return tensor;
    }

    public static Tensor create(double d){
        int[] shape = new int[0];
        Tensor t = create(shape);
        t.setValue(d);
        return t;
    }

    public static Tensor create(double[] d){
        int[] shape = new int[]{d.length};
        Tensor1 t = (Tensor1)create(shape);
        t._value = d;
        return t;
    }

    public static Tensor create(double[][] d){
        int[] shape = new int[]{d.length,d[0].length};
        Tensor2 t = (Tensor2)create(shape);
        t._value = d;
        return t;
    }

    public static Tensor create(double[][][] d){
        int[] shape = new int[]{d.length,d[0].length,d[0][0].length};
        Tensor3 t = (Tensor3)create(shape);
        for(int i=0;i<t._value.length;i++){
            t._value[i] = (Tensor2)create(d[i]);
        }
        return t;
    }

    public static Tensor create(double[][][][] d){
        int[] shape = new int[]{d.length,d[0].length,d[0][0].length,d[0][0][0].length};
        Tensor4 t = (Tensor4)create(shape);
        for(int i=0;i<t._value.length;i++){
            t._value[i] = (Tensor3)create(d[i]);
        }
        return t;
    }

    /**
     * 创建一个shape的Tensor,但值为空，即Tensor里的double[]...为空
     * @param shape
     * @return
     */
    public static Tensor create(int[] shape){
        Tensor tensor = Tensors.newInstance(shape.length);
        tensor._shape = shape;
        tensor.init();
        return tensor;
    }

    /**
     *
     * @param shape
     * @param value create之后，每个值按value设置
     * @return
     */
    public static Tensor create(int[] shape,double value){
        Tensor tensor = create(shape);
        tensor.createData(value,value);
        return tensor;
    }

    /**
     *
     * @param shape
     * @param min 每个值按min,max之间随机设置
     * @param max
     * @return
     */
    public static Tensor create(int[] shape,double min, double max){
        Tensor tensor = create(shape);
        tensor.createData(min,max);
        return tensor;
    }

}
