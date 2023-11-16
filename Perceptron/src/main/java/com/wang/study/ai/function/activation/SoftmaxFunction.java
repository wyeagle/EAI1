package com.wang.study.ai.function.activation;


import com.wang.study.ai.util.NumUtil;

/**
 *
 */
public class SoftmaxFunction extends ActivationFunction {

    public double f(Object... params) {
        double[] os = NumUtil.clone(allOutputs(params));
        double a = actualValue(params);

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

    public double df(Object... params){
        double f = f(params);

        double df = f*(1-f);

        return df;
    }

    public static void main(String[] args){
        double[] os = new double[]{1,2,3,4000};

        for(double a:os) {
            SoftmaxFunction f = new SoftmaxFunction();
            double d = f.f(a, os);
            System.out.println(f.f(a, os)+":"+f.df(a, os));
        }
    }
}