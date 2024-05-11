import java.util.Iterator;

public class ST<Key extends Comparable<Key>, Value> {
	private Node root;

	private class Node {
		private Key key;
		private Value value;
		private Node left, right;
		private int size;

		public Node(Key key, Value value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}

		@Override
		public String toString() {
			return "(" + key + ")";
		}

	}

	public ST() { // Initialise an empty ordered symbol table
		//root is already null so it is empty already
	}

	public void put(Key key, Value val) { // Put the key-value pair into this table
		if (key == null)
			throw new NullPointerException("Key is Null");
		root = put(root, key, val);
	}

	private Node put(Node node, Key key, Value value) {
		if (node == null)
			return new Node(key, value,1);
		int compare = key.compareTo(node.key);
		if (compare < 0)
			node.left = put(node.left, key, value);
		else if (compare > 0)
			node.right = put(node.right, key, value);
		else
			node.value = value;
		 node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	public Value get(Key key) { // Get the value paired with key (or null)
		if (key == null)
			throw new NullPointerException("Key is Null");
		return get(root, key);
	}

	private Value get(Node node, Key key) {
		if (node == null)
			return null;

		int compare = key.compareTo(node.key);
		if (compare < 0)
			return get(node.left, key);
		else if (compare > 0)
			return get(node.right, key);
		else
			return node.value;
	}

	public void delete(Key key) { // Remove the pair that has this key
		if (key == null)
			throw new NullPointerException("Key is Null");
		root = delete(root, key);
	
	}


	
	   private Node delete(Node node, Key key) {
	        if (node == null) return null;

	        int cmp = key.compareTo(node.key);
	        if      (cmp < 0) node.left  = delete(node.left,  key);
	        else if (cmp > 0) node.right = delete(node.right, key);
	        else {
	            if (node.right == null) return node.left;
	            if (node.left  == null) return node.right;
	            Node t = node;
	            node.key = min(t.right);
	            node.right = deleteMin(t.right);
	            node.left = t.left;
	        }
	        node.size = size(node.left) + size(node.right) + 1;
	        return node;
	    }

	public boolean contains(Key key) { // Is there a value paired with the key?
		if (key == null)
			throw new NullPointerException("Key is Null");
		return get(key) != null;
	}

	public boolean isEmpty() { // Is this symbol table empty?
		return root == null;
	}

	public int size() { // Number of key-value pairs in this table
		return size(root);

	}

	private int size(Node node) {
		if (node == null)
			return 0;
		return node.size;
	}

	public Key min() {
		return min(root);
	}

	private Key min(Node node) {
		if (node == null)
			return null;
		while (node.left != null) {
			node = node.left;
		}
		return node.key;

	}

	public Key max() {
		if (root == null)
			return null;
		Node node = root;
		while (node.right != null) {
			node = node.right;
		}
		return node.key;

	}

	public Key floor(Key key) { // Largest key less than or equal to key
		if (key == null)
			throw new NullPointerException(" Key is null");
		if (isEmpty())
			throw new NullPointerException(" ST is Empty");
		Node node = floor(root, key);
		if (node == null) {
			throw new IllegalArgumentException("Argument is to small");
		}
		return node.key;
	}

	private Node floor(Node node, Key key) {// Largest key less than or equal to key
		if (isEmpty())
			return null;
		int cmp = key.compareTo(node.key);
		if (cmp > 0) {
			Node temp = floor(node.right, key);
			if (temp != null) {
				return temp;
			} else {
				return node;
			}
		}
		return node;

	}

	public Key ceiling(Key key) {// Smallest key greater than or equal to key
		if(key == null) {
			throw new IllegalArgumentException("U Need to insert a valid Key");
			}
		Node node = ceiling(root, key);
		if( node == null) {
			throw new NullPointerException("There's no Node with that key value");
		}
		return node.key;
		
	}
	
	private Node ceiling( Node node, Key key) {
		if(node== null) return null;
		
		int compare = key.compareTo(node.key);
		if(compare ==0) {
			return node;
		}else if(compare > 0){
			return ceiling(node.right,key);
		}
		Node temporary = ceiling(node.left,key);
		if(temporary != null) {
			return temporary;
		}
		return node;
	}

	public int rank(Key key) {// Number of keys less than key
		if (key == null) {
			throw new NullPointerException("Key is null");
		}
		return rank(root, key);
	}

	private int rank(Node node, Key key) {
		if (node == null) {
			throw new NullPointerException("St is Empty");
		}
		int compare = key.compareTo(node.key);
		if (compare > 0) {
			return 1 + size(node.left) + size(node.right);
		}

		if (compare < 0) {
			return size(node.left);
		}
		return size(node.left);

	}

	public Key select(int k) { // Get a key of rank k
		if(k < 0 || k >= size()) { throw new IllegalArgumentException("Input Value invalid");}
	
		return select(root,k).key;
	}
	
	private Node select( Node node, int k) {
		if(node == null) return null;
		int leftSize = size(node.left);
		if(leftSize == k ) {
			return node;
		}else if( leftSize > k) {
			return select(node.left,k);
		}else {
			return select(node.left, k - leftSize -1);
		}
		
	}

	public void deleteMin() { // Delete the pair with the smallest key
		root = deleteMin(root);
	}

	private Node deleteMin(Node node) {
		if(node == null) { throw new NullPointerException	("ST is Empty");}
		
		if (node.left == null)
			return node.right;
		node.left = deleteMin(node.left);
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	public void deleteMax() {// Delete the pair with the largest key
		root = deleteMax(root);
	}

	private Node deleteMax(Node node) {
		if(node == null) { throw new NullPointerException("ST is Empty");}
		if(node.right == null) {
			return node.left;
		}
		node.right = deleteMax(node.right);
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	public int size(Key lo, Key hi) { // Number of keys in [lo, hi]
		if(lo == null) throw new IllegalArgumentException("First Argument is null");
		if(hi == null) throw new IllegalArgumentException("Second Argument is null");
		
		return size(root,lo,hi);
	}
	private int size(Node node, Key lo, Key hi) {
		if(node == null) return 0;
		if(node.key.compareTo(lo)< 0 ) {
			return size(node.right,lo,hi);
	}
		if(node.key.compareTo(hi)>0) {
			return size(node.left,lo,hi);
		}
		return 1 + size(node.left) + size(node.right);
	}
	

	private void inorder(Node node, Queue<Key> q) {
		if (node == null) {
			return;
		}
		inorder(node.left, q);
		q.enqueue(node.key);
		inorder(node.right, q);
	}
	private void inorder ( Node node, Queue<Key> q, Key lo, Key hi) {
	if(node == null) return;
	int compareLo = lo.compareTo(node.key);
	int compareHigh = hi.compareTo(node.key);
	
	if( compareLo <  0) {
		inorder(node.left,q,lo,hi);
	} else if(compareHigh > 0) {
		inorder(node.right,q,lo,hi);
	} else {
		q.enqueue(node.key);
	}
	
	}

	public Iterable<Key> keys(Key lo, Key hi) { // Keys in [lo, hi] in sorted order

		Queue<Key> q = new Queue<Key>();
		inorder(root,q,lo,hi);
		return q;
	}

	public Iterable<Key> keys() { // All keys in the table, in sorted order
		
		Queue<Key> q = new Queue<Key>();
		inorder(root, q);
		return q;
	}
	@Override 
	public String toString() {
		return toString(root);
	}
	private  String toString(Node node) {
		if(node == null) return "";
		else {
		return "["+ toString(node.left) + " " + node + " "+ toString(node.right)+"]";
	}
	}


	public static void main(String[] agrs) {
		ST<Integer, String> st = new ST<Integer, String>();
		
		st.put(23, "http://www.yale.edu");
		st.put(13, "http://www.google.com");
		st.put(5, "http://www.youtube.com");
		st.put(50, "http://www.sic.pt");
		st.put(2,"https://pt.wikipedia.org");
		st.put(3, "http://www.youtube.com");
		
		Iterator<Integer> it = st.keys().iterator();
		while(it.hasNext()) {
			System.out.print(it.next()+ " ; ");
		}
		System.out.println();
		System.out.println();
		st.delete(st.select(3));
		System.out.println("After removing Key 3:\n " + st);
		System.out.println();
		System.out.println("floor(30): " + st.floor(30));
		
		System.out.println("Ceiling of key 20: " + st.ceiling(20));
	
		System.out.println("get(13): " + st.get(13));
		System.out.println("get(5): " + st.get(5));


		System.out.println("is Empty: " + st.isEmpty());

		System.out.println("size: " + st.size());

		System.out.println("min: " + st.min());
	
		System.out.println("max: " + st.max());

		System.out.println("Size between key of 2 and key of 3 : " + st.size(st.select(2),st.select(3)));
		
		System.out.println("floor(30): " + st.floor(30));
	
		System.out.println("Ceiling of key 20: " + st.ceiling(20));
		System.out.println();
		System.out.println(st);
		System.out.println();
		st.deleteMax();
		System.out.println("Depois de remover o máximo: "+ st);
		System.out.println();
		st.deleteMin();
		System.out.println("Depois de remover o mínimo:  "+ st);
		System.out.println();
		System.out.println("Rank(23): " + st.rank(23));
		System.out.println("Key(2): " + st.select(2));
		System.out.println("size: " + st.size());
		System.out.println("min: " + st.min());
		System.out.println("max: " + st.max());
		
	}
}
