package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.activation.ReluFunction;
import com.wang.study.ai.util.AssertUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Tensor2Test extends TensorTest{


    @Test
    public void testBase() {
        Tensor t = create(1,1,5,10);
        Assert.assertEquals(2,t.numDimensions());

        Assert.assertArrayEquals(new int[]{5,10},t.shape());

        Location loc = new Location(new int[]{1,1});
        Assert.assertEquals(1,t.getValue(loc),0);

        t.setValue(loc,10);
        Assert.assertEquals(10,t.getValue(loc),0);
        t.setValue(100);
        Assert.assertEquals(100,t.getValue(new Location(new int[]{0,0})),0);

        List<Location> locs = new ArrayList<>();
        locs.add(new Location(new int[]{1,2,3}));
        locs.add(new Location(new int[]{-1}));
        locs.add(new Location(new int[]{1,100}));
        for(Location l:locs) {
            try {
                t.setValue(l, 5);
                Assert.assertTrue(false);
            } catch (EAIException e) {
                System.out.println(e);
                Assert.assertTrue(true);
            }
            try {
                t.getValue(l);
                Assert.assertTrue(false);
            } catch (EAIException e) {
                System.out.println(e);
                Assert.assertTrue(true);
            }
        }

        Assert.assertEquals(10,t.width());
        Assert.assertEquals(5,t.height());
        Assert.assertEquals(-1,t.depth());
        Assert.assertEquals(5,t.numOfD(0));
        Assert.assertEquals(10,t.numOfD(1));
        Assert.assertEquals(-1,t.numOfD(-1));
        Assert.assertEquals(-1,t.numOfD(t.numDimensions()));
        Assert.assertEquals(50,t.numValues());
    }

    @Test
    public void add() {
        Tensor t1 = create(1,1,3,3);
        Tensor t2 = create(4,4,3,3);
        Tensor t3 = create(4,4,3,4);
        t1.add(t2);

        for(int i=0;i<t1.height();i++) {
            for(int j=0;j<t1.width();j++) {
                Location loc = new Location(new int[]{i,j});
                Assert.assertEquals(5, t1.getValue(loc), 0);
            }
        }

        try {
            t3.add(t1);
            Assert.assertTrue(false);
        }catch(EAIException e){
            System.out.println(e);
            Assert.assertTrue(true);
        }
    }

    @Test
    public void sliceSubD() {
        Tensor t = create(1,1,10,2);
        t.setValue(new Location(new int[]{1,0}),5);
        t.setValue(new Location(new int[]{1,1}),5);
        Tensor t0 = t.sliceSubD(0);
        Tensor t1 = t.sliceSubD(1);

        Assert.assertEquals(1,t0.getValue(new Location(new int[]{0})),0);
        Assert.assertEquals(1,t0.numDimensions());
        Assert.assertEquals(5,t1.getValue(new Location(new int[]{0})),0);
        Assert.assertEquals(5,t1.getValue(new Location(new int[]{1})),0);
        Assert.assertEquals(1,t1.numDimensions());
    }

    @Test
    public void max() {
        Tensor t1 = create(1,1,3,3);
        Location loc = new Location(new int[]{1,1});
        t1.setValue(loc,4);
        Assert.assertEquals(4,t1.max(),0);
    }

    @Test
    public void slice2D() {
        Tensor t = create(1,1,3,3);
        t.setValue(new Location(new int[]{0,0}),5);
        t.setValue(new Location(new int[]{1,1}),10);

        Tensor t2 = t.slice2D(0,0,1,1);

        Assert.assertEquals(2,t2.numDimensions());

        Assert.assertEquals(4,t2.numValues());

        Assert.assertEquals(5,t2.getValue(0,0),0);
        Assert.assertEquals(1,t2.getValue(0,1),0);
        Assert.assertEquals(1,t2.getValue(1,0),0);
        Assert.assertEquals(10,t2.getValue(1,1),0);

        t = create(1,1,5,5);
        t.setValue(new Location(new int[]{3,3}),5);
        t.setValue(new Location(new int[]{4,4}),10);
        t2 = t.slice2D(3,3,4,4);
        Assert.assertEquals(2,t2.numDimensions());
        Assert.assertEquals(4,t2.numValues());
        Assert.assertEquals(5,t2.getValue(0,0),0);
        Assert.assertEquals(1,t2.getValue(0,1),0);
        Assert.assertEquals(1,t2.getValue(1,0),0);
        Assert.assertEquals(10,t2.getValue(1,1),0);

        t = create(1,1,5,5);
        t.setValue(new Location(new int[]{2,2}),5);
        t.setValue(new Location(new int[]{3,3}),10);
        t2 = t.slice2D(2,2,3,3);
        Assert.assertEquals(2,t2.numDimensions());
        Assert.assertEquals(4,t2.numValues());
        Assert.assertEquals(5,t2.getValue(0,0),0);
        Assert.assertEquals(1,t2.getValue(0,1),0);
        Assert.assertEquals(1,t2.getValue(1,0),0);
        Assert.assertEquals(10,t2.getValue(1,1),0);

        try {
            t = create(1,1,5,5);
            t.slice2D(2,2,5,5);
        }catch(EAIException e){
            System.out.println(e);
            Assert.assertTrue(true);
        }

        try {
            t = create(1,1,5,5);
            t.slice2D(-1,-1,3,3);
        }catch(EAIException e){
            System.out.println(e);
            Assert.assertTrue(true);
        }

    }

    @Test
    public void flat2D() {
        Tensor t = create(3,3,2,2);
        Tensor t2 = t.flat2D();
        Assert.assertEquals(t.numValues(),t2.numValues());
        Assert.assertEquals(2,t2.numDimensions());


        for(int i=0;i<t2.height();i++){
            for(int j=0;j<t2.width();j++) {
                Assert.assertEquals(3, t2.getValue(i, j), 0);
            }
        }
    }

    @Test
    public void padding() {
        Tensor t = create(1,1,2,2);
        t.padding(2,10);

        Assert.assertEquals(36,t.numValues());
        Assert.assertArrayEquals(new int[]{6,6},t._shape);
    }


    @Test
    public void func() {
        Tensor2 t = (Tensor2)create(2,2,2,2);
        Location loc = new Location(new int[]{1,1});
        t.setValue(loc,-1);

        t.func(new ReluFunction());
        double[][] valids = new double[][]{{2,2},{2,0}};

        AssertUtil.assertEquals(valids,t._value);
    }

    @Test
    public void conv() {

        Tensor t1 = create(2,2,5,5);
        Tensor t2 = create(5,5,5,5);


        Tensor0 t3 = (Tensor0)t1.conv(t2);
        Location loc = new Location(new int[0]);
        Assert.assertEquals(250,t3.getValue(loc),0);

    }

    @Test
    public void product() {
        Tensor t1 = create(2,2,5,3);
        Tensor t2 = create(5,5,3,1);


        Tensor2 t3 = (Tensor2)t1.product(t2);
        Assert.assertEquals(2,t3.numDimensions());
        Assert.assertEquals(5,t3.numValues());
        Assert.assertArrayEquals(new int[]{5,1},t3._shape);

        double[][] valids = new double[][]{{30},{30},{30},{30},{30}};

        AssertUtil.assertEquals(valids,t3._value);
    }

    @Test
    public void flat1D() {
        Tensor t1 = create(2,2,2,2);

        double[] flats = t1.flat1D();
        double[] valids = new double[]{2,2,2,2};

        Assert.assertArrayEquals(valids,flats,0);
    }
    
}