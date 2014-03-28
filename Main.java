
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Iterator;
 

public class Main {
	
	public static final int Min_E = 2, Max_E = 5000, Min_D = 1, Max_D = 30000;
 
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer line = new StringTokenizer(br.readLine());
        int nEntries = Integer.parseInt(line.nextToken());
        int nDependencies = Integer.parseInt(line.nextToken());
        
 
        List<Integer>[] sucLists = (List<Integer>[]) new List[nEntries];
        List<Integer>[] predLists = (List<Integer>[]) new List[nEntries];
       
 
        if (!(nEntries >= Min_E) && !(nEntries <= Max_E)) return;
        if (!(nDependencies >= Min_D) && !(nDependencies <= Max_D)) return;
 
        for (int i = 0; i < nEntries; i++){
        	sucLists[i] = new LinkedList<Integer>();
        	predLists[i] = new LinkedList<Integer>();
        }
                  
        int x, y;
        for (int i = 0; i < nDependencies; i++) {
	        line = new StringTokenizer(br.readLine());
	        x = Integer.parseInt(line.nextToken());
	        y = Integer.parseInt(line.nextToken());
 
        	sucLists[x].add(y);
        	predLists[y].add(x);
        }
 
        Manual man = new Manual(nEntries, sucLists, predLists);
        Iterator<Integer> ufp = man.getUserFriendlyPermutation();
        
        if(ufp.hasNext())
        	System.out.print(ufp.next());
        
        while (ufp.hasNext()) {
            System.out.print(" "+ufp.next());
        }
        System.out.println();
         
    }   
}