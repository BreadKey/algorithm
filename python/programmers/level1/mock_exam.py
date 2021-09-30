from typing import List


class Question:
    number: int
    answer: int

    def __init__(self, number: int, answer: int) -> None:
        self.number = number
        self.answer = answer


class Student:
    def answer(self, question: Question) -> int:
        pass


class Stupid(Student):
    pattern: List[int]

    def __init__(self, answers: List[int]) -> None:
        self.pattern = answers

    def answer(self, question: Question) -> int:
        length = len(self.pattern)
        index = (question.number % length or length) - 1
        return self.pattern[index]

def solution(answers) -> List[int]:
    stupids: List[Stupid] = [
        Stupid([1, 2, 3, 4, 5]),
        Stupid([2, 1, 2, 3, 2, 4, 2, 5]),
        Stupid([3, 3, 1, 1, 2, 2, 4, 4, 5, 5])
    ]
    corrects = [0 for _ in stupids]

    questions = [Question(i + 1, answer) for i, answer in enumerate(answers)]

    max_correct = 0

    for question in questions:
        for i in range(len(stupids)):
            if stupids[i].answer(question) == question.answer:
                corrects[i] += 1
                max_correct = max(max_correct, corrects[i])

    answer = []
    for i, correct in enumerate(corrects):
        if correct == max_correct:
            answer.append(i + 1)

    return answer
