package programmers.level3.immigration

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {
    @Test
    fun testCase1() {
        val solution = Solution()
        assertEquals(28, solution.solution(6, intArrayOf(7, 10)))
    }
}