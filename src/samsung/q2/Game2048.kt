package samsung.q2

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


fun <T> Array<Array<T>>.getValueAt(point: Offset) = this[point.y][point.x]
fun <T> Array<Array<T>>.setValueAt(point: Offset, value: T) {
    this[point.y][point.x] = value
}

class Game2048(rawBoard: Iterable<String>) {
    companion object {
        const val BLANK = 0
    }

    val initialBoard: Array<Array<Int>>
    val boardSize: Int

    init {
        initialBoard = rawBoard.reversed().map {
            it.split(' ').map { s -> s.toInt() }.toTypedArray()
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

    private fun findMaxValue(maxStep: Int, currentStep: Int, board: Array<Array<Int>>, direction: Direction) {
        val nextBoard = scroll(board, direction)

        if (currentStep < maxStep) {
            Direction.values().forEach {
                findMaxValue(maxStep, currentStep + 1, nextBoard, it)
            }
        } else {
            val currentMaxValue = nextBoard.map { it.max()!! }.max()!!

            if (maxValue == null || maxValue!! < currentMaxValue) {
                maxValue = currentMaxValue
            }
        }
    }

    fun scroll(board: Array<Array<Int>>, direction: Direction): Array<Array<Int>> {
        val nextBoard = Array<Array<Int>>(boardSize) {
            Array<Int>(boardSize) {
                BLANK
            }
        }

        val range = if (direction == Direction.Up || direction == Direction.Right) {
            boardSize - 1 downTo 0
        } else {
            0 until boardSize
        }

        for (crossAxisValue in range) {
            for (mainAxisValue in range) {
                var currentPoint = Offset.from(direction, mainAxisValue, crossAxisValue)
                var currentValue = board.getValueAt(currentPoint)
                if (currentValue == BLANK) continue

                while (canGo(currentValue, currentPoint + direction.vector, nextBoard)) {
                    nextBoard.setValueAt(currentPoint, BLANK)

                    currentPoint += direction.vector

                    if (currentValue == nextBoard.getValueAt(currentPoint)) {
                        currentValue *= 2
                    }
                }

                nextBoard.setValueAt(currentPoint, currentValue)
            }
        }

        return nextBoard
    }

    fun canGo(currentValue: Int, nextPoint: Offset, nextBoard: Array<Array<Int>>): Boolean = when {
        nextPoint.x !in 0 until boardSize || nextPoint.y !in 0 until boardSize -> false
        else -> when (nextBoard.getValueAt(nextPoint)) {
            BLANK, currentValue -> true
            else -> false
        }
    }
}