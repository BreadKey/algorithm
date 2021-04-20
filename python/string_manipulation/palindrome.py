import re

def is_palindrome(s: str) -> bool:
    s = re.sub(r'[^a-z0-9]', '', s.lower())

    return s == s[::-1]