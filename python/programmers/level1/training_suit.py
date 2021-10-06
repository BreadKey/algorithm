from typing import List


def solution(n: int, lost: List[int], reserve: List[int]):
    lost.sort()
    reserve.sort()
    reserve_but_lost = []

    for lost_number in lost:
        if lost_number in reserve:
            reserve_but_lost.append(lost_number)

    for number in reserve_but_lost:
        lost.remove(number)
        reserve.remove(number)

    count = n - len(lost)

    for lost_number in lost:
        if lost_number + 1 in reserve:
            if lost_number + 2 in lost:
                if lost_number - 1 in reserve:
                    count += 1
                    reserve.remove(lost_number - 1)
                    continue
            count += 1
            reserve.remove(lost_number + 1)

        elif lost_number - 1 in reserve:
            if lost_number - 2 in lost:
                if lost_number + 1 in reserve:
                    count += 1
                    reserve.remove(lost_number + 1)
                    continue
            count += 1
            reserve.remove(lost_number - 1)

    return count