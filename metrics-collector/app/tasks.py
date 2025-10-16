import asyncio
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.decorators import schedule
from app.queries import get_functions, append_new_functions
from app.services.metrics import save_function_metrics


@schedule(60)
async def save_all_metrics():
    await append_new_functions()
    functions = await get_functions()
    tasks = [save_function_metrics(func) for func in functions]
    await asyncio.gather(*tasks)


@asynccontextmanager
async def lifespan(app: FastAPI):
    asyncio.create_task(save_all_metrics())
    yield
