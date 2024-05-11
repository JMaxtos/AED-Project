import java.util.Iterator;

public class Queue<Item> implements Iterable<Item>{
	private Node first;
	private Node last;
	private int n = 0;

	private class Node {
		Item item;
		Node next;
	}

	public boolean isEmpty() {//eficiência Temporal : ~1  Eficiência espacial : ~1
		return first == null;
	}

	public int size() { //eficiência Temporal : ~1  Eficiência espacial : ~1
		return n;
	}

	public void enqueue(Item item) {//eficiência Temporal : ~1  Eficiência espacial : ~1
		Node node = new Node();
		node.item = item;

		if (last == null) {
			first = node;
			last = node;

		} else {
			last.next = node;
		}
		last = node;
		n++;

	}

	public Item dequeue() { //eficiência Temporal : ~1  Eficiência espacial : ~1
		if (isEmpty()) {
			throw new IllegalStateException("Vazio");
		}
		Item item = first.item;
		if (first == last) {
			first = null;
			last = null;

		} else {
			first = first.next;
		}

		n--;
		return item;
	}

	@Override
	public Iterator<Item> iterator() { 
		
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<Item>{
		
		Node node=first;
		
		public boolean hasNext() {
		return node!=null;
		}	
		public Item next() {
			Item item = node.item;
			node= node.next;
			return item;
		}
	}
	public static void main(String[] args) {
		Queue<String> q = new Queue<String>();
		q.enqueue("To");
		q.enqueue("Be");
		q.enqueue("Or");
		
		System.out.println(q.dequeue() + " Foi removido");
		System.out.println("Tamnaho " + q.size());
		System.out.println("Está vazio? "+ q.isEmpty());
		

		Iterator<String> it = q.iterator();
		while (it.hasNext()) {
			System.out.print( it.next() + " " );
		}
	}
	
	// A classe queue tem uma eficiência  espacial linear devido a armazenar N elementos: O(N)~linear
	// A classe queue tem uma eficiência temporal constante nas suas operações
	// O Iterator tem uma eficiência temporal linear e uma eficiência espacial constante


}
