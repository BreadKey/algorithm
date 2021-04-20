package samsung.q9

import java.util.*

class OperationWithBracket {
    companion object {
        const val PLUS = '+'
        const val MINUS = '-'
        const val MULTIPLY = '*'
        const val OPERATORS = "$PLUS$MINUS$MULTIPLY"
    }

    data class Expression(val a: Expression, val b: Expression, val operator: Char)

    fun operate(expression: String): Int {
        var finalExpression = ""

        val numberQueue = LinkedList<Int>()
        val operatorQueue = LinkedList<Char>()

        var currentToken = ""

        val bracketStack = LinkedList<Boolean>()

        expression.forEach { c ->
            when (c) {
                '(' -> {
                    bracketStack.add(true)
                }
                ')' -> {
                    bracketStack.pop()
                    finalExpression += operate(currentToken)
                    currentToken = ""
                }

                else -> {
                    if (bracketStack.isEmpty()) {
                        finalExpression += c
                    } else {
                        currentToken += c
                    }
                }
            }
        }

        finalExpression.forEachIndexed { index, c ->
            when (c) {
                PLUS, MINUS, MULTIPLY -> {
                    if (c == MINUS) {
                        if (index == 0 || OPERATORS.contains(finalExpression[index - 1])) {
                            currentToken += MINUS
                            return@forEachIndexed
                        }
                    }

                    val number = currentToken.toIntOrNull()
                    currentToken = ""

                    if (number != null)
                        numberQueue.add(number)

                    operatorQueue.add(c)
                }

                else -> {
                    currentToken += c
                }
            }
        }

        val lastNumber = currentToken.toIntOrNull()

        if (lastNumber != null) {
            numberQueue.add(lastNumber)
        }

        while (operatorQueue.isNotEmpty()) {
            numberQueue.addFirst(operate(numberQueue.poll(), numberQueue.poll(), operatorQueue.poll()))
        }

        return numberQueue.poll()
    }

    fun operate(a: Int, b: Int, operator: Char): Int {
        when (operator) {
            OperationWithBracket.PLUS -> return a + b
            OperationWithBracket.MINUS -> return a - b
            OperationWithBracket.MULTIPLY -> return a * b
        }

        throw(IllegalArgumentException())
    }

    fun addBracket(expression: String, offset: Int): String {
        val result = expression.toMutableList()

        result.add(offset + 2, ')')
        result.add(offset - 1, '(')

        return result.joinToString("")
    }

    private val temp = mutableListOf<Int>()

    fun calculateMaxResult(expression: String): Int {
        temp.clear()

        temp.add(operate(expression))
        testOperate(expression, 1)

        return temp.max()!!
    }

    fun testOperate(expression: String, currentOffset: Int) {
        if (currentOffset >= expression.length - 1) {
            return
        } else {
            val expressionWithBracket = addBracket(expression, currentOffset)

            temp.add(operate(expressionWithBracket))

            testOperate(expressionWithBracket, currentOffset + 6)
            testOperate(expression, currentOffset + 2)
        }
    }
}

fun main() {
    println(OperationWithBracket().calculateMaxResult(readLine()!!))
}