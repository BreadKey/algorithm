def solution(s: str) -> int:
    if len(s) == 1:
        return 1

    max_compress_length = len(s) // 2

    return min([len(compress(s, length)) for length in range(1, max_compress_length + 1)])


def compress(s: str, length: int) -> str:
    index = 0

    result = ""

    while index < len(s) - length:
        pattern = s[index: index + length]

        same_pattern_count = 0
        for next_index in range(index + length, len(s) - length + 1, length):
            if s[next_index: next_index + length] == pattern:
                same_pattern_count += 1
            else:
                break

        if same_pattern_count == 0:
            result += pattern
        else:
            result += f"{same_pattern_count + 1}" + pattern

        index += (same_pattern_count + 1) * length

    result += s[index:]

    return result
