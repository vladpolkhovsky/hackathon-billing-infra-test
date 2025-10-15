from pydantic import BaseModel

class HealthcheckResponseModel(BaseModel):
    status: str
