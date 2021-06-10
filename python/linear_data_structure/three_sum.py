from typing import List


def solution(numbers: List[int]) -> List[List[int]]:
    result = []
    numbers.sort()

    for i in range(len(numbers) - 2):
        if i > 0 and numbers[i] == numbers[i-1]:
            continue

        second = i + 1
        third = len(numbers) - 1

        while second < third:
            sum = numbers[i] + numbers[second] + numbers[third]

            if sum < 0:
                second += 1
            elif sum > 0:
                third -= 1
            else:
                result.append([numbers[i], numbers[second], numbers[third]])

                while second < third and numbers[second] == numbers[second + 1]:
                    second += 1
                while second < third and numbers[third] == numbers[third - 1]:
                    third -= 1

                second += 1
                third -= 1

    return result
