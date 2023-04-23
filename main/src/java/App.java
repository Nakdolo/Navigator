import Instruments.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException {
        Hash<String, Node> g = new Hash<>(); // graph
        List<Node> hasMetro = new List<>();

        Scanner scan = new Scanner(new File("C:\\Users\\Acer\\Desktop\\Prep\\Navigator\\main\\src\\resources\\DB.txt"));
        String line ;
        String locationName;
        Node cur;
        while(scan.hasNextLine()){
            line=scan.nextLine();
            String name = line.substring(line.indexOf(".") + 1, line.indexOf("_")).trim();
            cur=g.getOrPut(name,new Node());
            cur.setName(name);
            if(line.contains("!")){
                hasMetro.add(cur);
                cur.setWestness(Integer.parseInt(line.substring(0,line.indexOf("!"))));
            }else{
                cur.setWestness(Integer.parseInt(line.substring(0,line.indexOf("."))));
            }
            String busStops = line.substring(line.indexOf("_")+1,line.indexOf("]")).trim();
            String distances = line.substring(line.indexOf("]")+1).trim();

            while(busStops.contains(",")){
                name=busStops.substring(0,busStops.indexOf(",")).trim();
                double distance=Double.valueOf(distances.substring(0,distances.indexOf(",")));
                Node temp = g.getOrPut(name,new Node());
                if(!alreadyThere(cur.getNeighbors(),temp))
                 cur.addNeighbors(temp,distance);
                 temp.setName(name);
                 if(!alreadyThere(temp.getNeighbors(),cur))
                    temp.addNeighbors(cur,distance);
                 busStops=busStops.substring(busStops.indexOf(",")+1).trim();
                 distances=distances.substring(distances.indexOf(",")+1).trim();
            }
            name=busStops.trim();
            if (!alreadyThere(cur.getNeighbors(),g.getOrPut(name,new Node())))
                cur.addNeighbors(g.getOrPut(name,new Node()),Double.valueOf(distances));
            g.getOrPut(name,new Node()).setName(name);
            if(!alreadyThere(g.getOrPut(name,new Node()).getNeighbors(),cur))
                g.getOrPut(name,new Node()).addNeighbors(cur,Double.valueOf(distances));
        }
        scan= new Scanner(System.in);
        System.out.println("FROM WHERE (COPYING PREFERRED): ");
        String from =scan.nextLine().trim();
        System.out.println("TO WHERE (COPYING PREFERRED): ");
        String where = scan.nextLine().trim();
        ////////////////////BELMAN FORD\\\\\\\\\\\\\\\\\\\\\\\

        Hash<String, MyDouble> bf = new Hash<>();

        ///// NAME OF THE PLACE AND DISTANCE TO IT EVERYTHING IS PRETTY FAR EVEN UNREACHABLE AT FIRST EXCEPT THE "from" POINT

        for(int i = 0;i<g.size();i++){
            if(!g.getSs().get(i).equals(from)){
                bf.getOrPut(g.getSs().get(i), new MyDouble(10000000000.0)).setValue(10000000000.0);
            }else{
                bf.getOrPut(g.getSs().get(i), new MyDouble(10000000000.0)).setValue(0);
            }
            System.out.println(i + " "+bf.getOrPut(g.getSs().get(i),new MyDouble(10000000000.0) ).getValue());
        }

        ////// THEN THE ACTUAL ALGORITHM STARTS

        LinkedStack<String> haveWay ;
        Node temp ;
        for (int i=0;i<g.size();i++){
            System.out.println(g.getGs().get(i).getName()+":");
            for(int j = 0;j<g.getGs().get(i).getNeighbors().size();j++){
                System.out.println(g.getGs().get(i).getNeighbors().get(j).getName()+" : "+g.getGs().get(i).getDistance().get(j));
            }
            System.out.println("-------------------------");
        }
        List<String> tempHash;
        for(int i = 0;i<g.size()-1;i++){
            haveWay = new LinkedStack<>();
            haveWay.add(from);
            tempHash=new List<>();
            while(haveWay.size() != 0){
                temp=g.getOrPut(haveWay.get(),new Node());
                tempHash.add(temp.getName());
                for(int j = 0;j<temp.getNeighbors().size();j++){
                    if(!visited(tempHash,temp.getNeighbors().get(j).getName())){
                        if (bf.getOrPut(temp.getNeighbors().get(j).getName(), new MyDouble(0)).getValue() > bf.getOrPut(temp.getName(), new MyDouble(0)).getValue() + temp.getDistance().get(j))
                            bf.getOrPut(temp.getNeighbors().get(j).getName(), new MyDouble(0)).setValue(bf.getOrPut(temp.getName(), new MyDouble(0)).getValue() + temp.getDistance().get(j));
                        haveWay.add(temp.getNeighbors().get(j).getName());
                    }
                }
            }
        }
        System.out.println("HERE IS THE DISTANCE : "+bf.getOrPut(where,new MyDouble(0)).getValue());

        ////////////////////DAISHA\\\\\\\\\\\\\\\\\\\\\\\


    }
    static void sort(LinkedStack<String> names , LinkedStack<Double> values){
        List<String> namesList = new List<>();
        List<Double> valuesList= new List<>();
        int size = names.size();
        for(int i = 0;i<size;i++){
            namesList.add(names.get());
            valuesList.add(values.get());
        }
        for(int i = 0 ;i<size-1;i++){
            for(int j =0;j<size-1;j++){
                if(valuesList.get(j) >valuesList.get(j+1)){
                    String tempS = namesList.get(j);
                    Double tempD = valuesList.get(j);
                    valuesList.set(j,valuesList.get(j+1));
                    namesList.set(j,namesList.get(j+1));
                    valuesList.set(j+1,tempD);
                    namesList.set(j+1,tempS);
                }
            }
        }
        for (int i = 0 ;i<size;i++){
            names.add(namesList.get(i));
            values.add(valuesList.get(i));
        }
    }
    static boolean visited(List<String> all,String name){
        for(int i = 0;i<all.size();i++){
            if(all.get(i).equals(name)){
                return true;
            }
        }
        return false;
    }
    static boolean alreadyThere(List<Node> all, Node n ){
        for(int i =0;i<all.size();i++){
            if(n==all.get(i)){
                return true;
            }
        }
        return  false;
    }
}
