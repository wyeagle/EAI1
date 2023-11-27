package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.activation.ReluFunction;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tensor0Test {

    private Tensor create(double d){
        int[] shape = new int[0];
        Tensor t = Tensors.create(shape,d);
        return t;
    }

    @Test
    public void numDimensions() {
        Tensor t = create(0);
        Assert.assertEquals(0,t.numDimensions());
    }

    @Test
    public void shape() {
        Tensor t = create(1);
        Assert.assertEquals(0,t.shape().length);
    }

    @Test
    public void getValue() {
        Tensor t = create(1);
        Assert.assertEquals(1,t.getValue(),0);
    }

    @Test
    public void width() {
        Tensor t = create(1);
        Assert.assertEquals(-1,t.width());
    }

    @Test
    public void height() {
        Tensor t = create(1);
        Assert.assertEquals(-1,t.height());
    }

    @Test
    public void depth() {
        Tensor t = create(1);
        Assert.assertEquals(-1,t.depth());
    }

    @Test
    public void numOfD() {
        Tensor t = create(1);
        Assert.assertEquals(-1,t.numOfD(3));
    }

    @Test
    public void numValues() {
        Tensor t = create(1);
        Assert.assertEquals(1,t.numValues());
    }

    @Test
    public void setValue() {
        Tensor t = create(0);
        t.setValue(3);
        Assert.assertEquals(3,t.getValue(),0);
    }

    @Test
    public void add() {
        Tensor t1 = create(0);
        Tensor t2 = create(5);
        t1.add(t2);
        Location loc = new Location(new int[0]);
        Assert.assertEquals(5,t2.getValue(loc),0);
    }

    @Test
    public void sliceSubD() {
        Tensor t = create(1);
        try {
            t.sliceSubD(0);
            Assert.assertTrue(false);
        }catch(EAIException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void max() {
        Tensor t = create(8);
        Assert.assertEquals(8,t.max(),0);
    }

    @Test
    public void slice2D() {
        Tensor t = create(1);
        try {
            t.slice2D(0,0,0,0);
            Assert.assertTrue(false);
        }catch(EAIException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void flat2D() {
        Tensor t = create(1);
        Tensor t2 = t.flat2D();
        Assert.assertEquals(1,t2.numValues());
        double[] valids = new double[]{1};

        for(int i=0;i<t2.numValues();i++){
            Location loc = new Location(new int[]{i,0});
            Assert.assertEquals(valids[0],t2.getValue(loc),0);
        }
    }

    @Test
    public void padding() {
        Tensor t = create(1);
        try {
            t.padding(1,3);
            Assert.assertTrue(false);
        }catch(EAIException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void func() {
        Location loc = new Location(new int[0]);
        ActivationFunction af = new ReluFunction();

        Tensor t = create(1);
        t.func(af);
        Assert.assertEquals(1,t.getValue(loc),0);

        t = create(-1);
        t.func(af);
        Assert.assertEquals(0,t.getValue(loc),0);
    }

    @Test
    public void conv() {
        Tensor t1 = create(3);
        Tensor t2 = create(5);

        Tensor0 t3 = (Tensor0)t1.conv(t2);
        Location loc = new Location(new int[0]);
        Assert.assertEquals(15,t3.getValue(loc),0);
    }

    @Test
    public void product() {
        Tensor t1 = create(3);
        Tensor t2 = create(5);

        Tensor0 t3 = (Tensor0)t1.product(t2);
        Location loc = new Location(new int[0]);
        Assert.assertEquals(15,t3.getValue(loc),0);
    }

    @Test
    public void flat1D() {
        Tensor t = create(1);

        double[] ds = t.flat1D();
        Assert.assertEquals(1,ds.length);
        double[] valids = new double[]{1};

        for(int i=0;i<ds.length;i++){
            Assert.assertEquals(valids[0],ds[i],0);
        }
    }

}