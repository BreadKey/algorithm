package samsung.q8

class Slope {
    companion object {
        const val SAME = 0
        const val HIGH = 1
        const val LOW = -1
    }

    fun canPass(road: IntArray, slopeLength: Int): Boolean {
        var step = 1

        var isGoingDown = false

        road.forEachIndexed { index, height ->
            if (index > 0) {
                val previousHeight = road[index - 1]

                when (height - previousHeight) {
                    SAME -> {
                        step++
                    }
                    HIGH -> {
                        if (cannotGoHigh(step, slopeLength, isGoingDown)) return false

                        isGoingDown = false
                        step = 1
                    }
                    LOW -> {
                        if (cannotGoDown(step, slopeLength, isGoingDown)) return false

                        isGoingDown = true
                        step = 1
                    }
                    else -> return false
                }
            }
        }

        if (isGoingDown && step < slopeLength) return false
        return true
    }

    private fun cannotGoHigh(step: Int, slopeLength: Int, isGoingDown: Boolean) = step < slopeLength ||
        (isGoingDown && step < slopeLength * 2);

    private fun cannotGoDown(step: Int, slopeLength: Int, isGoingDown: Boolean) = isGoingDown && step < slopeLength

    fun solve(map: Array<IntArray>, slopeLength: Int): Int {
        var result = 0

        for (row in map) {
            if (canPass(row, slopeLength)) {
                result++
            }
        }

        repeat(map.size) { columnIndex ->
            val column = IntArray(map.size) { rowIndex ->
                map[rowIndex][columnIndex]
            }

            if (canPass(column, slopeLength)) {
                result++
            }
        }

        return result
    }

    fun solve(question: Question) = solve(question.map, question.slopeLength)

    fun parseInput(input: String): Question {
        val splits = input.split('\n')

        val slopeLength = splits.first().split(' ').last().toInt()

        val map = splits.drop(1).map { row -> row.split(' ').map { s -> s.toInt() }.toIntArray() }.toTypedArray()

        return Question(map, slopeLength)
    }
}

class Question(val map: Array<IntArray>, val slopeLength: Int)

fun main() {
    val slope = Slope()

    var input: String = readLine()!! + '\n'

    val mapSize = input.split(' ').first().toInt()

    repeat(mapSize) { index ->
        input += readLine()!!
        if (index < mapSize - 1) {
            input += '\n'
        }
    }

    println(slope.solve(slope.parseInput(input)))
}