package programmers.level3.immigration

class Solution {
    fun solution(n: Int, times: IntArray): Long {
        times.sort()
        var currentTime = times.last().toLong() * n
        var high = currentTime
        var low = 0L

        while(true) {
            val passengers = times.map { currentTime / it }.sum()

            println(passengers)

            if (passengers >= n)
                high = currentTime
            else
                low = currentTime

            val before = currentTime
            currentTime = (high + low + 1) / 2

            if (before == currentTime)
                break
        }

        return currentTime.toLong()
    }
}