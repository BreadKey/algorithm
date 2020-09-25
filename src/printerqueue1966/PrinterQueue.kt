package printerqueue1966

import java.util.*

fun main() {
    val testCaseCount = readLine()!!.toInt()
    val testCases = mutableListOf<TestCase>()

    repeat(testCaseCount) {
        val index = readLine()!!.split(' ').last().toInt()
        val priorities = readLine()!!.split(' ').map { it.toInt() }

        testCases.add(TestCase(priorities, index))
    }

    val printerQueue = PrinterQueue()

    testCases.forEach { testCase ->
        printerQueue.clear()
        testCase.priorities.forEach { priority ->
            printerQueue.addDocument(priority)
        }

        var count = 1

        while (printerQueue.print().index != testCase.index) {
            count++
        }

        println(count)
    }
}

data class TestCase(val priorities: List<Int>, val index: Int)

data class Document(val index: Int, val priority: Int)

class PrinterQueue {
    private val queue: Queue<Document> = LinkedList<Document>()

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

    fun clear() = queue.clear()
}