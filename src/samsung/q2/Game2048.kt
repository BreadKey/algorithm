package samsung.q2

fun main() {
    val boardSize = readLine()!!.toInt()

    val input = mutableListOf<String>()

    repeat(boardSize) {
        input.add(readLine()!!)
    }

    println(Game2048(input).solve())
}

enum class Direction(val vector: Offset) {
    Left(Offset(-1, 0)), Right(Offset(1, 0)), Up(Offset(0, 1)), Down(Offset(0, -1))
}

data class Offset(val x: Int, val y: Int) {
    fun cross() = Offset(y, x)

    operator fun plus(other: Offset) = Offset(x + other.x, y + other.y)
    operator fun minus(other: Offset) = Offset(x - other.x, y - other.y)
    operator fun times(other: Offset) = Offset(x * other.x, y * other.y)

    companion object {
        fun from(direction: Direction, mainAxisValue: Int, crossAxisValue: Int) = when (direction) {
            Direction.Right, Direction.Left -> Offset(mainAxisValue, crossAxisValue)
            else -> Offset(crossAxisValue, mainAxisValue)
        }
    }
}


fun Array<IntArray>.getValueAt(point: Offset) = this[point.y][point.x]
fun Array<IntArray>.setValueAt(point: Offset, value: Int) {
    this[point.y][point.x] = value
}

class Game2048(rawBoard: Iterable<String>) {
    companion object {
        const val BLANK = 0
    }

    val initialBoard: Array<IntArray>
    val boardSize: Int

    init {
        initialBoard = rawBoard.reversed().map {
            it.split(' ').map { s -> s.toInt() }.toIntArray()
        }.toTypedArray()

        assert(initialBoard.size == initialBoard.first().size)

        boardSize = initialBoard.size
        assert(boardSize in 1..20)
    }

    private var maxValue: Int? = null

    fun solve(): Int {
        Direction.values().forEach {
            findMaxValue(5, 0, initialBoard, it)
        }

        return maxValue ?: -1
    }

    private fun findMaxValue(maxStep: Int, step: Int, board: Array<IntArray>, direction: Direction) {
        val nextBoard = scroll(board, direction)

        val currentStep = step + 1

        if (currentStep < maxStep) {
            Direction.values().forEach {
                findMaxValue(maxStep, currentStep, nextBoard, it)
            }
        } else {
            val currentMaxValue = nextBoard.map { it.max()!! }.max()!!

            if (maxValue == null || maxValue!! < currentMaxValue) {

                maxValue = currentMaxValue
            }
        }
    }

    fun scroll(board: Array<IntArray>, direction: Direction): Array<IntArray> {
        val nextBoard = Array<IntArray>(boardSize) {
            IntArray(boardSize) {
                BLANK
            }
        }

        val range = if (direction == Direction.Up || direction == Direction.Right) {
            boardSize - 1 downTo 0
        } else {
            0 until boardSize
        }

        for (crossAxisValue in range) {
            var beforeMerged = false
            for (mainAxisValue in range) {
                var currentPoint = Offset.from(direction, mainAxisValue, crossAxisValue)
                var currentValue = board.getValueAt(currentPoint)
                if (currentValue == BLANK) continue

                var isMerged = false
                while (canGo(currentValue, currentPoint + direction.vector, nextBoard, beforeMerged, isMerged)) {
                    nextBoard.setValueAt(currentPoint, BLANK)

                    currentPoint += direction.vector

                    if (currentValue == nextBoard.getValueAt(currentPoint)) {
                        currentValue *= 2
                        isMerged = true
                    }
                }

                beforeMerged = isMerged

                nextBoard.setValueAt(currentPoint, currentValue)
            }
        }

        return nextBoard
    }

    fun canGo(currentValue: Int, nextPoint: Offset, nextBoard: Array<IntArray>, beforeMerged: Boolean, isMerged: Boolean): Boolean = when {
        nextPoint.x !in 0 until boardSize || nextPoint.y !in 0 until boardSize -> false
        else -> when (nextBoard.getValueAt(nextPoint)) {
            BLANK -> true
            currentValue -> !beforeMerged && !isMerged
            else -> false
        }
    }
}