import aiohttp

PROMETHEUS_URL = "http://localhost:9090/api/v1/query_range"

def prepare_results(data):
    results = data.get("data", {}).get("result", [])
    return [values for result in results for values in result["values"]]

async def prometheus_request(params):
    async with aiohttp.ClientSession() as session:
        async with session.get(PROMETHEUS_URL, params=params) as response:
            if response.status != 200:
                raise Exception(f"Ошибка запроса к Prometheus: {response}")
            data = await response.json()
            print(data)
            return prepare_results(data)
