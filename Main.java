
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
 
 
public class Main {
 
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine());
        int nEntries = Integer.parseInt(line.nextToken());
        int nDependencies = Integer.parseInt(line.nextToken());
        int minE = 2, maxE = 5000, minD = 1, maxD = 30000;
 
        List<Integer>[] sucList = (List<Integer>[]) new List[nEntries];
        List<Integer>[] antList = (List<Integer>[]) new List[nEntries];
        List<Integer> userFriendlyPerm = new ArrayList<Integer>();
 
        if (!(nEntries >= minE) && !(nEntries <= maxE))
            System.out.println("entradas invalidas");
        if (!(nDependencies >= minD) && !(nDependencies <= maxD))
            System.out.println("dependencias invalidas");
        else {
 
            for (int i = 0; i < nEntries; i++){
                sucList[i] = new ArrayList<Integer>();
                antList[i] = new ArrayList<Integer>();
            }
             
             
            int x, y;
            for (int i = 0; i < nDependencies; i++) {
                line = new StringTokenizer(br.readLine());
                x = Integer.parseInt(line.nextToken());
                y = Integer.parseInt(line.nextToken());
 
                sucList[x].add(y);
                antList[y].add(x);
            }
        }
 
         
        topologicalSort(nEntries, sucList, antList, userFriendlyPerm);
         
        System.out.println("order:");
        Iterator<Integer> it2 = userFriendlyPerm.iterator();
 
        while (it2.hasNext()) {
            System.out.print(it2.next()+" ");
        }
        System.out.println();
         
    }
    
    protected class TieBreaker<T> implements Comparator<T>{

    	private 
    	TieBreaker(){
    		
    	}
		@Override
		public int compare(T arg0, T arg1) {
			// TODO Auto-generated method stub
			return 0;
		}
    	
    }
 
    private static void topologicalSort(int nEntries, List<Integer>[] sucList,
            List<Integer>[] antList, List<Integer> userFriendlyPerm) {
    	
    	//in this context, the deque behaves just like a stack
    	//the ArrayDeque implementation is used solely for better efficiency
        Deque<Integer> ready = new ArrayDeque<Integer>(nEntries*2);
        int[] inCounter = new int[nEntries];
         
        int first;
        //in a 1st iteration, we push the elements with no antecessors, following rule (b),
        //starting from the highest
        for(int i = nEntries-1; i > -1 ; i--){
            inCounter[i] = antList[i].size();
            if(inCounter[i] == 0){
                System.out.println("nao tenho ant: "+i);
                ready.push(i);
            }
        }
        while(!ready.isEmpty()){
            first = ready.pop();
            userFriendlyPerm.add(first);
            
             
            for(int k=0;k<sucList[first].size();k++){
                int l = sucList[first].get(k);
                inCounter[l]--;
                if(inCounter[l] == 0){
                    ready.add(l);
                }
            }
        }
    }
     
    private void funA(){
         
    }
     
    private int funB(int x, int y){
        return 0;
    }
}