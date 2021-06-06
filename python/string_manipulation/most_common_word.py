import re
from typing import Dict, List

def of(paragraph: str, banned: List[str] = []) -> str :
    cleaned: str = re.sub('|'.join(banned + [r'[^a-z ]' ]), '', paragraph.lower())

    word_map: Dict[str, int] = {}

    result:str = ''

    for word in cleaned.split():
        if word in word_map:
            word_map[word] += 1

            if word_map[result] < word_map[word]:
                result = word
        else:
            word_map[word] = 1
            if not result:
                result = word

    return result
    
            

