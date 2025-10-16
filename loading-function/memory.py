from random import randint

from fastapi import FastAPI

app = FastAPI()

@app.get("/")
async def memory() -> dict:
    size = randint(100, 500)
    memory = [b"m" * 1024 * 1024 for _ in range(size)]
    return {"size": size}
