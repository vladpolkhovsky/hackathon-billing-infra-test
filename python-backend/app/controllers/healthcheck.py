from app.models.responses.healthcheck import HealthcheckResponseModel


def healthcheck_route() -> HealthcheckResponseModel:
    response = HealthcheckResponseModel(
        status="OK"
    )

    return response
