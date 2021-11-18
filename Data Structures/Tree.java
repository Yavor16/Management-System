import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

class Main{
    public static void main(String[] args){
      
    }
}
class BinaryTree<T>{
    private static class BinaryTreeNode<T>{
        
        public BinaryTreeNode(T value, BinaryTreeNode<T> leftChild, BinaryTreeNode<T> rightChild) {
            if (value == null) throw new IllegalArgumentException("Value cannot be null!");

            this.value = value;
        }
    }
}