from fastapi import FastAPI

from app.routes import healthcheck

app = FastAPI(
    root_path="/api/telemetry",
)

app.include_router(
    healthcheck.router,
    prefix="/health",
    tags=["health"],
)