from typing import List

MAX_INT = 0xFFFFFFFF


class Tile:
    space = 0
    horizontal = 1
    vertical = 2
    bottom_left = 3
    bottom_right = 4
    top_left = 5
    top_right = 6
    t = 7
    t_90 = 8
    t_180 = 9
    t_270 = 10
    item = 11


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

    rectangle_map = convert_path(rectangle_map)

    print_path(rectangle_map)

    return 0  # walk(path, character_x, character_y)


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


def print_path(path: List[List[int]], character: List[int] = None):
    reverse = list(reversed(path))

    for i in range(len(reverse)):
        y = len(reverse) - 1 - i
        string = f'{y}\t'
        for x in range(len(reverse[i])):
            if character and character[0] == x and character[1] == y:
                string += '★'
                continue

            value = path[y][x]

            # value = reverse[i][j]
            if value == Tile.space:
                string += '□'
            elif value == Tile.horizontal:
                string += '━'
            elif value == Tile.vertical:
                string += '┃'
            elif value == Tile.bottom_left:
                string += '┗'
            elif value == Tile.bottom_right:
                string += '┛'
            elif value == Tile.top_left:
                string += '┏'
            elif value == Tile.top_right:
                string += '┓'
            elif value == Tile.t:
                string += '┳'
            elif value == Tile.t_90:
                string += '┫'
            elif value == Tile.t_180:
                string += '┻'
            elif value == Tile.t_270:
                string += '┣'
            elif value == Tile.item:
                string += '⊙'
        print(string)


def convert_path(rectangle_map: List[List[List[Rectangle]]]) -> List[List[int]]:
    result = [[Tile.space] * len(rectangle_map[0]) for _ in rectangle_map]
    for x in range(len(rectangle_map[0])):
        for y in range(len(rectangle_map)):
            result[y][x] = convert(rectangle_map[y][x], x, y)

    return result


def convert(rectangles: List[Rectangle], x: int, y: int) -> int:
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
        connected_map = [False, False, False, False]

        if y in [rectangle.top for rectangle in rectangles]:
            connected_map[up] = True

            if x in [rectangle.left for rectangle in rectangles]:
                connected_map[left] = True

            if x in [rectangle.right for rectangle in rectangles]:
                connected_map[right] = True

        if y in [rectangle.bottom for rectangle in rectangles]:
            connected_map[down] = True

            if x in [rectangle.left for rectangle in rectangles]:
                connected_map[left] = True

            if x in [rectangle.right for rectangle in rectangles]:
                connected_map[right] = True

        if sum(connected_map) == 3:
            if connected_map[up] and connected_map[left] and connected_map[right]:
                return Tile.t_180
            if connected_map[down] and connected_map[left] and connected_map[right]:
                return Tile.t
            if connected_map[up] and connected_map[down] and connected_map[left]:
                return Tile.t_90
            else:
                return Tile.t_270

        else:
            if connected_map[up]:
                if connected_map[left]:
                    return Tile.bottom_right
                else:
                    return Tile.bottom_left
            else:
                if connected_map[left]:
                    return Tile.top_right
                else:
                    return Tile.top_left

    return Tile.space
