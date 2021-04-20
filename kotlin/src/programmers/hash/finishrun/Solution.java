package programmers.hash.finishrun;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public String solution(String[] participant, String[] completion) {
        String answer = "";

        HashMap<String, Integer> participantMap = new HashMap();

        for (String p : participant) {
            participantMap.merge(p, 1, Integer::sum);
        }

        for (String c : completion) {
            participantMap.merge(c, -1, Integer::sum);
        }

        for (Map.Entry<String, Integer> entry : participantMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }


        return answer;
    }
}
