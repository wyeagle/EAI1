package com.wang.study.ai.tensor;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LocationTest {

    @Test
    public void testLoc() {
        Location location = new Location(new int[]{1, 2, 3});
        int[] loc = location.loc();
        assertEquals(1, loc[0]);
        assertEquals(2, loc[1]);
        assertEquals(3, loc[2]);
        assertEquals(3, loc.length);
    }

    @Test
    public void testLoc1() {
        Location location = new Location(new int[]{1, 2, 3, 4, 5});
        int[] loc = location.loc();
        assertEquals(1, loc[0]);
        assertEquals(2, loc[1]);
        assertEquals(3, loc[2]);
        assertEquals(4, loc[3]);
        assertEquals(5, loc[4]);
        assertEquals(5, loc.length);
    }

    @Test
    public void subLoc() {
        Location location = new Location(new int[]{1, 2, 3});
        Location subLocation = location.subLoc();
        int[] loc = subLocation.loc();
        assertEquals(2, loc[0]);
        assertEquals(3, loc[1]);
        assertEquals(2, loc.length);

    }

    @Test
    public void testSubLoc1(){
        Location location = new Location(new int[]{1, 2, 3, 4, 5});
        Location subLocation = location.subLoc();
        int[] loc = subLocation.loc();
        assertEquals(2, loc[0]);
        assertEquals(3, loc[1]);
        assertEquals(4, loc[2]);
        assertEquals(5, loc[3]);
        assertEquals(4, loc.length);
    }

    @Test
    public void testSubLoc2(){
        Location location = new Location(new int[]{1, 2, 3, 4, 5, 6});
        Location subLocation = location.subLoc();
        int[] loc = subLocation.loc();
        assertEquals(2, loc[0]);
        assertEquals(3, loc[1]);
        assertEquals(4, loc[2]);
        assertEquals(5, loc[3]);
        assertEquals(5, loc.length);
    }

    @Test
    public void testSubLoc3(){
        Location location = new Location(new int[]{1, 2, 3, 4, 5, 6, 7});
        Location subLocation = location.subLoc();
        int[] loc = subLocation.loc();
        assertEquals(2, loc[0]);
        assertEquals(3, loc[1]);
        assertEquals(4, loc[2]);
        assertEquals(5, loc[3]);
        assertEquals(6, loc[4]);
        assertEquals(6, loc.length);
    }

}