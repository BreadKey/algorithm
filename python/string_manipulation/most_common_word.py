import collections
import re
from typing import List


def of(paragraph: str, banned: List[str] = []) -> str:
    cleaned: str = re.sub(
        '|'.join(banned + [r'[^a-z ]']), '', paragraph.lower())

    word_map = collections.defaultdict(int)

    for word in cleaned.split():
        word_map[word] += 1

    return max(word_map, key=word_map.get)
