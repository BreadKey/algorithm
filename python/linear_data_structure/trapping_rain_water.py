from typing import List


def calculate(heights: List[int]) -> int:
    return sum([_trap_water(valley) for valley in _capture_vallies(heights)])


def _capture_vallies(heights: List[int]) -> List[List[int]]:
    result = []
    is_descending = True

    valley = []
    for height in heights:
        valley.append(height)

        if len(valley) > 1:
            if is_descending:
                if valley[-2] < valley[-1]:
                    is_descending = False

            if not is_descending:
                end_of_valley = None

                if valley[-1] >= valley[0]:
                    result.append(valley)
                    end_of_valley = -1

                elif valley[-1] < valley[-2]:
                    result.append(valley[:-1])
                    end_of_valley = -2

                if end_of_valley:
                    valley = valley[end_of_valley:]
                    is_descending = True

    if not is_descending and len(valley) > 2:
        result.append(valley)

    return result


def _trap_water(valley: List[int]) -> int:
    valley_height = min(valley[0], valley[-1])

    return sum([valley_height - height for height in valley[1:-1]])
