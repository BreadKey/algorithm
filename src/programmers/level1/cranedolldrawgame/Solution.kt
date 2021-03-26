package programmers.level1.cranedolldrawgame

import java.util.*

class Solution {
    private val game = CraneDollDrawGame()

    fun solution(board: Array<IntArray>, moves: IntArray): Int {
        game.startGame(board)

        for (move in moves) {
            game.move(move)
            game.draw()
        }

        return game.burstDollCount
    }
}

class CraneDollDrawGame {
    companion object {
        const val EMPTY = 0
        const val SAME_DOLL_COUNT = 2
    }

    private val bucket = LinkedList<Int>()
    fun getBucket() = bucket.toList()

    private lateinit var board: Array<IntArray>

    var position = 1
        private set

    var burstDollCount = 0
        private set

    fun startGame(board: Array<IntArray>) {
        this.board = board
        bucket.clear()
        burstDollCount = 0
    }

    fun move(to: Int) {
        position = to
    }

    fun draw() {
        for (line in board) {
            val doll = line[position - 1]
            if (doll != EMPTY) {
                line[position - 1] = EMPTY
                bucket.add(doll)
                if (canBurst()) {
                    burst()
                }
                break
            }
        }
    }

    private fun canBurst() = bucket.size >= SAME_DOLL_COUNT &&
            bucket.takeLast(SAME_DOLL_COUNT).distinct().size == 1

    private fun burst() {
        repeat(SAME_DOLL_COUNT) {
            bucket.removeLast()
            burstDollCount++
        }
    }
}