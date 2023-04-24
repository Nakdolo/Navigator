package Instruments;

public class List <T>{
    T [] list ;
    int ind = 0;
    public int size(){
        return ind;
    }
    public List(){
      list= (T[]) new Object[2];
    }
    public void add(T t){
        if(ind == list.length){
            copyAndExpand();
        }
        list[ind]=t;
        ind++;
    }
    public boolean contains(T t){
        for (int i = 0;i<list.length;i++){if(list[i]==t){ return true; }}
        return false;
    }
    void copyAndExpand(){
       T [] old = list;
       list=(T[])new Object[list.length*2];
       for(int i = 0;i<ind;i++){
           list[i]=old[i];
       }
    }
    public void set(int i , T t){
        list[i]=t;
    }
    public T get(int i){
        return list[i];
    }
}
