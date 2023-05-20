import Instruments.Hash;
import Instruments.LinkedStack;

import java.io.File;
import java.util.*;

public class NewApp{
    static HashMap<String,Vertice> g = new HashMap<>();
    public static void main(String[] args) {
        Scanner scan ;
        while(true) {
        try {
            scan = new Scanner(new File("main/src/resources/DB.txt"));
        }catch (Exception e){
            scan =new Scanner(System.in);
        }
            while (scan.hasNextLine()) {
                String row = scan.nextLine();
                String verticeName = row.substring(row.indexOf('.') + 1, row.indexOf('_')).trim().toLowerCase();
                Vertice v = g.computeIfAbsent(verticeName, s -> new Vertice()).setWestness(Integer.parseInt((row.contains("!")) ? row.substring(0, row.indexOf('!')) : row.substring(0, row.indexOf('.'))));
                String[] neighbourNames = row.substring(row.indexOf('_') + 1, row.indexOf(']')).split(",");
                String[] distances = row.substring(row.indexOf(']') + 1).split(",");
                for (int i = 0; i < neighbourNames.length; i++) {
                    Vertice neighbour = g.computeIfAbsent(neighbourNames[i].trim().toLowerCase(), s -> new Vertice());
                    neighbour.setWestness(v.westness + 1);
                    v.neighbours.putIfAbsent(neighbourNames[i].trim().toLowerCase(), Double.valueOf(distances[i].trim()));
                    neighbour.neighbours.putIfAbsent(verticeName, Double.valueOf(distances[i].trim()));
                }
            }
            scan = new Scanner(System.in);

            String from = scan.nextLine().trim().toLowerCase();
            String where = scan.nextLine().trim().toLowerCase();
        /*
        for (String cur : g.keySet()){
            System.out.println(cur+" :");
            for(String n : g.get(cur).neighbours.keySet()){
                System.out.println(n);
            }
            System.out.println(" ");
        }
        */
            ///////////////////////////////////BELMAN FORD
            HashMap<String, Double> bl = new HashMap<>();
            for (String names : g.keySet()) {
                if (!names.equals(from)) bl.put(names, Double.MAX_VALUE);
                else bl.put(names, 0.);
            }
            Stack<String> currentOnes;
            HashSet<String> visited;
            for (int i = 0; i < g.size() - 1; i++) {
                currentOnes = new Stack<>();
                visited = new HashSet<>();
                currentOnes.add(from);
                visited.add(from);
                while (!currentOnes.isEmpty()) {
                    String current = currentOnes.pop();
                    Vertice v = g.get(current);
                    double distanceToCurrent = bl.get(current);
                    for (String neighbours : v.neighbours.keySet()) {
                        if (visited.add(neighbours))
                            currentOnes.add(neighbours);
                        if (bl.get(neighbours) > distanceToCurrent + v.neighbours.get(neighbours)) {
                            bl.put(neighbours, distanceToCurrent + v.neighbours.get(neighbours));
                        }
                    }
                }
            }
            System.out.println(bl.get(where));

            ///////////////////////////////////DIJKSTRA
            LinkedList<Pack> d = new LinkedList<>();
            List<String> pp = new ArrayList<>();
            pp.add(from);
            d.add(new Pack(0, from, pp ));
            visited = new HashSet<>();
            visited.add(from);
            while (!d.get(0).name.equals(where)) {
                Vertice cur = g.get(d.get(0).name);
                for (String n : cur.neighbours.keySet()){
                    List<String> pptemp = new ArrayList<>(d.get(0).path);
                    pptemp.add(n);
                    if (visited.add(n))
                        d.add(new Pack(cur.neighbours.get(n) + d.get(0).val, n,pptemp));
                }
                d.remove(0);
                Collections.sort(d);
            }
            System.out.println(d.get(0).val);
            System.out.println("");
            for(String ans : d.get(0).path){
                System.out.println(ans);
            }

        }
    }
}
class Pack implements Comparable<Pack>{
    String name;
    double val;
    List<String> path ;
    Pack(double val, String name,List<String> p ){
        this.val=val;
        this.name=name;
        path=p;
    }
    @Override
    public int compareTo(Pack p){
        if(p.val<this.val){
            return  1;
        }else if(p.val>this.val){
            return -1;
        }else{
            return 0;
        }
    }
}