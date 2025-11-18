package com.utkarsh.util;

import java.util.List;
import java.util.ArrayList;

public class GenericUtil{

    public static <T> void printList(List<? extends T> list){
        for(T item : list){
            System.out.println(item);
        }
    }

    public static <T> List<T> arrayToList(T[] array){
        List<T> list = new ArrayList<>();
        for(T item : array){
            list.add(item);
        }
        return list;
    }

    public static <T> T[] listToArray(List<T> list, T[] array){
        return list.toArray(array);
    }

    public static <T extends Comparable<T>> T findMax(List<T> list){
        if(list == null || list.isEmpty()){
            return null;
        }
        T max = list.get(0);
        for(T item : list){
            if(item.compareTo(max) > 0){
                max = item;
            }
        }
        return max;
    }
}
