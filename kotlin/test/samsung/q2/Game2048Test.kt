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
        ).reversedArray(), boardAfterScrollUp.board)

        val boardAfterScrollDown = game.scroll(game.initialBoard, Direction.Down)
        assertArrayEquals(arrayOf(
            arrayOf(2, 2, 2),
            arrayOf(4, 4, 4),
            arrayOf(8, 8, 8)
        ).reversedArray(), boardAfterScrollDown.board)

        val boardAfterScrollRight = game.scroll(game.initialBoard, Direction.Right)
        assertArrayEquals(arrayOf(
            arrayOf(0, 2, 4),
            arrayOf(0, 4, 8),
            arrayOf(0, 8, 16)
        ).reversedArray(), boardAfterScrollRight.board)

        assertEquals(16, game.solve())
    }

    @Test
    fun testOnly1Block() {
        val block = listOf("4")

        val game = Game2048(block)

        assertEquals(4, game.solve())
    }

    @Test
    fun mergeOnlyOnce() {
        val board = listOf(
            "2 0 0 0",
            "2 0 0 0",
            "2 0 0 0",
            "2 0 0 0"
        )

        val game = Game2048(board)

        val scrollUpResult = game.scroll(game.initialBoard, Direction.Up)

        assertArrayEquals(
            arrayOf(
                arrayOf(4, 0, 0, 0),
                arrayOf(4, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            ).reversedArray(),
            scrollUpResult.board
        )
    }

    @Test
    fun mergeTwice() {
        val board = listOf(
            "2 0 0 0",
            "2 0 0 0",
            "2 0 0 0",
            "2 0 0 0"
        )

        val game = Game2048(board)

        val scrollUpResult = game.scroll(game.initialBoard, Direction.Up)

        assertArrayEquals(
            arrayOf(
                arrayOf(4, 0, 0, 0),
                arrayOf(4, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            ).reversedArray(),
            scrollUpResult.board
        )

        val scrollUpTwiceResult = game.scroll(scrollUpResult.board, Direction.Up)

        assertArrayEquals(
            arrayOf(
                arrayOf(8, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            ).reversedArray(),
            scrollUpTwiceResult.board
        )
    }

    @Test
    fun example3() {
        val game = Game2048(
            listOf(
                "2 4 16 8",
                "8 4 0 0",
                "16 8 2 0",
                "2 8 2 0"
            )
        )

        val scrollUpResult = game.scroll(game.initialBoard, Direction.Up)

        assertArrayEquals(
            arrayOf(
                arrayOf(2, 8, 16, 8),
                arrayOf(8, 16, 4, 0),
                arrayOf(16, 0, 0, 0),
                arrayOf(2, 0, 0, 0)
            ).reversedArray(),
            scrollUpResult.board
        )
    }

    @Test
    fun example5() {
        val game = Game2048(listOf(
            "2 0 2 8",
            "0 0 2 2",
            "0 0 0 0",
            "0 0 0 0"
        ))

        val scrollLefResult = game.scroll(game.initialBoard, Direction.Left)

        assertArrayEquals(
            arrayOf(
                arrayOf(4, 8, 0, 0),
                arrayOf(4, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            ).reversedArray(),
            scrollLefResult.board
        )
    }

    @Test
    fun example6() {
        val game = Game2048(
            ("16 16 8 32 32 0 0 8 8 8\n" +
                    "16 0 0 0 0 8 0 0 0 16\n" +
                    "0 0 0 0 0 0 0 0 0 2\n" +
                    "0 0 0 0 0 0 0 0 0 0\n" +
                    "0 0 0 0 0 0 0 0 0 0\n" +
                    "0 0 0 0 0 0 0 0 0 0\n" +
                    "0 0 0 0 0 0 0 0 0 0\n" +
                    "0 0 0 0 0 0 0 0 0 0\n" +
                    "0 0 0 0 0 0 0 0 0 0\n" +
                    "0 0 0 0 0 0 0 0 0 0").split('\n')
        )

        assertEquals(64, game.solve())
    }

    @Test
    fun example7() {
        val game = Game2048(("2 4 8 16\n" +
                "4 8 16 32\n" +
                "8 16 32 64\n" +
                "16 32 64 128").split("\n"))

        assertEquals(128, game.solve())
    }

    @Test
    fun example8() {
        val game = Game2048(("0 0 64 32 32 0 0 0 0 0\n" +
                "0 32 32 64 0 0 0 0 0 0\n" +
                "0 0 128 0 0 0 0 0 0 0\n" +
                "64 64 128 0 0 0 0 0 0 0\n" +
                "0 0 64 32 32 0 0 0 0 0\n" +
                "0 32 32 64 0 0 0 0 0 0\n" +
                "0 0 128 0 0 0 0 0 0 0\n" +
                "64 64 128 0 0 0 0 0 0 0\n" +
                "128 32 2 4 0 0 0 0 0 0\n" +
                "0 0 128 0 0 0 0 0 0 0").split('\n'))

        assertEquals(1024, game.solve())
    }

    @Test
    fun mergeRight() {
        val board = listOf(
            "2 2 4",
            "0 0 0",
            "0 0 0"
        )
        val game = Game2048(board)
        val scrollRight = game.scroll(game.initialBoard, Direction.Right)

        assertEquals(4, scrollRight.board.getValueAt(Offset(2, 2)))
        assertEquals(4, scrollRight.board.getValueAt(Offset(1, 2)))
    }
}