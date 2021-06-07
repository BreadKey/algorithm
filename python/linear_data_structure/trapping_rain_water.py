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
                    answer += _trap_water(valley)
                    valley = valley[-1:]
                    is_descending = True
                elif valley[-1] < valley[-2]:
                    answer += _trap_water(valley[:-1])
                    valley = valley[-2:]
                    is_descending = True


    return answer

def _trap_water(valley: List[int]) -> int:
    if len(valley) < 2:
        return 0

    while valley[0] > valley[-1]:
        valley = valley[1:]

    count = 0
    start_height = valley[0]

    for index in range(1, len(valley) - 1):
        count += start_height - valley[index]

    return count