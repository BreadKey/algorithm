package samsung.q3

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DummyGameTest {
    @Test
    fun moveTest() {
        val game = DummyGame(2)

        val snake = game.spawnSnake()

        val result = game.move(snake, headDirection = Direction.Right)

        assertEquals(false, result.isGameOver)
        assertEquals(Offset(1, 0), result.snake.head())
        assertEquals(Offset(1, 0), result.snake.tail())
    }

    @Test
    fun eatAppleTest() {
        val game = DummyGame(2, apples = mutableListOf(Offset(1, 0)))

        val snake = game.spawnSnake()

        val result = game.move(snake, headDirection = Direction.Right)

        assertEquals(false, result.isGameOver)
        assertEquals(Offset(1, 0), result.snake.head())
        assertEquals(Offset(0, 0), result.snake.tail())
    }

    @Test
    fun rotateTest() {
        val game = DummyGame(2)

        assertEquals(Direction.Right, game.headDirection)
        game.rotateHead(Direction.Right)
        assertEquals(Direction.Down, game.headDirection)
    }

    @Test
    fun gameOverTest() {
        val game = getGameFromInput("6\n" +
            "3\n" +
            "3 4\n" +
            "2 5\n" +
            "5 3\n" +
            "3\n" +
            "3 D\n" +
            "15 L\n" +
            "17 D")
        game.startGame()
        assertEquals(9, game.playTimeSeconds)
    }

    @Test
    fun gameOverTest2() {
        val input = "10\n" +
            "5\n" +
            "1 5\n" +
            "1 3\n" +
            "1 2\n" +
            "1 6\n" +
            "1 7\n" +
            "4\n" +
            "8 D\n" +
            "10 D\n" +
            "11 D\n" +
            "13 L"

        val game = getGameFromInput(input)

        game.startGame()

        assertEquals(13, game.playTimeSeconds)
    }

    fun getGameFromInput(input: String): DummyGame {
        val inputQueue = LinkedList<String>()

        input.split('\n').forEach {
            inputQueue.add(it)
        }

        val mapSize = inputQueue.poll().toInt()

        val apples = mutableListOf<Offset>()
        val appleCount = inputQueue.poll().toInt()
        repeat(appleCount) {
            with(inputQueue.poll().split(' ')) {
                apples.add(Offset(last().toInt() - 1, first().toInt() - 1))
            }
        }

        val commandCount = inputQueue.poll().toInt()
        val commands = LinkedList<Command>()
        repeat(commandCount) {
            with(inputQueue.poll().split(' ')) {
                commands.add(
                    Command(first().toInt(), if (last() == "L") Direction.Left else Direction.Right)
                )
            }
        }

        return DummyGame(mapSize, apples, commands)
    }
}