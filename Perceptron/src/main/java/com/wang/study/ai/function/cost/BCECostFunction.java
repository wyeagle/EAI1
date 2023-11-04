package com.wang.study.ai.function.cost;

/**
 * 二元交叉熵损失函数（Binary Cross-Entropy Loss）
 * L(y,p)=−(y⋅log(p)+(1−y)⋅log(1−p))
 * 其中y是实际标签 (0或1)，p 是模型预测的样本属于类别1的概率。
 */
public class BCECostFunction extends CostFunction {

    public double f(Object... params) {
        double p = acutalValue(params);
        double y = expectedValue(params);

        double cost = -y*Math.log(p)-(1-y)*Math.log(1-p);
        return cost;
    }

    public double df(Object... params) {
        double p = acutalValue(params);
        double y = expectedValue(params);

        double df = (p-y)/(p*(1-p));
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
}
