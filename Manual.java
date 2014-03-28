import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents a manual.
 */
public class Manual {

	private int nEntries;
	private List<Integer>[] sucLists, predLists;
	private List<Integer> userFriendlyPerm;
	
	/**
	 * @param nEntries: number of manual entries
	 * @param sucList: lists of direct successors for each entry
	 * @param antList: lists of direct predecessors for each entry
	 */
	public Manual(int nEntries, List<Integer>[] sucLists,
            List<Integer>[] predLists){
		this.nEntries = nEntries;
		this.predLists = predLists;
		this.sucLists = sucLists;
		userFriendlyPerm = new ArrayList<Integer>();
	}
	
	private void topologicalSort(){
		 ReadyList<Integer> ready = new ReadyList<Integer>(predLists);
	     int[] inCounter = new int[nEntries];	         
	     int head;
	        
	     for(int i = nEntries-1; i > -1 ; i--){
	    	 inCounter[i] = predLists[i].size();
	    	 if(inCounter[i] == 0){
            	ready.add(i);
	    	 }
	     }
	     while(!ready.isEmpty()){
	    	 head = ready.poll();
	    	 userFriendlyPerm.add(head);
	    	 ready.updatePositions(head);
	            
	         for(Integer i: sucLists[head]){
	        	 inCounter[i]--;
	             if(inCounter[i] == 0){
	            	 ready.add(i);
	             }
	         }
	     }
	}
	
	/**
	 * Returns an iterator for the user friendly permutation of the manual.
	 */
	public Iterator<Integer> getUserFriendlyPermutation(){
		if(userFriendlyPerm.isEmpty()){
			this.topologicalSort();
		}
		return userFriendlyPerm.iterator();
	}
	
	
	/**
	 *	Class made solely to re-implement the add method following
	 *  rules A and B of the problem.
	 */
	private class ReadyList<E> extends LinkedList<E> {

		private List<E>[] predecessors;
		private int[] ufpPositions; 
	    private int counter;
		
		public ReadyList(List<E>[] l){
			super();
			predecessors = l;
			ufpPositions = new int[l.length];	//positions range from 1 to l.length
			counter = 1;
		}
		
		/**
		 * Adds e1 to the list following rules A and B of the problem
		 */
		@Override
		public boolean add(E e1){
			boolean inserted = false;
			Iterator<E> itRList = this.iterator();
			if(this.isEmpty()){
				super.add(e1);
				return true;
			}
			while(!inserted && itRList.hasNext()){
				E e2 = itRList.next();
				
				//rule A (particular)
				if(predecessors[(Integer)e2].isEmpty() && !predecessors[(Integer)e1].isEmpty()){	
					super.add(this.indexOf((Integer)e2),e1);
					inserted = true;
				} else {
					if(!predecessors[(Integer)e1].equals(predecessors[(Integer)e2])){	//rule A
						Iterator<E> itPreds1 = predecessors[(Integer)e1].iterator();
						Iterator<E> itPreds2;
						boolean valid = true;
						while(itPreds1.hasNext() && !inserted){
							itPreds2 = predecessors[(Integer)e2].iterator();
							E next1 = itPreds1.next();
							while(itPreds2.hasNext() && valid){
								E next2 = itPreds2.next();
								if(ufpPositions[(Integer)next1] <= ufpPositions[(Integer)next2]){
									valid = false;
								}
							}
							if(valid){
								super.add(this.indexOf((Integer)e2),e1);
								inserted = true;
							}
							valid = true;
						}
					}
					if(!inserted && (Integer)e1 < (Integer)e2){ //rule B
						super.add(this.indexOf((Integer)e2),e1);
						inserted = true;
					}
				} 
			}
			if(!inserted){
				this.addLast(e1);
				inserted = true;
			}
			return inserted;
		}
		
		/**
		 * Updates the array that registers the position in the UFP for each
		 * manual entry. To be always used when an entry is added to the UFP.
		 */
		public void updatePositions(int elem){
			ufpPositions[elem] = counter++;
		}
	}
}
