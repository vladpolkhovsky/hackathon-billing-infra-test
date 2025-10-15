from datetime import datetime
import os

import asyncpg

DB_CONFIG = {
    "user": os.getenv("DB_USER", default="postgres"),
    "password": os.getenv("DB_PASSWORD", default="postgres"),
    "database": os.getenv("DB_DATABASE", default="billing"),
    "host": os.getenv("DB_HOST", default="localhost"),
    "port": os.getenv("DB_PORT", default=6000)
}

async def get_functions():
    conn = await asyncpg.connect(**DB_CONFIG)
    try:
        rows = await conn.fetch("SELECT id, name, created_at, updated_at FROM billing.functions")
        return [dict(row) for row in rows]
    finally:
        await conn.close()


async def save_metrics(function_id: str, metrics: list, metric_type: str):
    conn = await asyncpg.connect(**DB_CONFIG)
    try:
        rows = [(function_id, float(value[1]), metric_type, datetime.fromtimestamp(value[0])) for value in metrics]
        await conn.executemany(
            """
            INSERT INTO billing.metrics (function_id, value, type, created_at)
            VALUES ($1, $2, $3, $4)
            """,
            rows
        )
    finally:
        await conn.close()
