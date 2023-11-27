package com.wang.study.ai.tensor;

import com.wang.study.ai.util.StringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Location {
    private int[] _loc;

    public Location(int[] loc){
        _loc = loc;
    }

    public int[] loc(){
        return _loc;
    }

    public String toString(){
        return StringUtil.int2Str(_loc);
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object obj){
        if(!(obj instanceof Location)){
            return false;
        }
        return this.hashCode() == ((Location)obj).hashCode() ? true:false;
    }

    public Location subLoc(){
        int[] subs = Arrays.copyOfRange(_loc,1,_loc.length);
        Location subLoc = new Location(subs);
        return subLoc;
    }

    public static void main(String[] args){
        Location l1 = new Location(new int[]{1,2,3,4});
        Location l2 = new Location(new int[]{1,2,3,4});

        Map<Location, String> map = new HashMap<>();
        map.put(l1,"123");
        System.out.println(map.get(l2));
    }
}
