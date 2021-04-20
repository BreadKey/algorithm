package samsung.q9

import org.junit.Assert.*
import org.junit.Test

class OperationWithBracketTest {
    val operationWithBracket = OperationWithBracket()

    @Test
    fun operateTest() {
        assertEquals(6, operationWithBracket.operate("1+2+3"))
    }

    @Test
    fun operateWithBracketTest() {
        assertEquals(8, operationWithBracket.operate("2+(2*3)"))
        assertEquals(41, operationWithBracket.operate("3+(8*7)-(9*2)"))
        assertEquals(-44, operationWithBracket.operate("(3+8)*(7-9)*2"))
    }

    @Test
    fun addBracketTest() {
        assertEquals("(1+2)", operationWithBracket.addBracket("1+2", 1))

        assertEquals(3, operationWithBracket.operate(operationWithBracket.addBracket("1+2", 1)))
    }

    @Test
    fun solveTest() {
        assertEquals(136, operationWithBracket.calculateMaxResult("3+8*7-9*2"))
        assertEquals(64, operationWithBracket.calculateMaxResult("8*3+5"))
        assertEquals(66, operationWithBracket.calculateMaxResult("8*3+5+2"))
        assertEquals(0, operationWithBracket.calculateMaxResult("1*2+3*4*5-6*7*8*9*0"))
        assertEquals(426384, operationWithBracket.calculateMaxResult("1*2+3*4*5-6*7*8*9*9"))
        assertEquals(24, operationWithBracket.calculateMaxResult("1-9-1-9-1-9-1-9-1-9"))
    }
}