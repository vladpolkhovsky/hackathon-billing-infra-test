from datetime import datetime, timedelta

from app.services.requests import prometheus_request

def prepare_results(data):
    results = data.get("data", {}).get("result", [])
    return [values for result in results for values in result["values"]]


def prepare_params(start, end, step, query):
    start = start.timestamp()
    end = end.timestamp()
    return {
        "query": query,
        "start": start,
        "end": end,
        "step": step,
    }


def create_collector(query):
    async def collector(function):
        step = 10
        end = datetime.now()
        start = datetime.now() - timedelta(minutes=1)
        params = prepare_params(start, end, step, query.format(function_name=function["name"]))
        data = await prometheus_request(params)

        return prepare_results(data)

    return collector
