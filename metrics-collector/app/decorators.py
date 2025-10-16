import asyncio
import logging
from functools import wraps
from typing import Coroutine


logger = logging.getLogger(__name__)

def schedule(delay: float):
    def wrapper(coro: Coroutine):
        logger.info(f"scheduling {coro.__name__} every {delay} seconds")

        @wraps(coro)
        async def func(*args, **kwargs):
            logger.info(f"bind {coro.__name__} with args={args} kwargs={kwargs}")
            while True:
                result = await coro(*args, **kwargs)
                logger.info(f"{coro.__name__} returned {result}")
                await asyncio.sleep(delay)

        return func

    return wrapper