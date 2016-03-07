package me.antonle.algs4j.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Randomized queue.
 * A randomized queue is similar to a stack or queue,
 * except that the item removed is chosen uniformly at random from items in the data structure.
 * <p>
 * Corner cases.
 * The order of two or more iterators to the same randomized queue must be mutually independent;
 * each iterator must maintain its own random order.
 * Throw a java.lang.NullPointerException if the client attempts to add a null item;
 * throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
 * throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
 * throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
 * <p>
 * Performance requirements.
 * Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in constant amortized time.
 * That is, any sequence of M randomized queue operations (starting from an empty queue) should take at most cM steps in the worst case, for some constant c.
 * A randomized queue containing N items must use at most 48N + 192 bytes of memory.
 * Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time;
 * and construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int N;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        N = 0;
        items = (Item[]) new Object[1];
    }

    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * return the number of items on the queue
     */
    public int size() {
        return N;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (items.length == N) {
            resize(items.length * 2);
        }
        items[N++] = item;
    }

    /**
     * remove and return a random item
     **/
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        Item item = items[i];
        items[i] = items[--N];
        items[N] = null;
        if (items.length / 4 > N) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(N)];
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Item[] newItems = (Item[]) new Object[newCapacity];
        for (int i = 0; i < N; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    /**
     * an independent iterator over items in random order
     */
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int current = 0;
            private int[] shuffledIndexes = new int[N];

            @Override
            public boolean hasNext() {
                if (current == 0) {
                    shuffleIndexes();
                }
                return current < N;
            }

            @Override
            public Item next() {
                if (current == 0) {
                    shuffleIndexes();
                }
                if (current >= N || size() == 0) {
                    throw new NoSuchElementException();
                }
                return items[shuffledIndexes[current++]];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private void shuffleIndexes() {
                for (int i = 0; i < N; i++) {
                    shuffledIndexes[i] = i;
                }
                StdRandom.shuffle(shuffledIndexes);
            }
        };
    }
}
