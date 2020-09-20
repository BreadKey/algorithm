package q1182

/**
 * N개의 정수로 이루어진 수열이 있을 때, 크기가 양수인 부분수열 중에서 그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램을 작성하시오.
 */
class Solution {
    private var count = 0
    fun solution(number: Int, sum: Int, integers: IntArray): Int {
        count = 0

        checkSum(sum, 0, 0, integers)

        return count
    }

    private fun checkSum(sum: Int, currentSum: Int, index: Int, integers: IntArray) {
        if (index == integers.size) return

        if (currentSum + integers[index] == sum) count++

        checkSum(sum, currentSum, index + 1, integers)
        checkSum(sum, currentSum + integers[index], index + 1, integers)
    }
}

fun main() {
    val solution = Solution()
    val numberAndSum = readLine()!!.split(" ").map { it.toInt() }
    val integers = readLine()!!.split(" ").map { it.toInt() }.toIntArray()

    println(solution.solution(numberAndSum.first(), numberAndSum.last(), integers))
}