package Instruments;

public class LinkedStack <T>{
    Unit<T> first;
    Unit<T> last ;
    int size ;
    public LinkedStack(){
       size=0;
    }
    public void add(T t){
        if(size==0){
            first=new Unit<>(t);
            first.next=last;
        }else if(size==1){
            last=new Unit<>(t);
            first.next=last;
        }else{
            last.next=new Unit<>(t);
            last=last.next;
        }
        size++;
    }
    public T get() {
        T t = first.val;
        first=first.next;
        size--;
        return t;
    }
    public int size(){
        return size;
    }

}
class Unit<T>{
    T val ;
    Unit<T> next;
    Unit(T t ){
        val = t;
    }
}
