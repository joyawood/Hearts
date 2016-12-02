
public class Node {
	Node left = null;
	Node right = null;
	int value;
	
	public Node(int value){
		this.value = value;
	}
	public void setLeftChild(Node left){
		this.left=left;
	}
	public void setRightChild(Node right){
		this.right=right;
	}
	public boolean isLeaf(){
		
		return left == null && right ==null;
	}

}
