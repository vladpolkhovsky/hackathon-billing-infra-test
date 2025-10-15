from fastapi import FastAPI

from app.routes import healthcheck
from app.tasks import lifespan

app = FastAPI(
    root_path="/api/telemetry",
    lifespan=lifespan
)

app.include_router(
    healthcheck.router,
    prefix="/health",
    tags=["health"],
)
