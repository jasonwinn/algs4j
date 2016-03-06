package me.antonle.algs4j.queues

import spock.lang.Specification

class DequeTest extends Specification {

    private Deque<String> deque

    void setup() {
        deque = new Deque()
    }

    def "Simple add/removeFirst test"() {
        when:
        deque.addFirst("First")
        deque.addLast("Last")
        deque.addLast("NewLast")
        deque.addFirst("NewFirst")

        then:
        "NewFirst".equals(deque.removeFirst())
        "First".equals(deque.removeFirst())
        "Last".equals(deque.removeFirst())
        "NewLast".equals(deque.removeFirst())
        deque.isEmpty()
    }

    def "Simple add/removeLast test"() {
        when:
        deque.addFirst("First")
        deque.addLast("Last")
        deque.addLast("NewLast")
        deque.addFirst("NewFirst")

        then:
        "NewLast".equals(deque.removeLast())
        "Last".equals(deque.removeLast())
        "First".equals(deque.removeLast())
        "NewFirst".equals(deque.removeLast())
        deque.isEmpty()
    }

    def "Iterator test"() {
        when:
        deque.addFirst("First")
        deque.addLast("Last")
        deque.addLast("NewLast")
        deque.addFirst("NewFirst")

        then:
        def list = new ArrayList<>()
        for (String item : deque) {
            list.add(item)
        }
        0 == list.indexOf("NewFirst")
        1 == list.indexOf("First")
        2 == list.indexOf("Last")
        3 == list.indexOf("NewLast")
        !deque.isEmpty()
        deque.size() == 4
    }
}
