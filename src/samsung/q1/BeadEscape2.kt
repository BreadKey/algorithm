package samsung.q1

fun main() {
    val n = readLine()!!.split(" ").first().toInt()

    val input = mutableListOf<String>()

    repeat(n) {
        input.add(readLine()!!)
    }

    val beadEscape = BeadEscape2(input)

    println(beadEscape.solution())
}

enum class Tile(val char: Char) {
    Space('.'), Wall('#'), Hole('O'), RedBead('R'), BlueBead('B')
}

data class Offset(val x: Int, val y: Int) : Comparable<Offset> {
    operator fun plus(other: Offset) = Offset(x + other.x, y + other.y)
    operator fun minus(other: Offset) = Offset(x - other.x, y - other.y)
    operator fun times(other: Offset) = Offset(x * other.x, y * other.y)

    override fun compareTo(other: Offset): Int = x.compareTo(other.x) + y.compareTo(other.y)
}

enum class Direction(val force: Offset, val isPositive: Boolean) {
    Left(Offset(-1, 0), false),
    Right(Offset(1, 0), true),
    Up(Offset(0, 1), true),
    Down(Offset(0, -1), false)
}

data class TiltLog(
    val readBeadPosition: Offset,
    val blueBeadPosition: Offset,
    val direction: Direction
)

data class RollResult(
    val redBeadPosition: Offset,
    val blueBeadPosition: Offset,
    val canRole: Boolean,
    val holeIn: Boolean
)

class BeadEscape2(rawBoard: Iterable<String>) {
    private val board: Array<Array<Tile>>
    val startRedBeadPosition: Offset
    val startBlueBeadPosition: Offset
    private val stepsBeforeTiltsMap = mutableMapOf<TiltLog, Int>()

    private var minStepUntilHoleIn: Int? = null

    init {
        board = convertToBoard(rawBoard)

        var redBeadX = 0
        var redBeadY = 0
        var blueBeadX = 0
        var blueBeadY = 0

        board.forEachIndexed { y, rows ->
            rows.forEachIndexed { x, tile ->
                if (tile == Tile.RedBead) {
                    redBeadX = x
                    redBeadY = y
                } else if (tile == Tile.BlueBead) {
                    blueBeadX = x
                    blueBeadY = y
                }
            }
        }

        startRedBeadPosition = Offset(redBeadX, redBeadY)
        startBlueBeadPosition = Offset(blueBeadX, blueBeadY)
    }

    fun solution(): Int {
        val n = board.size
        val m = board.first().size
        assert(n in 3..10 && m in 3..10)

        Direction.values().forEach { direction ->
            tilt(0, direction)
        }

        return minStepUntilHoleIn ?: -1
    }

    private fun tilt(
        step: Int,
        direction: Direction,
        redBeadPosition: Offset = startRedBeadPosition,
        blueBeadPosition: Offset = startBlueBeadPosition
    ) {
        val tiltLog = TiltLog(redBeadPosition, blueBeadPosition, direction)
        val currentStep = step + 1

        if (canTilt(currentStep, tiltLog)) {
            stepsBeforeTiltsMap[tiltLog] = currentStep

            val rollResult = roll(direction, redBeadPosition, blueBeadPosition)

            if (rollResult.holeIn) {
                if (minStepUntilHoleIn == null || minStepUntilHoleIn!! > currentStep) minStepUntilHoleIn = currentStep
                return
            } else if (rollResult.canRole) {
                Direction.values().forEach {
                    tilt(currentStep, it, rollResult.redBeadPosition, rollResult.blueBeadPosition)
                }
            }
        }
    }

    private fun canTilt(currentStep: Int, tiltLog: TiltLog): Boolean =
        currentStep < minStepUntilHoleIn ?: 11 && (stepsBeforeTiltsMap[tiltLog]
            ?: Int.MAX_VALUE) > currentStep

    fun roll(
        direction: Direction,
        redBeadPosition: Offset = startRedBeadPosition,
        blueBeadPosition: Offset = startBlueBeadPosition
    ): RollResult {
        var nextRedBeadPosition = redBeadPosition
        var nextBlueBeadPosition = blueBeadPosition

        while (getTileAt(nextBlueBeadPosition + direction.force) != Tile.Wall) {
            nextBlueBeadPosition += direction.force

            if (getTileAt(nextBlueBeadPosition) == Tile.Hole) {
                return RollResult(
                    nextRedBeadPosition,
                    nextBlueBeadPosition,
                    canRole = false,
                    holeIn = false
                )
            }
        }

        while (getTileAt(nextRedBeadPosition + direction.force) != Tile.Wall) {
            nextRedBeadPosition += direction.force

            if (getTileAt(nextRedBeadPosition) == Tile.Hole) {
                return RollResult(
                    nextRedBeadPosition,
                    blueBeadPosition,
                    canRole = true,
                    holeIn = true
                )
            }
        }

        if (nextBlueBeadPosition == nextRedBeadPosition) {
            if (direction.isPositive) {
                if (redBeadPosition < blueBeadPosition) {
                    nextRedBeadPosition -= direction.force
                } else {
                    nextBlueBeadPosition -= direction.force
                }
            } else {
                if (redBeadPosition > blueBeadPosition) {
                    nextRedBeadPosition -= direction.force
                } else {
                    nextBlueBeadPosition -= direction.force
                }
            }
        }

        return RollResult(
            nextRedBeadPosition,
            nextBlueBeadPosition,
            canRole = nextRedBeadPosition != redBeadPosition || nextBlueBeadPosition != blueBeadPosition,
            holeIn = false
        )
    }

    private fun convertToBoard(rawBoard: Iterable<String>): Array<Array<Tile>> = rawBoard.reversed().map { s ->
        s.toRow()
    }.toTypedArray()

    private fun String.toRow(): Array<Tile> = map { c ->
        Tile.values().first { it.char == c }
    }.toTypedArray()

    fun getTileAt(offset: Offset) = try {
        board[offset.y][offset.x]
    } catch (e: Exception) {
        Tile.Wall
    }
}