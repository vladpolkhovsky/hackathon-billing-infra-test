FROM python:3.13-slim

RUN apt-get update && apt-get install -y --no-install-recommends curl

# Install uv.
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

# Copy the application into the container.
COPY . /app

# Install the application dependencies.
WORKDIR /app
RUN uv sync --frozen --no-cache

CMD ["/app/.venv/bin/uvicorn", "app.main:app", "--proxy-headers", "--host", "0.0.0.0", "--port", "8081"]
