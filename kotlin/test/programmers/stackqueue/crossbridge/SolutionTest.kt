package programmers.stackqueue.crossbridge

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {
    @Test
    fun case1() {
        val solution = Solution()

        assertEquals(8, solution.solution(2, 10, intArrayOf(7, 4, 5, 6)))
    }

    @Test
    fun case3() {
        val solution = Solution()

        assertEquals(110, solution.solution(100, 100, intArrayOf(10, 10, 10, 10, 10, 10, 10, 10, 10, 10)))
    }
}