package programmers.level1.twooutandplus

/**
 * 정수 배열 numbers가 주어집니다. numbers에서 서로 다른 인덱스에 있는 두 개의 수를 뽑아 더해서 만들 수 있는 모든 수를 배열에 오름차순으로 담아 return 하도록 solution 함수를 완성해주세요.
 */
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