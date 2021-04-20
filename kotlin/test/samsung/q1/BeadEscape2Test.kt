package samsung.q1

import org.junit.Assert.*
import org.junit.Test

class BeadEscape2Test {
    private val exampleRawBoards = listOf(
        "#####",
        "#..B#",
        "#.#.#",
        "#RO.#",
        "#####"
    )

    private val beadEscape = BeadEscape2(exampleRawBoards)

    @Test
    fun getTileAt() {
        assertEquals(Tile.RedBead, beadEscape.getTileAt(Offset(1, 1)))
        assertEquals(Tile.Hole, beadEscape.getTileAt(Offset(2, 1)))
        assertEquals(Tile.BlueBead, beadEscape.getTileAt(Offset(3, 3)))
    }

    @Test
    fun rollUp() {
        assertEquals(
            RollResult(Offset(1, 3), Offset(3, 3), canRole = true, holeIn = false),
            beadEscape.roll(Direction.Up)
        )
    }

    @Test
    fun rollRight() {
        assertEquals(true, beadEscape.roll(Direction.Right).holeIn)
    }

    @Test
    fun example() {
        assertEquals(Offset(1, 1), beadEscape.startRedBeadPosition)
        assertEquals(Offset(3, 3), beadEscape.startBlueBeadPosition)

        assertEquals(
            1, beadEscape.solve()
        )
    }

    @Test
    fun example2() {
        val example = "#######\n" +
                "#...RB#\n" +
                "#.#####\n" +
                "#.....#\n" +
                "#####.#\n" +
                "#O....#\n" +
                "#######"

        val beadEscape = BeadEscape2(example.split("\n"))

        val firstRollResult = beadEscape.roll(Direction.Left)
        assertEquals(RollResult(Offset(1, 5), Offset(2, 5), canRole = true, holeIn = false), firstRollResult)

        val secondRollResult =
            beadEscape.roll(Direction.Down, firstRollResult.redBeadPosition, firstRollResult.blueBeadPosition)
        assertEquals(RollResult(Offset(1, 3), Offset(2, 5), canRole = true, holeIn = false), secondRollResult)

        val thirdRollResult =
            beadEscape.roll(Direction.Right, secondRollResult.redBeadPosition, secondRollResult.blueBeadPosition)
        assertEquals(RollResult(Offset(5, 3), Offset(5, 5), canRole = true, holeIn = false), thirdRollResult)

        val fourthRollResult =
            beadEscape.roll(Direction.Down, thirdRollResult.redBeadPosition, thirdRollResult.blueBeadPosition)
        assertEquals(RollResult(Offset(5, 1), Offset(5, 5), canRole = true, holeIn = false), fourthRollResult)

        val fifthResult =
            beadEscape.roll(Direction.Left, fourthRollResult.redBeadPosition, fourthRollResult.blueBeadPosition)
        assertEquals(RollResult(Offset(1, 1), Offset(5, 5), canRole = true, holeIn = true), fifthResult)

        assertEquals(5, beadEscape.solve())
    }

    @Test
    fun example3() {
        assertSolution(
            5, "#######\n" +
                    "#..R#B#\n" +
                    "#.#####\n" +
                    "#.....#\n" +
                    "#####.#\n" +
                    "#O....#\n" +
                    "#######"
        )
    }

    @Test
    fun example4() {
        assertSolution(
            -1, "##########\n" +
                    "#R#...##B#\n" +
                    "#...#.##.#\n" +
                    "#####.##.#\n" +
                    "#......#.#\n" +
                    "#.######.#\n" +
                    "#.#....#.#\n" +
                    "#.#.#.#..#\n" +
                    "#...#.O#.#\n" +
                    "##########"
        )
    }

    @Test
    fun example5() {
        assertSolution(
            1, "#######\n" +
                    "#R.O.B#\n" +
                    "#######"
        )
    }

    @Test
    fun example6() {
        assertSolution(
            7, "##########\n" +
                    "#R#...##B#\n" +
                    "#...#.##.#\n" +
                    "#####.##.#\n" +
                    "#......#.#\n" +
                    "#.######.#\n" +
                    "#.#....#.#\n" +
                    "#.#.##...#\n" +
                    "#O..#....#\n" +
                    "##########"
        )
    }

    @Test
    fun example7() {
        assertSolution(
            -1, "##########\n" +
                    "#.O....RB#\n" +
                    "##########"
        )
    }

    @Test
    fun example8() {
        assertSolution(
            4, "######\n" +
                    "#O##.#\n" +
                    "#.#.##\n" +
                    "#..#.#\n" +
                    "#B...#\n" +
                    "###R##\n" +
                    "#..#.#\n" +
                    "###..#\n" +
                    "#....#\n" +
                    "######"
        )
    }

    @Test
    fun example9() {
        val beadEscape = BeadEscape2(
            ("##########\n" +
                    "#..###..##\n" +
                    "#....#####\n" +
                    "###O..####\n" +
                    "##B..##..#\n" +
                    "##.#R#..##\n" +
                    "##########").split("\n")
        )

        val firstRoll = beadEscape.roll(Direction.Right)
        assertEquals(RollResult(Offset(4, 1), Offset(4, 2), canRole = true, holeIn = false), firstRoll)

        val secondsRoll = beadEscape.roll(Direction.Up, firstRoll.redBeadPosition, firstRoll.blueBeadPosition)
        assertEquals(RollResult(Offset(4, 3), Offset(4, 4), canRole = true, holeIn = false), secondsRoll)

        assertSolution(
            3, "##########\n" +
                    "#..###..##\n" +
                    "#....#####\n" +
                    "###O..####\n" +
                    "##B..##..#\n" +
                    "##.#R#..##\n" +
                    "##########"
        )
    }

    @Test
    fun example10() {
        assertSolution(
            4, "######\n" +
                    "#.####\n" +
                    "##R#.#\n" +
                    "##..##\n" +
                    "#B...#\n" +
                    "#O####\n" +
                    "######"
        )
    }

    @Test
    fun example11() {
        assertSolution(
            2, "######\n" +
                    "##.#.#\n" +
                    "#.#.##\n" +
                    "#..#.#\n" +
                    "#B..##\n" +
                    "#R#.##\n" +
                    "#.##.#\n" +
                    "#O.###\n" +
                    "######"
        )
    }

    @Test
    fun example12() {
        val beadEscape = BeadEscape2(
            ("#########\n" +
                    "#..#...R#\n" +
                    "#..##.#.#\n" +
                    "#.###OB.#\n" +
                    "####.#..#\n" +
                    "#########").split("\n")
        )

        val firstRoll = beadEscape.roll(Direction.Right)
        assertEquals(RollResult(Offset(7, 4), Offset(7, 2), canRole = true, holeIn = false), firstRoll)

        val secondRoll = beadEscape.roll(Direction.Down, firstRoll.redBeadPosition, firstRoll.blueBeadPosition)
        assertEquals(RollResult(Offset(7, 2), Offset(7, 1), canRole = true, holeIn = false), secondRoll)

        assertSolution(
            3, "#########\n" +
                    "#..#...R#\n" +
                    "#..##.#.#\n" +
                    "#.###OB.#\n" +
                    "####.#..#\n" +
                    "#########"
        )
    }

    @Test
    fun example13() {
        assertSolution(
            3, "######\n" +
                    "#..#.#\n" +
                    "######\n" +
                    "#.##.#\n" +
                    "#.OB.#\n" +
                    "##.#R#\n" +
                    "###..#\n" +
                    "######"
        )
    }

    @Test
    fun example14() {
        assertSolution(
            5, "#####\n" +
                    "#O.##\n" +
                    "#B.##\n" +
                    "#..##\n" +
                    "#..##\n" +
                    "##.R#\n" +
                    "#...#\n" +
                    "#####"
        )
    }

    @Test
    fun example15() {
        assertSolution(
            5, "#####\n" +
                    "#O.##\n" +
                    "#B.##\n" +
                    "#..##\n" +
                    "#..##\n" +
                    "##.R#\n" +
                    "#...#\n" +
                    "#####"
        )
    }

    @Test
    fun example16() {
        assertSolution(
            7, "#######\n" +
                    "#.###R#\n" +
                    "##....#\n" +
                    "#..#.##\n" +
                    "##.#OB#\n" +
                    "##....#\n" +
                    "#######"
        )
    }

    @Test
    fun example17() {
        assertSolution(
            3, "#####\n" +
                    "###R#\n" +
                    "##..#\n" +
                    "#OB.#\n" +
                    "#.#.#\n" +
                    "#####"
        )
    }

    @Test
    fun example18() {
        assertSolution(
            -1, "##########\n" +
                    "#R#...#..#\n" +
                    "#...#....#\n" +
                    "#######.##\n" +
                    "#....#..##\n" +
                    "#.####O#.#\n" +
                    "#.#......#\n" +
                    "#........#\n" +
                    "#....#...#\n" +
                    "##########"
        )
    }

    private fun assertSolution(result: Int, input: String) {
        val beadEscape = BeadEscape2(input.split("\n"))

        assertEquals(result, beadEscape.solve())
    }
}