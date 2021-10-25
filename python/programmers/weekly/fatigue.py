from typing import List

def solution(k: int, input: List[List[int]]) -> int:
    solution = Solution()
    solution.adventure(k, input)

    return solution.max_count

class Solution:
    max_count = 0

    def adventure(self, fatigue: int, dungeons: List[List[int]], count: int = 0):
        if not dungeons:
            self.max_count = max(count, self.max_count)
            return

        for i in range(len(dungeons)):
            current_dungeon = dungeons[i]

            required_fatigue = current_dungeon[0]

            if fatigue >= required_fatigue:
                consumed_fatigue = current_dungeon[1]
                self.adventure(fatigue - consumed_fatigue, self.exclude(i, dungeons), count + 1)
            else:
                self.adventure(fatigue, self.exclude(i, dungeons), count)
    
    def exclude(self, index: int, dungeons: List[List[int]]) -> List[List[int]]:
        return dungeons[:index] + dungeons[index + 1:]
