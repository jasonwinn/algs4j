package me.antonle.algs4j.queues

import edu.princeton.cs.algs4.StdRandom
import spock.lang.Specification

class RandomizedQueueTest extends Specification {

    private RandomizedQueue<String> queue

    void setup() {
        queue = new RandomizedQueue()
    }

    def "Simple test"() {
        setup:
        def set = new HashSet<String>()
        def a = "A"
        def b = "B"
        def c = "C"
        set.add(a)
        set.add(b)
        set.add(c)

        when:
        queue.enqueue(a)
        queue.enqueue(b)
        queue.enqueue(c)

        for (String s : queue) {
            println(s)
        }

        then:
        set.contains(queue.sample())
        queue.size() == 3
        set.contains(queue.dequeue())
        set.contains(queue.dequeue())
        set.contains(queue.dequeue())
        queue.isEmpty()
        queue.size() == 0
    }

    def "Thrown exception on sample if array is empty"() {
        when:
        queue.sample()

        then:
        thrown NoSuchElementException
        queue.isEmpty()

    }

    def "Random multiple enqueue/dequeue/sample"() {
        expect:
        for (int i = 0; i < 10000; i++) {
             randomAction(queue, StdRandom.uniform(5))
        }

    }

    void randomAction(RandomizedQueue<String> queue, int p) {
        int N = 1000
        switch (p) {
            case 0:
                queue.enqueue(String.valueOf(StdRandom.uniform(N)))
                break
            case 1:
                try {
                    queue.dequeue()
                } catch (NoSuchElementException e) {
                    // do nothing
                }
                break
            case 2:
                try {
                    queue.sample()
                } catch (NoSuchElementException e) {
                    // do nothing
                }
                break
            case 3:
                queue.size()
                break
            case 4:
                queue.isEmpty()
                break
        }
    }
}
