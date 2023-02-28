package Instruments;

public class Node extends Object{
    public Node(){
        neighbors = new List<>();
        distance = new List<>();
    }
    public Node(String name){
        neighbors = new List<>();
        distance = new List<>();
        this.name=name;
    }
    private String name ;
    private List<Node> neighbors ;
    private List<Double> distance;
    int westness ;
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWestness(int westness) {
        this.westness = westness;
    }
    public void addNeighbors(Node n , Double dist){
        neighbors.add(n);
        distance.add(dist);
    }
    public List<Node> getNeighbors(){
        return neighbors;
    }
    public List<Double> getDistance(){
        return distance;
    }
}
