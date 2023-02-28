package Instruments;

public class List <T>{
    T [] list ;
    private int size ;
    List(){
        list= (T [])new Object [2] ;
        size = 2;
    }
    T get(int i){
       return  list[i];
    }
    void add(T t){

    }

}
