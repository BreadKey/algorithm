package programmers.level1.cranedolldrawgame

import org.junit.Assert.*
import org.junit.Test

class CraneDollDrawGameTest {
    @Test
    fun catch() {
        val game = CraneDollDrawGame()
        game.startGame(
                arrayOf(
                        intArrayOf(0, 0, 0, 3, 0),
                        intArrayOf(1, 2, 0, 1, 2)
                )
        )

        game.move(1)
        game.draw()
        assertEquals(1, game.getBucket().last())

        game.move(2)
        game.draw()
        assertEquals(2, game.getBucket().last())

        game.move(3)
        game.draw()
        assertEquals(2, game.getBucket().last())

        game.move(4)
        game.draw()
        assertEquals(3, game.getBucket().last())

        game.move(4)
        game.draw()
        assertEquals(1, game.getBucket().last())
    }

    @Test
    fun testCase1() {
        val solution = Solution()

        assertEquals(
                4, solution.solution(
                arrayOf(
                        intArrayOf(0, 0, 0, 0, 0),
                        intArrayOf(0, 0, 1, 0, 3),
                        intArrayOf(0, 2, 5, 0, 1),
                        intArrayOf(4, 2, 4, 4, 2),
                        intArrayOf(3, 5, 1, 3, 1)
                ),
                intArrayOf(1, 5, 3, 5, 1, 2, 1, 4)
        )
        )
    }
}