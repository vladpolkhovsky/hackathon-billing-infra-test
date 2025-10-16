from app.queries import save_metrics
from app.services.collectors import create_collector
from logging import getLogger

logger = getLogger(__name__)

Metrics = {
    "memory_usage": create_collector(r'sum(container_memory_usage_bytes{{pod=~"{function_name}.+"}})'),
    "cpu_usage": create_collector(r'sum(rate(container_cpu_usage_seconds_total{{pod=~"{function_name}.+"}}[1m]))'),
    "request_count": create_collector(r'sum(rate(activator_request_count{{revision_name=~"{function_name}.+"}}[1m]))'),
}

async def save_function_metrics(function):
    for metric_type, collector in Metrics.items():
        metrics = await collector(function)
        logger.info(f"Saving metrics to databse {function["name"]} [{function["id"]}] / {metric_type}")
        await save_metrics(function["id"], metrics, metric_type)
