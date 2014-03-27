import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Classe so para re-implementar o metodo add de forma a considerar as regras do enunciado
 * @author ricardojrp
 */
public class MyPriorityQueue<E> extends LinkedList<E> {

	
	private List<E>[] antecessors;
	private int[] ufpPositions; 
    private int counter;
	
	public MyPriorityQueue(List<E>[] l){
		super();
		antecessors = l;
		ufpPositions = new int[l.length]; //tudo init a 0, por isso as posicoes vao de 1 a l.length
		counter = 1;
	}
	
	/**
	 * Adiciona e1 'a ready list respeitando as regras A e B do enunciado
	 */
	@Override
	public boolean add(E e1){
		boolean inserted = false;
		Iterator<E> it = super.iterator();
		if(super.isEmpty()){
			super.add(e1);
			return true;
		}
		//System.out.println("e1: "+e1);
		while(!inserted && it.hasNext()){
			E e2 = it.next();
			//System.out.println("e2: "+e2);
			if(antecessors[(Integer)e2].isEmpty() && !antecessors[(Integer)e1].isEmpty()){	//rule A (particular)
				//System.out.println("entered: rule A(particular)");
				super.add(super.indexOf((Integer)e2),e1);
				inserted = true;
			} else {
				if(!antecessors[(Integer)e1].equals(antecessors[(Integer)e2])){	//rule A
//					System.out.println("entered: rule A");
					Iterator<E> it1 = antecessors[(Integer)e1].iterator();
					Iterator<E> it2;
					boolean valid = true;
					while(it1.hasNext() && !inserted){
						it2 = antecessors[(Integer)e2].iterator();
						E next1 = it1.next();
						while(it2.hasNext() && valid){
							//System.out.println("...");
							E next2 = it2.next();
							//System.out.println(ufpPositions[(Integer) next1]+" "+ufpPositions[(Integer) next2]);
							if(ufpPositions[(Integer) next1] <= ufpPositions[(Integer) next2]){
								valid = false;
							}
						}
						
						if(valid){
							//System.out.println("entered: valid rule A");
							super.add(super.indexOf((Integer)e2),e1);
							inserted = true;
						}
						valid = true;
					}
					
				}
				if(!inserted && (Integer)e1 < (Integer)e2){ //rule B
					//System.out.println("entered: rule B");
					super.add(super.indexOf((Integer)e2),e1);
					inserted = true;
				}
			} 
		}
		if(!inserted){
			//System.out.println("entered: no rule");
			super.addLast(e1);
			inserted = true;
		}
		
		return inserted;
	}
	
	/**
	 * atualiza a posicao na user friendly permutation da entrada elem
	 */
	public void incPosition(int elem){
		ufpPositions[elem] = counter++;
	}
}
