import asyncio
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.decorators import schedule
from app.queries import get_functions
from app.services.metrics import save_function_metrics


@schedule(60)
async def save_all_metrics():
    functions = await get_functions()
    tasks = [save_function_metrics(func) for func in functions]
    await asyncio.gather(*tasks)


@asynccontextmanager
async def lifespan(app: FastAPI):
    asyncio.create_task(save_all_metrics())
    yield
