import logging

from app.routes import healthcheck
from app.tasks import lifespan
from fastapi import FastAPI

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",
    datefmt="%Y-%m-%d %H:%M:%S",
)
logging.root.setLevel(logging.INFO)

app = FastAPI(
    root_path="/api/telemetry",
    lifespan=lifespan
)

app.include_router(
    healthcheck.router,
    prefix="/health",
    tags=["health"],
)
