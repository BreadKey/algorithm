from typing import List


def solution(jobs: List[List[int]]) -> int:
    result: int = None

    def make(current: List[List[int]], candidates: List[List[int]]):
        if not candidates:
            mean = _calculate_mean(current)
            nonlocal result
            result = mean if result is None else min(result, mean)

            return

        for i in range(len(candidates)):
            nextCandidates = candidates.copy()
            nextCurrent = current.copy()
            nextCurrent.append(nextCandidates.pop(i))
            make(nextCurrent, nextCandidates)

    make([], jobs)

    return result


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
