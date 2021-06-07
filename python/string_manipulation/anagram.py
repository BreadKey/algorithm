from typing import Dict, List

def of(words: List[str]) -> List[List[str]]: 
    anagram_map: Dict[str, List[str]] = {}
    
    for word in words:
        anagram = ''.join(sorted(word))

        if anagram not in anagram_map:
            anagram_map[anagram] = [word]
        else:
            anagram_map[anagram].append(word)

    return list(anagram_map.values())