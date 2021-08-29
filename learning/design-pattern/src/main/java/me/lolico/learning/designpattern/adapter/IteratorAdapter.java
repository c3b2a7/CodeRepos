package me.lolico.learning.designpattern.adapter;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author lolico
 */
public class IteratorAdapter<T> implements Enumeration<T> {

    Iterator<T> iterator;

    public IteratorAdapter(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public T nextElement() {
        return iterator.next();
    }

}
