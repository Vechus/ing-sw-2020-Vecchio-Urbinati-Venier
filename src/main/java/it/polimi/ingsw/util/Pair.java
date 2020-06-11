package it.polimi.ingsw.util;

public class Pair<T1, T2> {
    private T1 v1;
    private T2 v2;

    public Pair(T1 v1, T2 v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    public T1 first(){ return this.v1; }
    public T2 second(){ return this.v2; }
}
