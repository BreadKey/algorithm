package samsung.q2

import org.junit.Assert.*
import org.junit.Test

class Game2048Test {
    @Test
    fun example() {
        val example = "2 2 2\n" +
            "4 4 4\n" +
            "8 8 8"

        val game = Game2048(example.split('\n'))

        assertEquals(2, game.initialBoard.getValueAt(Offset(0, 2)))

        val boardAfterScrollUp = game.scroll(game.initialBoard, Direction.Up)
        assertArrayEquals(arrayOf(
            arrayOf(2, 2, 2),
            arrayOf(4, 4, 4),
            arrayOf(8, 8, 8)
        ).reversedArray(), boardAfterScrollUp)

        val boardAfterScrollDown = game.scroll(game.initialBoard, Direction.Down)
        assertArrayEquals(arrayOf(
            arrayOf(2, 2, 2),
            arrayOf(4, 4, 4),
            arrayOf(8, 8, 8)
        ).reversedArray(), boardAfterScrollDown)

        val boardAfterScrollRight = game.scroll(game.initialBoard, Direction.Right)
        assertArrayEquals(arrayOf(
            arrayOf(0, 2, 4),
            arrayOf(0, 4, 8),
            arrayOf(0, 8, 16)
        ).reversedArray(), boardAfterScrollRight)

        assertEquals(16, game.solve())
    }

    @Test
    fun testOnly1Block() {
        val block = listOf("4")

        val game = Game2048(block)

        assertEquals(4, game.solve())
    }
}