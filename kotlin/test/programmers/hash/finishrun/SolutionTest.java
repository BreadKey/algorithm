package programmers.hash.finishrun;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void testFinishRun() {
        Solution solution = new Solution();
        String[] participants = {"leo", "kiki", "eden"};
        String[] completion = {"kiki", "eden"};
        assertEquals("leo", solution.solution(participants, completion));
    }
}