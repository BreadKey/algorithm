package programmers.stackqueue.crossbridge

import java.util.*

data class Truck(val weight: Int, var goTime: Int)

class Solution {
    fun solution(bridge_length: Int, weight: Int, truck_weights: IntArray): Int {
        val bridge = LinkedList<Truck>()
        val truckQueue = LinkedList<Truck>().apply {
            addAll(truck_weights.map { Truck(it, 0) })
        }
        var seconds = 0

        while (truckQueue.isNotEmpty() || bridge.isNotEmpty()) {
            seconds++

            if (isPassed(bridge, bridge_length, seconds)) {
                bridge.poll()
            }

            if (canGo(bridge, weight, truckQueue)) {
                bridge.add(truckQueue.poll().apply {
                    goTime = seconds
                })
            }
        }

        return seconds
    }

    fun isPassed(bridge: Queue<Truck>, length: Int, currentSeconds: Int): Boolean = bridge.isNotEmpty() &&
        bridge.first().goTime + length == currentSeconds

    fun canGo(bridge: Queue<Truck>, weight: Int, truckQueue: Queue<Truck>): Boolean = truckQueue.isNotEmpty() &&
        bridge.weight() + truckQueue.first().weight <= weight

    fun Queue<Truck>.weight(): Int = map { it.weight }.sum()
}