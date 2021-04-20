package programmers.hash.camouflage

class Solution {
    fun solution(clothes: Array<Array<String>>): Int {
        val clothesMap = mutableMapOf<String, Int>()

        clothes.forEach {
            val partName = it.last()

            val count = clothesMap[partName] ?: 0

            clothesMap[partName] = count + 1
        }

        var result = 1

        clothesMap.values.forEach {
            result *= it + 1
        }

        return result - 1
    }
}