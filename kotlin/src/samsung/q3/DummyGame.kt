package samsung.q3

import java.util.*

fun main() {
    val mapSize = readLine()!!.toInt()

    val apples = mutableListOf<Offset>()
    val appleCount = readLine()!!.toInt()
    repeat(appleCount) {
        with(readLine()!!.split(' ')) {
            apples.add(Offset(last().toInt() - 1, first().toInt() - 1))
        }
    }

    val commands = LinkedList<Command>()
    val commandCount = readLine()!!.toInt()
    repeat(commandCount) {
        with(readLine()!!.split(' ')) {
            commands.add(
                Command(first().toInt(), if (last() == "L") Direction.Left else Direction.Right)
            )
        }
    }

    val game = DummyGame(mapSize, apples, commands)
    game.startGame()

    println(game.playTimeSeconds)
}

enum class Direction(val vector: Offset) {
    Left(Offset(-1, 0)), Right(Offset(1, 0)), Up(Offset(0, -1)), Down(Offset(0, 1))
}

data class Offset(val x: Int, val y: Int) {
    operator fun plus(other: Offset) = Offset(x + other.x, y + other.y)
    operator fun minus(other: Offset) = Offset(x - other.x, y - other.y)
    operator fun times(other: Offset) = Offset(x * other.x, y * other.y)
}

data class MoveResult(val snake: List<Offset>, val isGameOver: Boolean)
data class Command(val seconds: Int, val direction: Direction)

fun Iterable<Offset>.head() = last()
fun Iterable<Offset>.tail() = first()

class DummyGame(
    private val mapSize: Int,
    private val apples: MutableList<Offset> = mutableListOf(),
    private val commands: LinkedList<Command> = LinkedList()
) {
    var playTimeSeconds: Int = 0
        private set

    var headDirection: Direction = Direction.Right
        private set

    fun startGame() {
        assert(mapSize in 2..100)
        playTimeSeconds = 0
        headDirection = Direction.Right

        val snake = spawnSnake()

        while (true) {
            val moveResult = move(snake, headDirection)
            playTimeSeconds++

            if (moveResult.isGameOver) break

            if (commands.firstOrNull()?.seconds == playTimeSeconds) {
                rotateHead(commands.poll().direction)
            }
        }
    }

    fun rotateHead(direction: Direction) {
        assert(direction == Direction.Left || direction == Direction.Right)

        headDirection = if (direction == Direction.Left) {
            when (headDirection) {
                Direction.Left -> Direction.Down
                Direction.Down -> Direction.Right
                Direction.Right -> Direction.Up
                Direction.Up -> Direction.Left
            }
        } else {
            when (headDirection) {
                Direction.Left -> Direction.Up
                Direction.Up -> Direction.Right
                Direction.Right -> Direction.Down
                Direction.Down -> Direction.Left
            }
        }
    }

    fun spawnSnake() = LinkedList<Offset>().apply { add(Offset(0, 0)) }

    fun move(snake: Queue<Offset>, headDirection: Direction): MoveResult {
        val nextPoint = snake.head() + headDirection.vector

        if (isWall(nextPoint) || isBody(snake, nextPoint)) {
            return MoveResult(snake.toList(), true)
        }

        if (isExistApple(nextPoint)) {
            apples.remove(nextPoint)
        } else {
            snake.poll()
        }

        snake.add(nextPoint)

        return MoveResult(snake.toList(), false)
    }

    fun isExistApple(point: Offset): Boolean = apples.contains(point)
    fun isWall(point: Offset): Boolean =
        point.x !in 0 until mapSize || point.y !in 0 until mapSize

    fun isBody(snake: Iterable<Offset>, point: Offset): Boolean = snake.contains(point)
}