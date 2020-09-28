package simulation.robotcleaner14503

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class RobotCleanerTest {
    @Test
    fun example1() {
        val map = ("1 1 1\n" +
            "1 0 1\n" +
            "1 1 1").split('\n')
            .map {
                it.split(' ')
                    .map { it.toInt() }
                    .toIntArray()
            }
            .toTypedArray()

        val direction = Direction.Up

        val robotCleaner = RobotCleaner(map, Offset(1, 1), direction)
        val cleanCount = robotCleaner.startClean()

        assertEquals(1, cleanCount)
    }

    @Test
    fun example2() {
        val input = "11 10\n" +
            "7 4 0\n" +
            "1 1 1 1 1 1 1 1 1 1\n" +
            "1 0 0 0 0 0 0 0 0 1\n" +
            "1 0 0 0 1 1 1 1 0 1\n" +
            "1 0 0 1 1 0 0 0 0 1\n" +
            "1 0 1 1 0 0 0 0 0 1\n" +
            "1 0 0 0 0 0 0 0 0 1\n" +
            "1 0 0 0 0 0 0 1 0 1\n" +
            "1 0 0 0 0 0 1 1 0 1\n" +
            "1 0 0 0 0 0 1 1 0 1\n" +
            "1 0 0 0 0 0 0 0 0 1\n" +
            "1 1 1 1 1 1 1 1 1 1"

        assertCleanCount(57, input)
    }

    private fun assertCleanCount(cleanCount: Int, input: String) {
        val inputQueue = LinkedList<String>().apply {
            input.split('\n').forEach {
                add(it)
            }
        }

        val mapSize = with(inputQueue.poll().split(' ').map { it.toInt() }) {
            Offset(last(), first())
        }

        val map = Array(mapSize.y) {
            IntArray(mapSize.x) {
                RobotCleaner.SPACE
            }
        }

        with(inputQueue.poll().split(' ').map { it.toInt() }) {
            val position = Offset(get(1), get(0))
            val direction = when(get(2)) {
                0 -> Direction.Up
                1 -> Direction.Right
                2 -> Direction.Down
                else -> Direction.Left
            }

            repeat(mapSize.y) { y ->
                inputQueue.poll().split(' ').forEachIndexed { x, s ->
                    map.setValueAt(Offset(x, y), s.toInt())
                }
            }

            val robotCleaner = RobotCleaner(map, position, direction)

            assertEquals(cleanCount, robotCleaner.startClean())
        }
    }
}