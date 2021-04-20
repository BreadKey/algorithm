package samsung.q6

import org.junit.Assert.*
import org.junit.Test

class LabTest {
    @Test
    fun infectTest() {
        val lab = Lab()

        assertEquals(0, lab.infect(arrayOf(intArrayOf(2, 0), intArrayOf(0, 0))))

        assertEquals(1, lab.infect(arrayOf(intArrayOf(2, 1), intArrayOf(1, 0))))

        assertEquals(27, lab.infect(parseMap("2 1 0 0 1 1 0\n" +
            "1 0 1 0 1 2 0\n" +
            "0 1 1 0 1 0 0\n" +
            "0 1 0 0 0 1 0\n" +
            "0 0 0 0 0 1 1\n" +
            "0 1 0 0 0 0 0\n" +
            "0 1 0 0 0 0 0")))
    }

    @Test
    fun calculateMaxSpaceCountTest() {
        val lab = Lab()

        assertEquals(27,
            lab.calculateMaxSpaceCount(parseMap("2 0 0 0 1 1 0\n" +
                "0 0 1 0 1 2 0\n" +
                "0 1 1 0 1 0 0\n" +
                "0 1 0 0 0 0 0\n" +
                "0 0 0 0 0 1 1\n" +
                "0 1 0 0 0 0 0\n" +
                "0 1 0 0 0 0 0")))

    }

    @Test
    fun calculateMaxSpaceCountTest2() {
        val lab = Lab()

        assertEquals(9, lab.calculateMaxSpaceCount(parseMap("0 0 0 0 0 0\n" +
            "1 0 0 0 0 2\n" +
            "1 1 1 0 0 2\n" +
            "0 0 0 0 0 2")))
    }

    @Test
    fun calculateMaxSpaceCountTest3() {
        val lab = Lab()

        assertEquals(3, lab.calculateMaxSpaceCount(parseMap("2 0 0 0 0 0 0 2\n" +
            "2 0 0 0 0 0 0 2\n" +
            "2 0 0 0 0 0 0 2\n" +
            "2 0 0 0 0 0 0 2\n" +
            "2 0 0 0 0 0 0 2\n" +
            "0 0 0 0 0 0 0 0\n" +
            "0 0 0 0 0 0 0 0\n" +
            "0 0 0 0 0 0 0 0")))
    }

    @Test
    fun calculateMaxSpaceCountTest4() {
        val lab = Lab()

        assertEquals(15, lab.calculateMaxSpaceCount(parseMap("0 0 0 0 0\n" +
            "0 0 1 0 0\n" +
            "0 0 0 0 2\n" +
            "0 0 0 0 0")))
    }

    @Test
    fun case5() {
        val lab = Lab()

        assertEquals(41, lab.calculateMaxSpaceCount(parseMap("1 0 1 0 1 0 1 0\n" +
            "0 0 0 0 1 0 1 0\n" +
            "0 1 0 1 0 0 0 1\n" +
            "1 0 0 0 0 1 0 0\n" +
            "1 0 0 1 0 0 0 0\n" +
            "0 0 1 1 0 1 0 0\n" +
            "0 0 0 0 0 0 0 2\n" +
            "0 0 1 0 0 1 1 0")))
    }

    @Test
    fun case6() {
        val lab = Lab()

        assertEquals(21, lab.calculateMaxSpaceCount(parseMap("0 0 0 1 1 0\n" +
            "0 0 0 1 0 0\n" +
            "0 0 0 0 0 0\n" +
            "0 0 0 0 0 1\n" +
            "0 1 0 0 2 0")))
    }

    @Test
    fun case7() {
        val lab = Lab()

        assertEquals(6, lab.calculateMaxSpaceCount(parseMap("0 0 0\n" +
            "0 0 0\n" +
            "0 0 0")))
    }

    fun parseMap(input: String) = input.split('\n').map { s -> s.split(' ').map { c -> c.toInt() }.toIntArray() }.toTypedArray()
}