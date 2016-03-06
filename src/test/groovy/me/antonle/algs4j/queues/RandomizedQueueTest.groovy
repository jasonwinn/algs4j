package me.antonle.algs4j.queues

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
}
