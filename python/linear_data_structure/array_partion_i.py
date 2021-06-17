from typing import List


def maxSumOfMinPair(numbers: List[int]) -> int:
    return sum(sorted(numbers)[::2])