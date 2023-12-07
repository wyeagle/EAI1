package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.activation.ActivationFunction;
import com.wang.study.ai.function.activation.ReluFunction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Tensor1Test extends TensorTest{

    @Test
    public void testBase() {
        Tensor t = create(1,1,5);
        Assert.assertEquals(1,t.numDimensions());

        int[] shape = t.shape();
        Assert.assertEquals(1,shape.length);
        Assert.assertEquals(5,shape[0]);

        Location loc = new Location(new int[]{3});
        Assert.assertEquals(1,t.getValue(loc),0);

        t.setValue(loc,10);
        Assert.assertEquals(10,t.getValue(loc),0);

        t.setValue(100);
        Assert.assertEquals(100,t.getValue(new Location(new int[]{0})),0);

        List<Location> locs = new ArrayList<>();
        locs.add(new Location(new int[]{1,2}));
        locs.add(new Location(new int[]{-1}));
        locs.add(new Location(new int[]{1000}));
        for(Location l:locs) {
            try {
                t.setValue(l, 5);
                Assert.assertTrue(false);
            } catch (EAIException e) {
                Assert.assertTrue(true);
            }
            try {
                t.getValue(l);
                Assert.assertTrue(false);
            } catch (EAIException e) {
                Assert.assertTrue(true);
            }
        }

        Assert.assertEquals(5,t.width());
        Assert.assertEquals(-1,t.height());
        Assert.assertEquals(-1,t.depth());
        Assert.assertEquals(5,t.numOfD(0));
        Assert.assertEquals(-1,t.numOfD(-1));
        Assert.assertEquals(-1,t.numOfD(1));
        Assert.assertEquals(5,t.numValues());
    }

    @Test
    public void add() {
        Tensor t1 = create(1,1,3);
        Tensor t2 = create(4,4,3);
        t1.add(t2);

        for(int i=0;i<t1.numValues();i++) {
            Location loc = new Location(new int[]{i});
            Assert.assertEquals(5, t1.getValue(loc), 0);
        }
    }

    @Test
    public void sliceSubD() {
        Tensor t = create(1,1,10);
        t.setValue(new Location(new int[]{1}),5);
        Tensor t0 = t.sliceSubD(0);
        Tensor t1 = t.sliceSubD(1);

        Assert.assertEquals(1,t0.getValue(new Location(new int[0])),0);
        Assert.assertEquals(0,t0.numDimensions());
        Assert.assertEquals(5,t1.getValue(new Location(new int[0])),0);
        Assert.assertEquals(0,t1.numDimensions());
    }

    @Test
    public void max() {
        Tensor t1 = create(1,1,3);
        Location loc = new Location(new int[]{1});
        t1.setValue(loc,4);
        Assert.assertEquals(4,t1.max(),0);
    }

    @Test
    public void slice2D() {
        Tensor t = create(1,1,3);
        try {
            t.slice2D(0,0,0,0);
            Assert.assertTrue(false);
        }catch(EAIException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void flat2D() {
        Tensor t = create(1,1,5);
        Tensor t2 = t.flat2D();
        Assert.assertEquals(t.numValues(),t2.numValues());
        Assert.assertEquals(2,t2.numDimensions());

        for(int i=0;i<t2.numValues();i++){
            Location loc = new Location(new int[]{i,0});
            Assert.assertEquals(1,t2.getValue(loc),0);
        }
    }

    @Test
    public void padding() {
        Tensor t = create(1,1,5);
        t.padding(5,10);

        Assert.assertEquals(15,t.numValues());

        double[] valids = new double[]{10,10,10,10,10,1,1,1,1,1,10,10,10,10,10};


        Assert.assertArrayEquals(valids,((Tensor1)t)._value,0);
    }


    @Test
    public void func() {
        Tensor t = create(1,1,5);
        Location loc = new Location(new int[]{1});
        t.setValue(loc,-1);

        t.func(new ReluFunction(),true);
        double[] valids = new double[]{1,0,1,1,1};


        Assert.assertArrayEquals(valids,((Tensor1)t)._value,0);
    }

    @Test
    public void conv() {

        Tensor t1 = create(2,2,5);
        Tensor t2 = create(5,5,5);


        Tensor0 t3 = (Tensor0)t1.conv(t2);
        Location loc = new Location(new int[0]);
        Assert.assertEquals(50,t3.getValue(loc),0);

    }

    @Test
    public void product() {
        Tensor t1 = create(2,2,5);
        Tensor t2 = create(5,5,5);


        Tensor t3 = t1.matmul(t2);
        Assert.assertEquals(1,t3.numDimensions());
        Assert.assertEquals(5,t3.numValues());
        for(int i=0;i<t3.numValues();i++){
            Location loc = new Location(new int[]{i});
            Assert.assertEquals(10,t3.getValue(loc),0);
        }
    }

    @Test
    public void flat1D() {
        Tensor t1 = create(2,2,5);

        double[] flats = t1.flat1D();
        double[] valids = new double[]{2,2,2,2,2};

        Assert.assertArrayEquals(valids,flats,0);
    }
    
}