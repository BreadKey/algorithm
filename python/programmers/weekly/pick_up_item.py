from typing import List


def solution(rectangles: List[List[int]], character_x: int, character_y: int, item_x: int, item_y: int) -> int:
    max_x = 0
    max_y = 0

    for rectangle in rectangles:
        max_x = max(max_x, rectangle[2])
        max_y = max(max_y, rectangle[3])

    path = [[0] * (max_x + 1) for _ in range(max_y + 1)]

    for rectangle in rectangles:
        for x in range(rectangle[0], rectangle[2] + 1):
            path[rectangle[1]][x] += 1
            path[rectangle[3]][x] += 1
        for y in range(rectangle[1] + 1, rectangle[3]):
            path[y][rectangle[0]] += 1
            path[y][rectangle[2]] += 1

    pad = 2
    stored_path = [[0] * (len(path[0]) + pad) for _ in range(len(path) + pad)]
    clear_path(path, stored_path)
    stored_path = remove_pad(stored_path)




def clear_path(path: List[List[int]], stored_path: List[List[int]], x: int = 0, y: int = 0):
    if x < 0 or y < 0 or x > len(stored_path[0]) - 1 or y > len(stored_path) - 1:
        return

    if stored_path[y][x] != 0:
        return

    stored_path[y][x] = -1 if x == 0 or y == 0 or x == len(stored_path[0]) - 1 or y == len(stored_path) - 1 else path[y - 1][x - 1] or -1

    if stored_path[y][x] != -1:
        return

    clear_path(path, stored_path, x - 1, y)
    clear_path(path, stored_path, x + 1, y)
    clear_path(path, stored_path, x, y - 1)
    clear_path(path, stored_path, x, y + 1)
    clear_path(path, stored_path, x + 1, y + 1)
    clear_path(path, stored_path, x - 1, y + 1)
    clear_path(path, stored_path, x - 1, y - 1)
    clear_path(path, stored_path, x + 1, y - 1)

def remove_pad(path: List[List[int]]) -> List[List[int]]:
    path = path[1: len(path) - 1]
    path = [row[1:len(row) - 1] for row in path]

    return path

def walk(path: List[List[int]], character_x: int, character_y: int, item_x: int, item_y: int, step_x: int, step_y: int, step_count: int = 0):
    if character_x == item_x and character_y == item_y:
        return step_count

    if path[character_y + step_y][character_x + step_x] == 1:
        return walk(path, character_x + step_x, character_y + step_y, item_x, item_y, step_x, step_y, step_count + 1)
    
    if step_x == 0:
        if path[character_y][character_x - 1] == 1:
            return walk(path, character_x - 1, character_y, item_x, )