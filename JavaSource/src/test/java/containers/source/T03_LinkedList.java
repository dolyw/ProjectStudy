package containers.source;

/**
 * LinkedList源码解析
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/26 15:21
 */
public class T03_LinkedList<E> {

    /**
     * Node节点内部类，存放当前节点，前驱节点，后继节点
     *
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * 当前集合的大小
     */
    transient int size = 0;

    /**
     * 当前集合第一个节点
     */
    transient Node<E> first;

    /**
     * 当前集合最后一个节点
     */
    transient Node<E> last;

    /**
     * 构造为空
     */
    public T03_LinkedList() {
    }



}
