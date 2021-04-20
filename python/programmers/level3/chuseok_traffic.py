from datetime import datetime, timedelta
from typing import List, Tuple

def solution(lines: List[str]) -> int:
    format = "%Y-%m-%d %H:%M:%S.%f"

    jobs: List[Tuple[datetime, datetime]] = []

    event_times: List[datetime] = []

    for line in lines:
        parts = line.split()

        end_datetime = datetime.strptime(" ".join(parts[:2]), format)

        seconds = parts[2][:-1]

        start_datetime = end_datetime - \
            timedelta(seconds=float(seconds) - 0.001)

        jobs.append((start_datetime, end_datetime))

        event_times.append(start_datetime)
        event_times.append(end_datetime)

    max = 0

    for event_time in event_times:
        count = 0

        window_start = event_time
        window_end = window_start + timedelta(seconds=0.999)

        for job in jobs:
            if window_start <= job[1] <= window_end or \
                window_start <= job[0] <= window_end or \
                    job[0] <= window_start and job[1] >= window_end:
                count = count + 1

        if count > max:
            max = count

    return max
