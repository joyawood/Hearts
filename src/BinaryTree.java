import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BinaryTree {

	public static void main(String[] args) {
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8,9 };
		Node root = buildSubtree(numbers, 0, numbers.length - 1);
		//print(root);
		listLevels(root);

	}

	private static void print(Node root) {
		System.out.println("\nin order: ");
		inOrderPrint(root);
		System.out.println("\npre order: ");
		preOrderPrint(root);
		System.out.println("\npost order: ");
		postOrderPrint(root);
	}

	private static void inOrderPrint(Node root) {
		if (root.left != null) {
			inOrderPrint(root.left);
		}
		System.out.println(root.value);
		if (root.right != null) {
			inOrderPrint(root.right);
		}
	}

	private static void preOrderPrint(Node root) {
		System.out.println(root.value);
		if (root.left != null) {
			preOrderPrint(root.left);
		}
		if (root.right != null) {
			preOrderPrint(root.right);
		}
	}

	private static void postOrderPrint(Node root) {
		if (root.left != null) {
			postOrderPrint(root.left);
		}
		if (root.right != null) {
			postOrderPrint(root.right);
		}
		System.out.println(root.value);
	}

	private static Node buildSubtree(int[] array, int start, int end) {
		int middle = (start + end) / 2;
		Node newNode = new Node(array[middle]);

		if (start != middle) {
			newNode.setLeftChild(buildSubtree(array, start, middle - 1));
		}
		if (end != middle) {
			newNode.setRightChild(buildSubtree(array, middle + 1, end));
		}
		return newNode;
	}

	private static void listLevels(Node root) {
		List<LinkedList<Node>> levels = new ArrayList<>();
		LinkedList<Node> levelOne = new LinkedList();
		levelOne.add(root);
		levels.add(levelOne);
		int index = -1;
		boolean next = true;
		while (next) {
			index++;
			LinkedList<Node> previousLevel = levels.get(index);
			LinkedList<Node> currentLevel = new LinkedList();
			next = false;
			for (int i = 0; i < previousLevel.size(); i++) {
				Node current = previousLevel.get(i);
				if (current.left != null) {
					currentLevel.add(current.left);
					next = true;
				}
				if (current.right != null) {
					currentLevel.add(current.right);
					next = true;
				}
			}
			if(next){
				levels.add(currentLevel);
			}
		}
		int count = 1;
		for(LinkedList<Node> level: levels){
			System.out.println("\nlevel " + count);
			count ++;
			for(Node node: level){
				System.out.print(node.value+" ");
			}
		}
	}
}
