from app.queries import save_metrics
from app.services.collectors import create_collector

Metrics = {
    "memory_usage": create_collector(r'sum(container_memory_usage_bytes{{pod=~"{function_name}.+"}})'),
    "cpu_usage": create_collector(r'sum(rate(container_cpu_usage_seconds_total{{pod=~"{function_name}.+"}}[1m]))'),
}

async def save_function_metrics(function):
    for metric_type, collector in Metrics.items():
        metrics = await collector(function)
        await save_metrics(function["id"], metrics, metric_type)
