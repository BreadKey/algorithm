package programmers.level1.twooutandplus

class Solution {
    fun solution(numbers: IntArray): IntArray {
        val result = mutableListOf<Int>()

        repeat(numbers.size - 1) { index ->
            for (next in index + 1 until numbers.size) {
                result.add(numbers[index] + numbers[next])
            }
        }

        return result.distinct().sorted().toIntArray()
    }
}