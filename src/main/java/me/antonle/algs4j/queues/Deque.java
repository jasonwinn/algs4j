package me.antonle.algs4j.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Dequeue.
 * A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data structure.
 * <p>
 * Corner cases.
 * throw java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
 * <p>
 * Performance requirements.
 * Your deque implementation must support each deque operation in constant worst-case time.
 * A deque containing N items must use at most 48N + 192 bytes of memory and use space proportional to the number of items currently in the deque.
 * Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;

    public Deque() {
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        int count = 0;
        Node root = first;
        while (root != null) {
            count++;
            root = root.next;
        }
        return count;
    }

    /**
     * add the item to the front
     * newFirst <-> first <-> X <-> X <-> X <-> X <-> X <-> last
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("You can not insert null item");
        }
        Node newFirst = new Node(item);
        if (!isEmpty()) {
            newFirst.next = first;
            first.previous = newFirst;
            first = newFirst;
        } else {
            first = newFirst;
            last = newFirst;
        }
    }

    /**
     * add the item to the end
     * first <-> X <-> X <-> X <-> X <-> X <-> last <-> newLast
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("You can not insert null item");
        }
        Node newLast = new Node(item);
        if (!isEmpty()) {
            last.next = newLast;
            newLast.previous = last;
            last = newLast;
        } else {
            first = newLast;
            last = newLast;
        }
    }

    /**
     * Remove and return the item from the front
     * first = Item <-> newFirst <-> X <-> X <-> X <-> X <-> last <-> item
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldFirst = first;
        first = first.next;
        if (first != null) {
            first.previous = null;
        } else {
            last = null;
        }
        return oldFirst.item;
    }

    /**
     * Remove and return the item from the end
     * first <-> X <-> X <-> X <-> X <-> X <-> newLast <-> last = Item
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldLast = last;
        last = last.previous;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return oldLast.item;
    }

    @Override
    public Iterator<Item> iterator() {

        return new Iterator<Item>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                Item item = current.item;
                current = current.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            this.item = item;
        }
    }
}
