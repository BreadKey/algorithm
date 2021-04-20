package programmers.level1.twooutandplus

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {
    private val solution = Solution()

    @Test
    fun testCase1() {
        assertArrayEquals(intArrayOf(2, 3, 4, 5, 6, 7), solution.solution(intArrayOf(2, 1, 3, 4, 1)))
    }

    @Test
    fun testCase2() {
        assertArrayEquals(intArrayOf(2, 5, 7, 9, 12), solution.solution(intArrayOf(5, 0, 2, 7)))
    }
}