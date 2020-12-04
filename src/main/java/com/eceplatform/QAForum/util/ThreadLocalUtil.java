package com.eceplatform.QAForum.util;

public class ThreadLocalUtil<T> {

    private final ThreadLocal<T> threadlocal = new ThreadLocal<>();

    public void setValue(T value){
        threadlocal.set(value);
    }

    public T getValue(){
        return threadlocal.get();
    }

    public void clear() { threadlocal.remove(); }

}
