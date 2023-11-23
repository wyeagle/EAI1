package com.wang.study.ai.function.activation;


import com.wang.study.ai.util.NumUtil;

/**
 *
 */
public class SoftmaxFunction extends ActivationFunction {

    protected double func(Object... params) {
        double[] os = allOutputs(params);
        double a = actualValue(params);

        //由于是计算e的次方，如果参数值过大，会造成NAN,比如e^700就会溢出，因此先计算参数的最大值，然后每个参数减去这个最大值，计算结果和原值不变
        double max = NumUtil.max(os);
        for(int i=0;i<os.length;i++){
            os[i] = os[i]-max;
        }
        a = a-max;


        double sum = 0d;
        double f = 0d;
        for(double o:os){
            double e = Math.exp(o);
            sum += e;
            if(o == a){
                f = e;
            }
        }

        f = f/sum;

        return f;
    }

    protected double dfunc(Object... params){
        double f = f(params);

        double df = f*(1-f);

        return df;
    }

    public static void main(String[] args){
        double[] os = new double[]{-985.464983,-1064.710174,-852.535859,-1127.275453,-869.357282,-1270.865801,-814.614576,-1371.291717,-1157.629658,-1120.690478};

        for(double a:os) {
            SoftmaxFunction f = new SoftmaxFunction();
            double d = f.f(a, os);
            System.out.println(f.f(a, os)+":"+f.df(a, os));
        }
    }
}
