package samsung.q4

fun main() {
    val firstLine = readLine()!!.split(' ').map { it.toInt() }
    val map = Array(firstLine[0]) {
        IntArray(firstLine[1])
    }

    val dicePosition = Offset(firstLine[3], firstLine[2])
    val commands = Array<Direction>(firstLine[4]) {
        Direction.Down
    }

    repeat(map.size) { y ->
        map[y] = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    }

    val lastLine = readLine()!!.split(' ').map { it.toInt() }
    repeat(commands.size) { i ->
        commands[i] = Direction.values()[lastLine[i] - 1]
    }

    val game = DiceGame(map = map, dicePosition = dicePosition, commands = commands)
    game.startGame()
}

data class Offset(val x: Int, val y: Int) {
    operator fun plus(other: Offset) = Offset(x + other.x, y + other.y)
}

enum class Direction(val vector: Offset) {
    Right(Offset(1, 0)), Left(Offset(-1, 0)), Up(Offset(0, -1)), Down(Offset(0, 1)),
}

fun Array<IntArray>.getValueAt(point: Offset) = this[point.y][point.x]
fun Array<IntArray>.setValueAt(point: Offset, value: Int) {
    this[point.y][point.x] = value
}

data class Dice(
    var top: Int,
    var front: Int,
    var bottom: Int,
    var back: Int,
    var left: Int,
    var right: Int
) {
    fun roll(direction: Direction) {
        val lastBottom = bottom
        when (direction) {
            Direction.Down -> {
                bottom = front
                front = top
                top = back
                back = lastBottom
            }

            Direction.Up -> {
                bottom = back
                back = top
                top = front
                front = lastBottom
            }

            Direction.Left -> {
                bottom = left
                left = top
                top = right
                right = lastBottom
            }

            Direction.Right -> {
                bottom = right
                right = top
                top = left
                left = lastBottom
            }
        }
    }
}

class DiceGame(val map: Array<IntArray>, val commands: Array<Direction>, val dicePosition: Offset) {
    private val dice: Dice
    private val mapWidth: Int = map.first().size
    private val mapHeight: Int = map.size

    init {
        assert(mapHeight in 1..20 && mapWidth in 1..20)
        dice = Dice(0, 0, 0, 0, 0, 0)
    }

    fun startGame() {
        val mapInPlay = map.copyOf()
        var currentDicePosition = dicePosition
        val diceInPlay = dice.copy()

        for (command in commands) {
            val nextPosition = currentDicePosition + command.vector

            if (isOutOfBound(nextPosition)) {
                continue
            }

            diceInPlay.roll(command)
            currentDicePosition = nextPosition

            val valueInTile = mapInPlay.getValueAt(currentDicePosition)

            if (valueInTile == 0)
                mapInPlay.setValueAt(currentDicePosition, diceInPlay.bottom)
            else {
                diceInPlay.bottom = valueInTile
                mapInPlay.setValueAt(currentDicePosition, 0)
            }

            println(diceInPlay.top)
        }
    }

    fun isOutOfBound(nextPosition: Offset) = nextPosition.x !in 0 until mapWidth || nextPosition.y !in 0 until mapHeight
}