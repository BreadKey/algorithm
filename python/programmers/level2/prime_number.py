from typing import Dict, List


def solution(numbers):
    return Solution().solve(numbers)

class Solution:
    answer = 0
    prime_map: Dict[int, bool] = {}

    def solve(self, numbers: str) -> int:
        int_numbers = [int(number) for number in numbers]

        self.check(None, int_numbers)

        return self.answer

    def check(self, current: int, remains: List[int]):
        if current is not None and self.prime_map.get(current) is None:
            result = self.is_prime(current)
            self.prime_map[current] = result

            if result:
                self.answer += 1

        for i in range(len(remains)):
            copy = remains.copy()
            next = copy.pop(i)

            self.check(int(f"{current or ''}{next}"), copy.copy())

    # https://en.wikipedia.org/wiki/Primality_test
    def is_prime(self, n: int) -> bool:
        """Primality test using 6k+-1 optimization."""
        if n <= 3:
            return n > 1
        if n % 2 == 0 or n % 3 == 0:
            return False
        i = 5
        while i ** 2 <= n:
            if n % i == 0 or n % (i + 2) == 0:
                return False
            i += 6
        return True