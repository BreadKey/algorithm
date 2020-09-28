package programmers.stackqueue.developfeature

import java.util.*

class Solution {
    private val featureCounts = mutableListOf<Int>()
    private lateinit var progressQueue: LinkedList<Int>
    private lateinit var speedQueue: LinkedList<Int>

    fun solution(progresses: IntArray, speeds: IntArray): IntArray {
        featureCounts.clear()

        progressQueue = LinkedList<Int>().apply {
            addAll(progresses.toList())
        }

        speedQueue = LinkedList<Int>().apply {
            addAll(speeds.toList())
        }

        while (progressQueue.isNotEmpty()) {
            for (i in 0 until progressQueue.size) {
                progressQueue[i] = progressQueue[i] + speedQueue[i]
            }

            release()
        }

        return featureCounts.toIntArray()
    }

    fun release() {
        var releasedFeatureCount = 0

        while (progressQueue.isNotEmpty() && progressQueue.first() >= 100) {
            progressQueue.poll()
            speedQueue.poll()
            releasedFeatureCount++
        }

        if (releasedFeatureCount > 0) {
            featureCounts.add(releasedFeatureCount)
        }
    }
}