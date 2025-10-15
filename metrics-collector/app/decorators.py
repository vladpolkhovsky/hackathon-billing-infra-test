import asyncio
from functools import wraps
from typing import Coroutine


def schedule(delay: float):
    def wrapper(coro: Coroutine):
        print(f"scheduling {coro.__name__} every {delay} seconds")

        @wraps(coro)
        async def func(*args, **kwargs):
            print(f"bind {coro.__name__} with args={args} kwargs={kwargs}")
            while True:
                result = await coro(*args, **kwargs)
                print(f"{coro.__name__} returned {result}")
                await asyncio.sleep(delay)

        return func

    return wrapper