Для того чтобы пересобрать все контейнеры используйте:

`docker compose up -d --force-recreate --build`

Для того чтобы удалить все данные с вольюмов:

`docker compose down -v`

Доступные ресурсы:
- http://localhost/api/billing/swagger-ui/index.html Billing-service Swagger
- http://localhost/api/billing/v3/api-docs Billing-service Open Api Doc (V3)
- http://localhost/kafka-ui Kafka UI