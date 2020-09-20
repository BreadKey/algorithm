package q1182

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {
    private val solution = Solution()

    @Test
    fun example1() {
        assertEquals(1, solution.solution(5, 0, intArrayOf(-7, -3, -2, 5, 8)))
    }

    @Test
    fun only1() {
        assertEquals(1, solution.solution(5, 5, intArrayOf(-10, -11, -13, -14, 5)))
    }
}