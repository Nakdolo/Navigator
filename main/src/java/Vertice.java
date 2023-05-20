import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vertice {
    int westness;
    HashMap<String,Double> neighbours = new HashMap<>();
       public Vertice(int westness){
            this.westness=westness;
       }
       public Vertice(){
       }
    public Vertice setWestness(int westness){
        this.westness = westness;
        return this;
    };
}
