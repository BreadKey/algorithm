from typing import List


class Tile:
    space = 0
    horizontal = 1
    vertical = 2
    bottom_left = 3
    bottom_right = 4
    top_left = 5
    top_right = 6
    intersection = 7
    item = 8


def solution(rectangles: List[List[int]], character_x: int, character_y: int, item_x: int, item_y: int) -> int:
    max_x = max(rectangles, key=lambda rectangle: rectangle[2])[2]
    max_y = max(rectangles, key=lambda rectangle: rectangle[3])[3]

    path = [[Tile.space] * (max_x + 1) for _ in range(max_y + 1)]

    for rectangle in rectangles:
        add_rectangle(path, rectangle)

    for rectangle in rectangles:
        remove_rectangle(path, rectangle)

    path[item_y][item_x] = Tile.item

    walk_result: List[int] = []

    up = character_y + 1
    down = character_y - 1
    left = character_x - 1
    right = character_x + 1

    if tile_at(path, character_x, up):
        walk_result.append(walk(path, character_x, up, 0, 1, 1))

    if tile_at(path, character_x, down):
        walk_result.append(
            walk(path, character_x, down,  0, -1, 1))

    if tile_at(path, left, character_y):
        walk_result.append(
            walk(path, left, character_y, -1, 0, 1))

    if tile_at(path, right, character_y):
        walk_result.append(
            walk(path, right, character_y, 1, 0, 1))

    # print(walk_result)

    return min(walk_result)


def add_rectangle(path: List[List[int]], rectangle: List[int]):
    bottom = rectangle[1]
    top = rectangle[3]

    left = rectangle[0]
    right = rectangle[2]

    for x in range(rectangle[0] + 1, rectangle[2]):
        path[bottom][x] = Tile.intersection if path[bottom][x] else Tile.horizontal
        if top != bottom:
            path[top][x] = Tile.intersection if path[top][x] else Tile.horizontal

    for y in range(rectangle[1] + 1, rectangle[3]):
        path[y][left] = Tile.intersection if path[y][left] else Tile.vertical
        if right != left:
            path[y][right] = Tile.intersection if path[y][right] else Tile.vertical

    path[bottom][left] = Tile.intersection if path[bottom][left] else Tile.bottom_left
    path[bottom][right] = Tile.intersection if path[bottom][right] else Tile.bottom_right
    path[top][left] = Tile.intersection if path[top][left] else Tile.top_left
    path[top][right] = Tile.intersection if path[top][right] else Tile.top_right


def remove_rectangle(path: List[List[int]], rectangle: List[int]):
    left = rectangle[0]
    bottom = rectangle[1]
    right = rectangle[2]
    top = rectangle[3]

    for x in range(left + 1, right):
        for y in range(bottom + 1, top):
            path[y][x] = Tile.space


def print_path(path: List[List[int]], character: List[int] = None):
    reverse = list(reversed(path))

    for i in range(len(reverse)):
        y = len(reverse) - 1 - i
        string = f'{y}'
        for j in range(len(reverse[i])):
            if character and character[0] == j and character[1] == y:
                string += '★'
                continue

            value = reverse[i][j]
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
            elif value == Tile.intersection:
                string += '╋'
            elif value == Tile.item:
                string += '⊙'
        print(string)


def walk(path: List[List[int]], character_x: int, character_y: int, step_x: int, step_y: int, step_count: int = 0):
    tile = tile_at(path, character_x, character_y)

    print(f"Step: {step_count}")
    print_path(path, [character_x, character_y])

    if step_count == 20:
        return

    if tile == Tile.item:
        return step_count

    if tile in [Tile.bottom_left, Tile.top_left]:
        if step_x == 0:
            return walk(path, character_x + 1, character_y, 1, 0, step_count + 1)
        else:
            next_step_y = 1 if tile == Tile.bottom_left else -1
            return walk(path, character_x, character_y +
                        next_step_y, 0, next_step_y, step_count + 1)

    elif tile in [Tile.bottom_right, Tile.top_right]:
        if step_x == 0:
            return walk(path, character_x - 1, character_y, -1, 0, step_count + 1)
        else:
            next_step_y = 1 if tile == Tile.bottom_right else -1
            return walk(path, character_x, character_y +
                        next_step_y, 0, next_step_y, step_count + 1)

    elif tile == Tile.intersection:
        walk_result = []

        if step_x != 0:
            down_tile = tile_at(path, character_x, character_y - 1)
            up_tile = tile_at(path, character_x, character_y + 1)

            if down_tile not in [Tile.space, Tile.intersection, Tile.horizontal]:
                walk_result.append(
                    walk(path, character_x, character_y - 1, 0, -1, step_count + 1))
            if up_tile not in [Tile.space, Tile.intersection, Tile.horizontal]:
                walk_result.append(
                    walk(path, character_x, character_y + 1, 0, 1, step_count + 1)
                )

            if not walk_result:
                if down_tile not in [Tile.space, Tile.horizontal]:
                    walk_result.append(
                        walk(path, character_x, character_y - 1, 0, -1, step_count + 1))
                if up_tile not in [Tile.space, Tile.horizontal]:
                    walk_result.append(
                        walk(path, character_x, character_y +
                             1, 0, 1, step_count + 1)
                    )

        else:
            left_tile = tile_at(path, character_x - 1, character_y)
            right_tile = tile_at(path, character_x + 1, character_y)

            if left_tile not in [Tile.space, Tile.intersection, Tile.vertical]:
                walk_result.append(
                    walk(path, character_x - 1,
                         character_y, -1, 0, step_count + 1)
                )
            if right_tile not in [Tile.space, Tile.intersection, Tile.vertical]:
                walk_result.append(
                    walk(path, character_x + 1, character_y, 1, 0, step_count + 1)
                )

            if not walk_result:
                if left_tile not in [Tile.space, Tile.vertical]:
                    walk_result.append(
                        walk(path, character_x - 1,
                             character_y, -1, 0, step_count + 1)
                    )
                if right_tile not in [Tile.space, Tile.vertical]:
                    walk_result.append(
                        walk(path, character_x + 1,
                             character_y, 1, 0, step_count + 1)
                    )

        if walk_result:
            return min(walk_result)

    if tile_at(path, character_x + step_x, character_y + step_y):
        return walk(path, character_x + step_x, character_y + step_y, step_x, step_y, step_count + 1)
    return walk(path, character_x - step_x, character_y - step_y, -step_x, -step_y, step_count + 1)


def tile_at(path: List[List[int]], x: int, y: int) -> int:
    if x not in range(len(path[0])) or y not in range(len(path)):
        return Tile.space

    return path[y][x]
