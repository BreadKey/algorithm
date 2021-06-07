def of(word: str) -> str:
    word_length = len(word)
    logest_length = 0

    if word_length < 2 or _is_palindrome(word):
        return word

    for index in range(word_length):
        if index + logest_length > word_length:
            break
        
        for stop in range(index + logest_length + 1, word_length + 1):
            substr = word[index:stop]

            if _is_palindrome(substr):
                length = stop - index

                if length > logest_length:
                    logest_length = length
                    answer = substr

    return answer

def _is_palindrome(input: str) -> bool:
    return input == input[::-1]