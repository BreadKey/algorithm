package programmers.stackqueue.printer

import java.util.*

data class Document(val index: Int, val priority: Int)

class Printer {
    private val queue = LinkedList<Document>()

    fun addDocument(priority: Int) {
        queue.add(Document(queue.size, priority))
    }

    fun print(): Document {
        while (true) {
            val document = queue.poll()

            if (queue.none { it.priority > document.priority }) {
                return document
            }

            queue.add(document)
        }
    }
}

class Solution {
    fun solution(priorities: IntArray, location: Int): Int {
        val printer = Printer()

        priorities.forEach {
            printer.addDocument(it)
        }

        var count = 1

        while (printer.print().index != location) {
            count++
        }

        return count
    }
}