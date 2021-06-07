from typing import Dict, List
import collections

def of(words: List[str]) -> List[List[str]]: 
    anagram_map: Dict[str, List[str]] = collections.defaultdict(list)
    
    for word in words:
        anagram = ''.join(sorted(word))

        anagram_map[anagram].append(word)

    return list(anagram_map.values())