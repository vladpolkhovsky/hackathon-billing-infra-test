import os

import aiohttp

PROMETHEUS_URL = os.getenv("PROMETHEUS_URL", default="http://localhost:52467")
PROMETHEUS_URL_FULL = {
    "query": PROMETHEUS_URL + "/api/v1/query",
    "query_range": PROMETHEUS_URL + "/api/v1/query_range",
}


async def prometheus_request(params, request_type="query_range"):
    async with aiohttp.ClientSession() as session:
        async with session.get(PROMETHEUS_URL_FULL[request_type], params=params) as response:
            if response.status != 200:
                raise Exception(f"Ошибка запроса к Prometheus: {response}")
            data = await response.json()

            return data
