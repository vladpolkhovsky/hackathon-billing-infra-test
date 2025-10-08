from fastapi import APIRouter

from app.controllers.healthcheck import healthcheck_route

router = APIRouter()

router.add_api_route(
    "/liveness",
    healthcheck_route,
    methods=["GET"],
)