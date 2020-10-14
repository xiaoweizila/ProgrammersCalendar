package com.lw.programmerscalendar.utils;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SimpleArrayListBuilder<K> extends ArrayList<K> {

    @Nullable
    public SimpleArrayListBuilder adds(K key) {
         super.add(key);
        return this;
    }
    @Nullable
    public SimpleArrayListBuilder addList(List<K> map) {
        for (int i = 0; i <map.size() ; i++) {
            super.add(map.get(i));
        }
        return this;
    }
    public SimpleArrayListBuilder putList(List deslist, ObjectIteraor tStringIteraor){
        if(deslist==null){
            return this;
        }
        for (int i = 0; i <deslist.size() ; i++) {
            super.add((K) tStringIteraor.getDesObj(deslist.get(i)));
        }
        return this;
    }
    public SimpleArrayListBuilder getChildList(List<K> map,int start,int end){
        for (int i = start; i <end+1 ; i++) {
            super.add(map.get(i));
        }
        return this;
    }
    public interface ObjectIteraor<T>{
        Object getDesObj(T t);
    }
}
