package com.github.anzewei.parallaxbacklayout;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * ParallaxBackLayout
 *
 * @author An Zewei (anzewei88[at]gmail[dot]com)
 * @since ${VERSION}
 */

public class LinkedStack<K, V> {
    private LinkedList<K> mLinkedList = new LinkedList<>();
    private LinkedHashMap<K, V> mTraceInfoHashMap = new LinkedHashMap<>();

    public void put(K k, V v) {
        mLinkedList.add(k);
        mTraceInfoHashMap.put(k, v);
    }

    public void remove(K k) {
        mLinkedList.remove(k);
        mTraceInfoHashMap.remove(k);
    }

    public K before(K k) {
        int index = mLinkedList.indexOf(k);
        if (index < 1)
            return null;
        return mLinkedList.get(index - 1);
    }

    public V get(K k){
        return mTraceInfoHashMap.get(k);
    }
    public K getKey(int index){
        return mLinkedList.get(index);
    }

    public int size(){
        return mLinkedList.size();
    }
}
