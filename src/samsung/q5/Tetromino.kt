package samsung.q5

fun main() {
    val firstLine = readLine()!!.split(' ').map { it.toInt() }
    val height = firstLine.first()
    val width = firstLine.last()
    val map = Array(height) {
        IntArray(width)
    }

    repeat(height) { y ->
        map[y] = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    }

    val calculator = MaxCalculatorByTetromino(map)

    println(calculator.calculateMax())
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

enum class Direction {
    Up, Down, Left, Right
}

abstract class Tetromino(val upwardOffsets: List<Point>) {
    init {
        assert(upwardOffsets.size == 4)
    }

    fun rotateOffset(upwardOffset: Point, direction: Direction): Point = when (direction) {
        Direction.Right -> Point(upwardOffset.y, -upwardOffset.x)
        Direction.Down -> Point(-upwardOffset.x, -upwardOffset.y)
        Direction.Left -> Point(-upwardOffset.y, upwardOffset.x)
        else -> upwardOffset
    }

    fun build(startPoint: Point, direction: Direction, bounds: Point): List<Point> {
        assert(upwardOffsets.size == 4)

        val result = mutableListOf<Point>()

        repeat(4) { index ->
            val point = startPoint + rotateOffset(upwardOffsets[index], direction)

            if (isOutOfBounds(point, bounds)) {
                return emptyList()
            }

            result.add(point)
        }

        return result
    }

    fun isOutOfBounds(point: Point, bounds: Point): Boolean = point.x !in 0 until bounds.x || point.y !in 0 until bounds.y
}

/**
 * [1]
 * [2]
 * [Z][0]
 */
class LMino : Tetromino(listOf(Point(1, 0), Point(0, 2), Point(0, 1), Point(0, 0)))

/**
 *    [1]
 *    [2]
 * [0][Z]
 */
class JMino : Tetromino(listOf(Point(-1, 0), Point(0, 2), Point(0, 1), Point(0, 0)))

/**
 * [0]
 * [2]
 * [Z]
 * [1]
 */
class IMino : Tetromino(listOf(Point(0, 2), Point(0, -1), Point(0, 1), Point(0, 0)))

/**
 * [1][2]
 * [Z][3]
 */
class OMino : Tetromino(listOf(Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0)))

/**
 *    [2][1]
 * [0][Z]
 */
class SMino : Tetromino(listOf(Point(-1, 0), Point(1, 1), Point(0, 1), Point(0, 0)))

/**
 * [1][2]
 *    [Z][0]
 */
class ZMino : Tetromino(listOf(Point(1, 0), Point(-1, 1), Point(0, 1), Point(0, 0)))

/**
 * [0][Z][1]
 *    [2]
 */
class TMino : Tetromino(listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 0)))

fun Array<IntArray>.getValueAt(point: Point) = this[point.y][point.x]

class MaxCalculatorByTetromino(val map: Array<IntArray>) {
    private val bounds = Point(map.first().size, map.size)
    private val tetrominos = listOf<Tetromino>(
        LMino(), JMino(), IMino(), OMino(), SMino(), ZMino(), TMino()
    )

    fun calculateMax(): Int {
        var maxOfSum = 0

        for (y in 0 until bounds.y) {
            for (x in 0 until bounds.x) {
                val startPoint = Point(x, y)
                for (tetromino in tetrominos) {
                    for (direction in Direction.values()) {
                        val tetrominoPoints = tetromino.build(startPoint, direction, bounds)

                        var sum = 0

                        for (point in tetrominoPoints) {
                            sum += map.getValueAt(point)
                        }

                        maxOfSum = maxOf(maxOfSum, sum)
                    }
                }
            }
        }

        return maxOfSum
    }
}