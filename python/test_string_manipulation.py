from string_manipulation import palindrome, logs

assert palindrome.is_palindrome("A man, a plan, a canal: Panama")

assert logs.sort(["dig1 8 1 5 1", "let1 art can", "dig2 3 6", "let2 own kit dig", "let3 art zero"]) == ["let1 art can", "let3 art zero", "let2 own kit dig", "dig1 8 1 5 1", "dig2 3 6"]