package Instruments;

public class Hash <S, G>{
    private List<S> Ss ;
    private List<G> Gs ;
    int size=0;
    public Hash(){
        Ss = new List<>();
        Gs = new List<>();
    }
    public Hash(Hash<S,G> copy){
        Ss = new List<>();
        Gs = new List<>();
        for(int i =0;i<copy.size();i++){
            Ss.add(copy.Ss.get(i));
            Gs.add(copy.Gs.get(i));
        }
    }
    public int size(){
        return size;
    }
    public G getOrPut(S s,G g) throws InstantiationException, IllegalAccessException {
        for(int i = 0;i<Ss.size();i++){
            if(Ss.get(i).equals(s)){
                return Gs.get(i);
            }
        }
        size++;
        G ans = (G)g.getClass().newInstance();
        Ss.add(s);
        Gs.add(ans);

        return ans;
    }
    public S getKey(G g){
        for(int i = 0;i<Ss.size();i++){
            if(Gs.get(i)==g){
                return Ss.get(i);
            }
        }
        return null;
    }

    public List<G> getGs() {
        return Gs;
    }

    public List<S> getSs() {
        return Ss;
    }
}
