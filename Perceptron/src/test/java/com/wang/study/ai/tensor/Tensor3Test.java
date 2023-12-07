package com.wang.study.ai.tensor;

import com.wang.study.ai.common.EAIException;
import com.wang.study.ai.function.activation.ReluFunction;
import com.wang.study.ai.util.AssertUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Tensor3Test extends TensorTest{


    @Test
    public void testBase() {
        Tensor t = create(1,1,5,10,7);
        Assert.assertEquals(3,t.numDimensions());

        Assert.assertArrayEquals(new int[]{5,10,7},t.shape());

        Location loc = new Location(new int[]{1,1,1});
        t.setValue(loc,10);
        Assert.assertEquals(10,t.getValue(loc),0);
        t.setValue(100);
        Assert.assertEquals(100,t.getValue(new Location(new int[]{0,0,0})),0);

        List<Location> locs = new ArrayList<>();
        locs.add(new Location(new int[]{1,2,3,4}));
        locs.add(new Location(new int[]{-1}));
        locs.add(new Location(new int[]{-1,3,3}));
        locs.add(new Location(new int[]{1,100,3}));
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

        Assert.assertEquals(5,t.depth());
        Assert.assertEquals(10,t.height());
        Assert.assertEquals(7,t.width());
        Assert.assertEquals(5,t.numOfD(0));
        Assert.assertEquals(10,t.numOfD(1));
        Assert.assertEquals(7,t.numOfD(2));
        Assert.assertEquals(-1,t.numOfD(-1));
        Assert.assertEquals(-1,t.numOfD(t.numDimensions()));

        Assert.assertEquals(350,t.numValues());
    }

    @Test
    public void add() {
        Tensor t1 = create(1,1,3,3,4);
        Tensor t2 = create(4,4,3,3,4);
        Tensor t3 = create(4,4,3,4,5);
        t1.add(t2);

        for(int i=0;i<t1.depth();i++) {
            for(int j=0;j<t1.height();j++) {
                for (int k = 0; k < t1.width(); k++) {
                    Location loc = new Location(new int[]{i, j,k});
                    Assert.assertEquals(5, t1.getValue(loc), 0);
                }
            }
        }

        try {
            t3.add(create(1,1,1));
            Assert.assertTrue(false);
        }catch(EAIException e){
            System.out.println(e);
            Assert.assertTrue(true);
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
        Tensor t = create(1,1,10,2,2);
        t.setValue(new Location(new int[]{1,1,0}),5);
        t.setValue(new Location(new int[]{1,1,1}),5);
        Tensor t0 = t.sliceSubD(0);
        Tensor t1 = t.sliceSubD(1);

        Assert.assertEquals(1,t0.getValue(0,0),0);
        Assert.assertEquals(2,t0.numDimensions());
        Assert.assertEquals(5,t1.getValue(1,0),0);
        Assert.assertEquals(5,t1.getValue(1,1),0);
        Assert.assertEquals(1,t1.getValue(0,1),0);
        Assert.assertEquals(2,t1.numDimensions());
    }

    @Test
    public void max() {
        Tensor t1 = create(1,1,3,3,3);
        Location loc = new Location(new int[]{1,1,1});
        t1.setValue(loc,4);
        Assert.assertEquals(4,t1.max(),0);
    }

    @Test
    /**
     * TODO
     */
    public void slice2D() {

        Tensor t = create(1,1,3,3,3);
        t.setValue(new Location(new int[]{0,0,0}),5);
        t.setValue(new Location(new int[]{1,1,1}),10);
        Tensor t2 = t.slice2D(0,0,1,1);
        Assert.assertEquals(3,t2.numDimensions());
        Assert.assertEquals(12,t2.numValues());
        Assert.assertEquals(5,t2.getValue(0,0,0),0);
        Assert.assertEquals(1,t2.getValue(0,1,0),0);
        Assert.assertEquals(1,t2.getValue(1,0,1),0);
        Assert.assertEquals(10,t2.getValue(1,1,1),0);


        try {
            t = create(1,1,5,5,5);
            t.slice2D(2,2,5,5);
        }catch(EAIException e){
            System.out.println(e);
            Assert.assertTrue(true);
        }

        try {
            t = create(1,1,5,5,5);
            t.slice2D(-1,-1,3,3);
        }catch(EAIException e){
            System.out.println(e);
            Assert.assertTrue(true);
        }

    }

    @Test
    public void flat2D() {
        Tensor t = create(3,3,2,2,2);
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
        Tensor t = create(1,1,2,2,3);
        t.padding(1,10);

        Assert.assertEquals(40,t.numValues());
        Assert.assertArrayEquals(new int[]{2,4,5},t._shape);
    }


    @Test
    public void func() {
        Tensor3 t = (Tensor3)create(2,2,2,2,2);
        Location loc = new Location(new int[]{0,1,1});
        t.setValue(loc,-1);
        loc = new Location(new int[]{1,1,1});
        t.setValue(loc,-1);

        t.func(new ReluFunction(),true);
        double[][] valids = new double[][]{{2,2},{2,0}};

        for(Tensor t2:t._value) {
            Tensor2 tt = (Tensor2)t2;
            AssertUtil.assertEquals(valids, tt._value);
        }
    }

    @Test
    public void conv() {

        Tensor t1 = create(2,2,5,5,2);
        Tensor t2 = create(5,5,5,5,2);


        Tensor0 t3 = (Tensor0)t1.conv(t2);
        Location loc = new Location(new int[0]);
        Assert.assertEquals(500,t3.getValue(loc),0);

    }

    @Test
    public void product() {
        Tensor t1 = create(2,2,5,5,3);
        Tensor t2 = create(5,5,5,3,1);


        Tensor3 t3 = (Tensor3)t1.matmul(t2);
        Assert.assertEquals(3,t3.numDimensions());
        Assert.assertEquals(25,t3.numValues());
        Assert.assertArrayEquals(new int[]{5,5,1},t3._shape);

        double[][] valids = new double[][]{{30},{30},{30},{30},{30}};

        for(Tensor tt:t3._value){
            Tensor2 st = (Tensor2)tt;
            AssertUtil.assertEquals(valids,st._value);
        }

    }

    @Test
    public void flat1D() {
        Tensor t1 = create(2,2,2,2,2);

        double[] flats = t1.flat1D();
        double[] valids = new double[]{2,2,2,2,2,2,2,2};

        Assert.assertArrayEquals(valids,flats,0);
    }
    
}