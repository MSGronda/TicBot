package com.jetbrains;

import java.util.Arrays;

public class Stack<E>{                      // reinventing the wheel, its good practice
    private static final int INIT_DIM = 5;
    private E[] stack = (E[]) new Object[INIT_DIM];
    private int current, dim=INIT_DIM;
    private boolean isEmpty=true;

    public E pop(){
        if(!isEmpty){
            E resp = stack[--current];
            if(current==0)
                isEmpty = true;
            return resp;
        }
        return null;        //no throwing exceptions
    }
    public boolean isEmpty(){
        return isEmpty;
    }
    public void push(E elem){
        resize();
        isEmpty = false;
        stack[current++] = elem;
    }
    public void addFromArray(E[] array){
        for(E elem : array){
            push(elem);
        }
    }
    private void resize(){
        if(current==dim){
            stack = Arrays.copyOf(stack, dim+=INIT_DIM);
        }
    }

}
