package samsung.q8

import org.junit.Assert.*
import org.junit.Test

class SlopeTest {
    @Test
    fun cannotPass() {
        val slope = Slope()

        assertFalse(slope.canPass(intArrayOf(3, 1, 1, 1), 2))
        assertFalse(slope.canPass(intArrayOf(2, 1, 1, 2), 2))
        assertFalse(slope.canPass(intArrayOf(3, 1, 1, 1), 2))
        assertFalse(slope.canPass(intArrayOf(2, 1), 2))
        assertFalse(slope.canPass(intArrayOf(3, 2, 1), 2))
        assertFalse(slope.canPass(intArrayOf(1, 1, 2, 3, 3, 3), 2))
        assertFalse(slope.canPass(intArrayOf(2, 1, 1, 1, 2), 2))
        assertFalse(slope.canPass(intArrayOf(3, 2, 2, 1), 2))
        assertFalse(slope.canPass(intArrayOf(1, 1, 2, 1), 2))
    }

    @Test
    fun canPass() {
        val slope = Slope()

        assertTrue(slope.canPass(intArrayOf(1, 1, 1, 1), 2))
        assertTrue(slope.canPass(intArrayOf(2, 1, 1), 2))
        assertTrue(slope.canPass(intArrayOf(3, 2, 2, 1, 1, 1), 2))
        assertTrue(slope.canPass(intArrayOf(3, 2, 2, 1, 1), 2))
        assertTrue(slope.canPass(intArrayOf(1, 1, 1, 2, 2, 3), 2))
    }

    @Test
    fun case1() {
        val slope = Slope()

        assertEquals(3, slope.solve(
            arrayOf(
                intArrayOf(3, 3, 3, 3, 3, 3),
                intArrayOf(2, 3, 3, 3, 3, 3),
                intArrayOf(2, 2, 2, 3, 2, 3),
                intArrayOf(1, 1, 1, 2, 2, 2),
                intArrayOf(1, 1, 1, 3, 3, 1),
                intArrayOf(1, 1, 2, 3, 3, 2)
            ), 2
        ))
    }

    @Test
    fun case2() {
        val slope = Slope()

        assertEquals(7, slope.solve(arrayOf(
            intArrayOf(3, 2, 1, 1, 2, 3),
            intArrayOf(3, 2, 2, 1, 2, 3),
            intArrayOf(3, 2, 2, 2, 3, 3),
            intArrayOf(3, 3, 3, 3, 3, 3),
            intArrayOf(3, 3, 3, 3, 2, 2),
            intArrayOf(3, 3, 3, 3, 2, 2)
        ), 2))
    }

    @Test
    fun case3() {
        val slope = Slope()

        val input = "6 3\n" +
            "3 2 1 1 2 3\n" +
            "3 2 2 1 2 3\n" +
            "3 2 2 2 3 3\n" +
            "3 3 3 3 3 3\n" +
            "3 3 3 3 2 2\n" +
            "3 3 3 3 2 2"

        assertEquals(3,
            slope.solve(slope.parseInput(input)))
    }

    @Test
    fun case4() {
        val slope = Slope()

        val input = "6 1\n" +
            "3 2 1 1 2 3\n" +
            "3 2 2 1 2 3\n" +
            "3 2 2 2 3 3\n" +
            "3 3 3 3 3 3\n" +
            "3 3 3 3 2 2\n" +
            "3 3 3 3 2 2"

        assertEquals(11,
            slope.solve(slope.parseInput(input)))
    }
}