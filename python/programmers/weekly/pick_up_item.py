from typing import List, Tuple
from enum import Enum, auto


class Tile(Enum):
    space = None
    horizontal = auto()
    vertical = auto()
    bottom_left = auto()
    bottom_right = auto()
    top_left = auto()
    top_right = auto()
    t = auto()
    t_90 = auto()
    t_180 = auto()
    t_270 = auto()
    item = auto()


class Rectangle:
    top: int
    bottom: int
    left: int
    right: int

    def __init__(self, input) -> None:
        self.top = input[3]
        self.bottom = input[1]
        self.left = input[0]
        self.right = input[2]


def solution(rectangle_inputs: List[List[int]], character_x: int, character_y: int, item_x: int, item_y: int) -> int:
    rectangles = [Rectangle(input) for input in rectangle_inputs]

    max_x = max(rectangles, key=lambda rectangle: rectangle.right).right
    max_y = max(rectangles, key=lambda rectangle: rectangle.top).top

    rectangle_map: List[List[List[Rectangle]]] = [
        [[] for _ in range(max_x + 2)] for _ in range(max_y + 2)]

    for rectangle in rectangles:
        add_rectangle(rectangle_map, rectangle)

    for rectangle in rectangles:
        clear_rectangle(rectangle_map, rectangle)

    path = to_path(rectangle_map)

    path[item_y][item_x] = Tile.item

    return walk(path, character_x, character_y)


def add_rectangle(rectangle_map: List[List[List[Rectangle]]], rectangle: Rectangle):
    for x in range(rectangle.left, rectangle.right + 1):
        rectangle_map[rectangle.top][x].append(rectangle)
        if rectangle.bottom != rectangle.top:
            rectangle_map[rectangle.bottom][x].append(rectangle)

    for y in range(rectangle.bottom + 1, rectangle.top):
        rectangle_map[y][rectangle.left].append(rectangle)
        if rectangle.right != rectangle.left:
            rectangle_map[y][rectangle.right].append(rectangle)


def clear_rectangle(rectangle_map: List[List[List[Rectangle]]], rectangle: Rectangle):
    for x in range(rectangle.left + 1, rectangle.right):
        for y in range(rectangle.bottom + 1, rectangle.top):
            rectangle_map[y][x].clear()


def to_path(rectangle_map: List[List[List[Rectangle]]]) -> List[List[Tile]]:
    result = [[Tile.space] * len(rectangle_map[0]) for _ in rectangle_map]
    for x in range(len(rectangle_map[0])):
        for y in range(len(rectangle_map)):
            result[y][x] = to_tile(rectangle_map[y][x], x, y)

    return result


def to_tile(rectangles: List[Rectangle], x: int, y: int) -> Tile:
    if not rectangles:
        return Tile.space

    length = len(rectangles)

    if length == 1:
        rectangle = rectangles[0]

        if rectangle.left == rectangle.right:
            return Tile.vertical
        elif rectangle.top == rectangle.bottom:
            return Tile.horizontal
        elif x == rectangle.left and y == rectangle.top:
            return Tile.top_left
        elif x == rectangle.right and y == rectangle.top:
            return Tile.top_right
        elif x == rectangle.left and y == rectangle.bottom:
            return Tile.bottom_left
        elif x == rectangle.right and y == rectangle.bottom:
            return Tile.bottom_right
        elif x in [rectangle.left, rectangle.right]:
            return Tile.vertical
        elif y in [rectangle.top, rectangle.bottom]:
            return Tile.horizontal

    else:
        up = 0
        down = 1
        left = 2
        right = 3
        opened = [False, False, False, False]

        if y in [rectangle.top for rectangle in rectangles]:
            opened[up] = True

            if x in [rectangle.left for rectangle in rectangles]:
                opened[left] = True

            if x in [rectangle.right for rectangle in rectangles]:
                opened[right] = True

        if y in [rectangle.bottom for rectangle in rectangles]:
            opened[down] = True

            if x in [rectangle.left for rectangle in rectangles]:
                opened[left] = True

            if x in [rectangle.right for rectangle in rectangles]:
                opened[right] = True

        if sum(opened) == 3:
            if opened[up] and opened[left] and opened[right]:
                return Tile.t_180
            if opened[down] and opened[left] and opened[right]:
                return Tile.t
            if opened[up] and opened[down] and opened[left]:
                return Tile.t_90
            else:
                return Tile.t_270

        else:
            if opened[up]:
                if opened[left]:
                    return Tile.bottom_right
                else:
                    return Tile.bottom_left
            else:
                if opened[left]:
                    return Tile.top_right
                else:
                    return Tile.top_left

    return Tile.space


def walk(path: List[List[Tile]], character_x: int, character_y: int, step_count=0, log: List[Tuple[int, int]] = []) -> int:
    tile = path[character_y][character_x]

    if path[character_y][character_x] == Tile.item:
        return step_count

    if tile in [Tile.t, Tile.t_90, Tile.t_180, Tile.t_270]:
        tile = t_to_corner(tile, character_x, character_y, log)

    next_points = get_opened_points(tile, character_x, character_y)
    next_points = list(
        filter(lambda point: path[point[1]][point[0]] and (not log or log[-1] != point), next_points))

    result = []
    for next_point in next_points:
        log.append((character_x, character_y))
        result.append(walk(
            path, next_point[0], next_point[1], step_count + 1, log.copy() if len(next_points) > 1 else log))

    if not next_points:
        log.append((character_x, character_y))
        return walk(path, log[-2][0], log[-2][1], step_count + 1, log)

    return min(result)


def t_to_corner(t: Tile, x: int, y: int, log: List[Tuple[int, int]]) -> Tile:
    if t == Tile.t:
        if x - log[-1][0] == 1:
            return Tile.top_right
        elif x - log[-1][0] == -1:
            return Tile.top_left

    elif t == Tile.t_180:
        if x - log[-1][0] == 1:
            return Tile.bottom_right
        elif x - log[-1][0] == -1:
            return Tile.bottom_left


def get_x(point: Tuple[int, int]) -> int: return point[0]
def get_y(point: Tuple[int, int]) -> int: return point[1]

def get_opened_points(tile: Tile, x: int, y: int) -> List[Tuple[int, int]]:
    if tile == Tile.horizontal:
        return [(x - 1, y), (x + 1, y)]

    elif tile == Tile.vertical:
        return [(x, y - 1), (x, y + 1)]

    elif tile == Tile.top_left:
        return [(x + 1, y), (x, y - 1)]

    elif tile == Tile.top_right:
        return [(x - 1, y), (x, y - 1)]

    elif tile == Tile.bottom_left:
        return [(x + 1, y), (x, y + 1)]

    elif tile == Tile.bottom_right:
        return [(x - 1, y), (x, y + 1)]
