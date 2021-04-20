package samsung.q4

import org.junit.Assert.*
import org.junit.Test

class DiceGameTest {
    @Test
    fun rollTest() {
        val dice = Dice(
            bottom = 1,
            front = 2,
            top = 3,
            back = 4,
            left = 5,
            right = 6
        )

        dice.roll(Direction.Down)
        assertEquals(dice, Dice(
            bottom = 2,
            front = 3,
            back = 1,
            top = 4,
            left = 5,
            right = 6
        ))
    }

    @Test
    fun startGameTest() {
        val game = DiceGame(
            map = arrayOf(
                intArrayOf(0, 2),
                intArrayOf(3, 4),
                intArrayOf(5, 6),
                intArrayOf(6, 8)
            ),
            dicePosition = Offset(0, 0),
            commands = arrayOf(
                Direction.Down,
                Direction.Down,
                Direction.Down,
                Direction.Right,
                Direction.Up,
                Direction.Up,
                Direction.Up,
                Direction.Left
            )
        )

        game.startGame()
    }
}