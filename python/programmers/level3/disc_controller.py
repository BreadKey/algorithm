from typing import List


def solution(jobs: List[List[int]]) -> int:
    result: int = None

    def schedule(scheduled: List[List[int]], jobs: List[List[int]]):
        if not jobs:
            mean = _calculate_mean(scheduled)
            nonlocal result
            result = mean if result is None else min(result, mean)

            return

        for i in range(len(jobs)):
            remain_jobs = jobs.copy()
            next = scheduled.copy()
            next.append(remain_jobs.pop(i))
            schedule(next, remain_jobs)

    schedule([], jobs)

    return result


def _calculate_mean(jobs: List[List[int]]) -> int:
    total_time = 0
    time = 0

    for job in jobs:
        if time <= job[0]:
            process_time = job[1]
            time = job[0]
        else:
            process_time = time - job[0] + job[1]

        time += job[1]
        total_time += process_time

    return total_time // len(jobs)
