FROM python:3.9

WORKDIR /app

RUN pip install fastapi uvicorn

COPY . .

CMD ["uvicorn", "memory:app", "--host", "0.0.0.0", "--port", "81"]
