import collections
import re
from typing import List


def of(paragraph: str, banned: List[str] = []) -> str:
    words: List[str] = [word for word in re.sub(
        r'[^\w]', ' ', paragraph.lower()).split()
        if not word in banned]

    return collections.Counter(words).most_common(1)[0][0]
