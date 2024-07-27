

package ClassLists;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author hailiejimenez
 */


public class SearchTree<E> {
    private Node<E> root;

    public SearchTree(Node root) {
        this.root = root;
    }
    public SearchTree(){
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
    
    public boolean isEmpty(){
        return root == null;
    }
    
    public boolean isLeaf(){
        // root no esta vacio
        if (!this.isEmpty()){
            return root.getYes() == null && root.getNo() == null;
            // return root.getLeft().isEmpty() && root.getRight().isEmpty();
        }
        return false;
    }
    
    public void recorrerPreorden(){
        
        // 1. Imprimir a la raiz
        // 2. recorrer preorden en hijo izquierdo
        // 3. recorrer preorden en hijo derecho
        
        if (!this.isEmpty()) {
            System.out.println(">");
            // 1. Imprimir a la raiz
            System.out.println(this.root.getString());
            
            // 2. recorrer preorden en hijo izquierdo
            if (root.getYes()!=null) {
                System.out.println("yes");
                root.getYes().recorrerPreorden(); 
            }
        
            // 3. recorrer preorden en hijo derecho
            if (root.getNo()!=null) {
                System.out.println("no");
                root.getNo().recorrerPreorden(); 
            }
        }
        System.out.println(">");
    }
    
    public SearchTree<E> buildQuestionTree(Queue<String> questions) {
        if (questions.isEmpty()) {
            return null;
        }

        // Initialize the root node
        Node root = new Node(questions.poll());
        SearchTree treeFinal = new SearchTree<>();
        Node mainNode = new Node(questions.poll());
        Queue<Node> queue = new LinkedList<>();
        queue.add(mainNode);

        // Process the questions queue
        while (!questions.isEmpty()) {
            Node currentNode = queue.poll();

            // Process the "yes" branch
            if (!questions.isEmpty()) {
                String yesQuestion = questions.poll();
                Node yesNode = new Node(yesQuestion);
                currentNode.setYes(new SearchTree(yesNode));
                if (!yesQuestion.equals("ANIMAL") && !yesQuestion.equals("X")) {
                    queue.add(yesNode);
                }
            }

            // Process the "no" branch
            if (!questions.isEmpty()) {
                String noQuestion = questions.poll();
                Node noNode = new Node(noQuestion);
                currentNode.setNo(new SearchTree(noNode));
                if (!noQuestion.equals("ANIMAL") && !noQuestion.equals("X")) {
                    queue.add(noNode);
                }
            }
        }
        treeFinal.setRoot(root);
        treeFinal.root.setYes(new SearchTree<>(mainNode));
        treeFinal.root.setNo(new SearchTree<>(mainNode));
        
        return treeFinal;
    }    
}
