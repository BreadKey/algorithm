from typing import List


def calculate(heights: List[int]) -> int:
    answer = 0
    valley = []
    is_descending = True
    for height in heights:
        valley.append(height)

        if len(valley) > 1:
            if is_descending:
                if valley[-1] > valley[-2]:
                    is_descending = False
            
            if not is_descending:
                if valley[-1] >= valley[0]:
                    answer += _trapping(valley)
                    valley = valley[-1:]
                    is_descending = True
                elif valley[-1] < valley[-2]:
                    answer += _trapping(valley[:-1])
                    valley = valley[-2:]
                    is_descending = True


    return answer

def _trapping(deque: List[int]) -> int:
    if len(deque) < 2:
        return 0

    while deque[0] > deque[-1]:
        deque = deque[1:]

    count = 0
    start_height = deque[0]

    for index in range(1, len(deque) - 1):
        count += start_height - deque[index]

    return count