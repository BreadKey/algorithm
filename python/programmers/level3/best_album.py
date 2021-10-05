from typing import Dict, List


class Song:
    genre: str
    id: int
    play: int

    def __init__(self, genre: str, id: int, play: int) -> None:
        self.genre = genre
        self.id = id
        self.play = play


def solution(genres, plays):
    song_map: Dict[str, List[Song]] = {}

    for i, genre in enumerate(genres):
        song_map[genre] = (song_map.get(genre) or []) + \
            [Song(genre, i, plays[i])]

    for song_list in song_map.values():
        song_list.sort(key=lambda song: song.play, reverse=True)

    sorted_genre = sorted(song_map.keys(), key=lambda genre: sum(
        [song.play for song in song_map[genre]]), reverse=True)

    answer: List[int] = []
    for genre in sorted_genre:
        answer += [song.id for song in song_map[genre]][:2]

    return answer