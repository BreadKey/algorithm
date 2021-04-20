package simulation.printerqueue1966

import org.junit.Assert.*
import org.junit.Test

class PrinterQueueTest {
    @Test
    fun test1() {
        assertEquals(1, countUntilPrinted(listOf(0), 0))
    }

    @Test
    fun test2() {
        assertEquals(5, countUntilPrinted(listOf(1, 1, 9, 1, 1, 1), 0))
    }

    fun countUntilPrinted(priorities: Iterable<Int>, index: Int): Int {
        val printerQueue = PrinterQueue()

        priorities.forEach {
            printerQueue.addDocument(it)
        }

        var count = 1

        while (printerQueue.print().index != 0) {
            count++
        }

        return count
    }

    @Test
    fun cacheTet() {

    }
}