package programmers.hash.camouflage

import org.junit.Assert.*
import org.junit.Test

class SolutionTest {
    @Test
    fun setTest() {
        val setA = setOf("a", "b")
        val setB = setOf("b", "a")

        assertEquals(true, setA == setB)

        val sets = mutableSetOf<Set<String>>()

        sets.add(setA)
        sets.add(setB)

        assertEquals(1, sets.size)
    }

    @Test
    fun case1() {
        val solution = Solution()

        assertEquals(3, solution.solution(arrayOf(arrayOf("a", "h"), arrayOf("b", "h"), arrayOf("c", "h"))))
    }

    @Test
    fun case2() {
        val solution = Solution()

        assertEquals(5, solution.solution(arrayOf(arrayOf("yellow_hat", "headgear"), arrayOf("blue_sunglasses", "eyewear"), arrayOf("green_turban", "headgear"))))
    }
}