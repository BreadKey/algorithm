package programmers.stackqueue.developfeature

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {
    private val solution = Solution()
    @Test
    fun test99and1() {
        val result = solution.solution(intArrayOf(99), intArrayOf(1))

        assertArrayEquals(intArrayOf(1), result)
    }

    @Test
    fun case1() {
        val result = solution.solution(intArrayOf(95, 30, 55), intArrayOf(1, 30, 5))

        assertArrayEquals(intArrayOf(2 ,1), result)
    }

    @Test
    fun case2() {
        val result = solution.solution(intArrayOf(95, 90, 99, 99, 80, 99), intArrayOf(1, 1, 1, 1, 1, 1))

        assertArrayEquals(intArrayOf(1, 3, 2), result)
    }
}