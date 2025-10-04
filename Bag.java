import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<T extends Comparable<T>> implements Iterable<T> {
    private Node<T> first;
    private int n; // Grau de saída

    private static class Node<T> {
        private T item;
        private Node<T> next;
    }

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void add(T item) {
        Node<T> newNode = new Node<>();
        newNode.item = item;

        if (first == null || item.compareTo(first.item) <= 0) {
            newNode.next = first;
            first = newNode;
        } else {
            Node<T> current = first;
            while (current.next != null && item.compareTo(current.next.item) > 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        n++;
    }

    public Iterator<T> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<T> {
        private Node<T> current;

        public LinkedIterator(Node<T> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T item = current.item;
            current = current.next;
            return item;
        }
    }
}