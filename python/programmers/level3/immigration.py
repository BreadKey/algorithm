from typing import List


def solution(n: int, times: List[int]) -> int:
    times.sort()
    current_time = n * times[-1]
    high = current_time
    low = 0

    while True:
        passengers = sum([current_time // time for time in times])

        if passengers >= n:
            high = current_time
        else:
            low = current_time

        before = current_time
        current_time = (high + low + 1) // 2

        if before == current_time:
            break

    return current_time
