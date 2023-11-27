package com.wang.study.ai.util;

import org.junit.Assert;

public class AssertUtil {
    public static void assertEquals(double[][] es, double[][] rs){
        Assert.assertEquals(es.length,rs.length);
        Assert.assertEquals(es[0].length,rs[0].length);

        for(int i=0;i<es[0].length;i++){
            Assert.assertArrayEquals(es[i],rs[i],0);
        }
    }
}
