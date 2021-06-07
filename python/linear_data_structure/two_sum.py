from typing import Dict, List


def answer(numbers: List[int], target: int) -> List[int]:
    number_map: Dict[int, int] = {}

    for index, number in enumerate(numbers):
        diff = target - number
        if diff in number_map:
            return [number_map[diff], index]

        number_map[number] = index