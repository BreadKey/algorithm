from typing import List


class Question:
    number: int
    answer: int

    def __init__(self, number: int, answer: int) -> None:
        self.number = number
        self.answer = answer


class Student:
    answers: List[int]

    def __init__(self, answers: List[int]) -> None:
        self.answers = answers

    def answer(self, question: Question) -> int:
        length = len(self.answers)
        index = (question.number % length or length) - 1
        return self.answers[index]


class Stupid1(Student):
    def __init__(self) -> None:
        super().__init__([1, 2, 3, 4, 5])


class Stupid2(Student):
    def __init__(self) -> None:
        super().__init__([2, 1, 2, 3, 2, 4, 2, 5])


class Stupid3(Student):
    def __init__(self) -> None:
        super().__init__([3, 3, 1, 1, 2, 2, 4, 4, 5, 5])


def solution(answers) -> List[int]:
    stupids: List[Student] = [Stupid1(), Stupid2(), Stupid3()]
    corrects = [0, 0, 0]

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
