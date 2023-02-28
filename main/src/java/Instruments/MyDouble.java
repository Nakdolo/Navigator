package Instruments;

public class MyDouble {
        double value ;
        public MyDouble(double value){
            this.value=value;
        }
        MyDouble(){}
        public void setValue(double value) {
            this.value = value;
        }
        public double getValue(){
            return value;
        }
}
