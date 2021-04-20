package simulation.robotcleaner14503

fun main() {
    val mapSize = with(readLine()!!.split(' ').map { it.toInt() }) {
        Offset(last(), first())
    }

    val map = Array(mapSize.y) {
        IntArray(mapSize.x) {
            RobotCleaner.SPACE
        }
    }

    with(readLine()!!.split(' ').map { it.toInt() }) {
        val position = Offset(get(1), get(0))
        val direction = when(get(2)) {
            0 -> Direction.Up
            1 -> Direction.Right
            2 -> Direction.Down
            else -> Direction.Left
        }

        repeat(mapSize.y) { y ->
            readLine()!!.split(' ').forEachIndexed { x, s ->
                map.setValueAt(Offset(x, y), s.toInt())
            }
        }

        val robotCleaner = RobotCleaner(map, position, direction)

        println(robotCleaner.startClean())
    }
}

data class Offset(val x: Int, val y: Int) {
    operator fun plus(other: Offset) = Offset(x + other.x, y + other.y)
    operator fun minus(other: Offset) = Offset(x - other.x, y - other.y)
}

enum class Direction(val vector: Offset) {
    Left(Offset(-1, 0)), Down(Offset(0, 1)), Right(Offset(1, 0)), Up(Offset(0, -1))
}

fun Array<IntArray>.getValueAt(point: Offset) = this[point.y][point.x]
fun Array<IntArray>.setValueAt(point: Offset, value: Int) {
    this[point.y][point.x] = value
}

class RobotCleaner(val map: Array<IntArray>, private val position: Offset, private val direction: Direction) {
    companion object {
        const val SPACE = 0
        const val WALL = 1
        const val CLEANED = 2
    }

    private var cleanCount = 0

    init {
        assert(map.size in 3..50 && map.first().size in 3..50)
        assert(canClean(position, map))
    }

    fun startClean(): Int {
        var currentPosition = position
        var currentDirection = direction

        val mapInClean = map.copyOf()
        cleanCount = 0

        while (true) {
            if (canClean(currentPosition, map)) {
                clean(currentPosition, mapInClean)
            }

            val lastDirection = currentDirection
            loop@ while (true) {
                currentDirection = rotateLeft(currentDirection)

                if (canClean(currentPosition + currentDirection.vector, mapInClean)) {
                    currentPosition += currentDirection.vector
                    break
                } else {
                    if (lastDirection == currentDirection) {
                        when (mapInClean.getValueAt(currentPosition - currentDirection.vector)) {
                            WALL -> return cleanCount
                            else -> {
                                currentPosition -= currentDirection.vector
                                break@loop
                            }
                        }
                    }
                }
            }
        }
    }

    fun rotateLeft(direction: Direction) = when (direction) {
        Direction.Left -> Direction.Down
        Direction.Down -> Direction.Right
        Direction.Right -> Direction.Up
        Direction.Up -> Direction.Left
    }

    fun clean(point: Offset, map: Array<IntArray>) {
        cleanCount++
        map.setValueAt(point, CLEANED)
    }

    fun canClean(point: Offset, map: Array<IntArray>) = map.getValueAt(point) == SPACE
}