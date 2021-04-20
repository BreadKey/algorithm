from typing import List


def sort(logs: List[str]) -> List[str]:
    words = []
    numbers = []

    for log in logs:
        content = log.split()[1]
        if content.isnumeric():
            numbers.append(log)
        else:
            words.append(log)

    words.sort(key=lambda log: (log.split()[1:], log.split()[0]))

    return words + numbers