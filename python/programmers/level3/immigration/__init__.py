from typing import List


def solution(n: int, times: List[int]) -> int:
    current_time = n * sum(times)

    step = current_time

    while True:
        passengers = sum([current_time // time for time in times])

        step = max(1, step // 2)

        if passengers > n:
            if step == 1: break
            current_time -= step
        elif passengers < n:
            current_time += step
        else:
            break

    while True:
        current_time -= 1

        passengers = sum([current_time // time for time in times])

        if passengers < n:
            return current_time + 1