
package ClassLists;

import java.util.ArrayList;

/**
 *
 * @author hailiejimenez
 */


public class Node<E> {
    private String string;
    private SearchTree yes;
    private SearchTree no;

    public Node(String string) {
        this.string = string;
        this.yes = yes;
        this.no = no;
    }

    public String getString() {
        return string;
    }

    public SearchTree<E> getYes() {
        return yes;
    }

    public SearchTree<E> getNo() {
        return no;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setYes(SearchTree<E> yes) {
        this.yes = yes;
    }

    public void setNo(SearchTree<E> no) {
        this.no = no;
    }

    
}
