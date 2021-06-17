from typing import List


def solution(jobs: List[List[int]]) -> int:
    return _calculate_mean(jobs)

def _calculate_mean(jobs: List[List[int]]) -> int:
    process_times: List[int] = []
    time = 0

    for job in jobs:
        if time <= job[0]:
            process_time = job[1]
            time = job[0]
        else:
            process_time = time - job[0] + job[1]

        time += job[1]
        process_times.append(process_time)

    return sum(process_times) // len(process_times)