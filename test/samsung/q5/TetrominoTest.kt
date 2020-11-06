package samsung.q5

import org.junit.Test
import java.util.*
import kotlin.math.min
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TetrominoTest {
    @Test
    fun lMinoBuildTest() {
        val lMino = LMino()

        assertEquals(listOf(
            Point(2, 1),
            Point(1, 3),
            Point(1, 2),
            Point(1, 1)
        ), lMino.build(Point(1, 1), Direction.Up, bounds = Point(10, 10)))

        assertEquals(listOf(
            Point(1, 0),
            Point(3, 1),
            Point(2, 1),
            Point(1, 1)
        ), lMino.build(Point(1, 1), Direction.Right, bounds = Point(10, 10)))
    }

    @Test
    fun sumOfEmpty() {
        assertEquals(0,
            emptyList<Int>().sum())
    }

    @Test
    fun testCase1() {
        assertMaxEquals("1 2 3 4 5\n" +
            "5 4 3 2 1\n" +
            "2 3 4 5 6\n" +
            "6 5 4 3 2\n" +
            "1 2 1 2 1", 19)
    }

    @Test
    fun testCase2() {
        assertMaxEquals("1 2 3 4 5\n" +
            "1 2 3 4 5\n" +
            "1 2 3 4 5\n" +
            "1 2 3 4 5", 20)
    }

    @Test
    fun testCase3() {
        assertMaxEquals("1 2 1 2 1 2 1 2 1 2\n" +
            "2 1 2 1 2 1 2 1 2 1\n" +
            "1 2 1 2 1 2 1 2 1 2\n" +
            "2 1 2 1 2 1 2 1 2 1", 7)
    }

    fun assertMaxEquals(input: String, expected: Int) {
        val map = input.split('\n').map {
            it.split(' ').map { s -> s.toInt() }.toIntArray()
        }.toTypedArray()

        val calculator = MaxCalculatorByTetromino(map)

        assertEquals(expected, calculator.calculateMax())
    }
}