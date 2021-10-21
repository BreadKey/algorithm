from typing import Deque, List


def solution(prices: List[int]) -> List[int]:
    answer: List[int] = []
    price_deque = Deque(prices)

    while price_deque:
        current_price = price_deque.popleft()

        duration = 0

        for price in price_deque:
            duration += 1
            if current_price > price:
                break

        answer.append(duration)

    return answer
