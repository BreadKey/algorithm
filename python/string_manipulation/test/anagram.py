from string_manipulation import anagram

assert(anagram.of(['eat', 'tea', 'tan', 'ate', 'nat', 'bat']) == [
    ['eat', 'tea', 'ate'],
    ['tan', 'nat'],
    ['bat']
])
