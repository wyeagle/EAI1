package com.wang.study.ai.function.cost;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.util.NumUtil;

/**
 * 二元交叉熵损失函数（Binary Cross-Entropy Loss）
 * L(y,p)=−(y⋅log(p)+(1−y)⋅log(1−p))
 * 其中y是实际标签 (0或1)，p 是模型预测的样本属于类别1的概率。
 */
public class BCECostFunction extends CostFunction {
    private static final double MINVALUE = 0.00000001d;

    public double f(Object... params) {
        double p = acutalValue(params);
        double y = expectedValue(params);

        if(p < MINVALUE){
            p = MINVALUE;
        }
        if(1-p < MINVALUE){
            p = 1-MINVALUE;
        }

        double cost = -y*Math.log(p)-(1-y)*Math.log(1-p);
        if((new Double(cost)).isNaN() || (new Double(cost)).isInfinite()){
            throw new EAIException("cost exception :"+p+":"+y+":cost = "+cost);
        }
        return cost;
    }

    public double df(Object... params) {
        double p = acutalValue(params);
        double y = expectedValue(params);

        if(p < MINVALUE){
            p = MINVALUE;
        }
        if(1-p < MINVALUE){
            p = 1-MINVALUE;
        }

        double df = (p-y)/(p*(1-p));

        if((new Double(df)).isNaN() || (new Double(df)).isInfinite()){
            throw new EAIException("cost exception :"+p+":"+y+":cost df = "+df);
        }
        return df;
    }

    public double fByArray(double[] actuals, double[] expecteds){
        double diff = 0d;
        for(int i=0;i<actuals.length;i++){
            diff += f(actuals[i],expecteds[i]);
        }

        diff = diff/actuals.length;
        return diff;
    }

    public static void main(String[] args){
        BCECostFunction f = new BCECostFunction();

        for(int i=-5;i<=5;i++){
            for(int j=-5;j<=5;j++){
                double di = new Double(i);
                double dj = new Double(j);
                System.out.println(i+":"+j+" f ="+f.f(di,dj));
                System.out.println(i+":"+j+" df ="+f.f(di,dj));
            }
        }
        double d1 = 9.3249036E-317;
        double d2 = 1;
        System.out.println(f.f(d1,d2));
        System.out.println(f.f(d2,d1));
    }

}
