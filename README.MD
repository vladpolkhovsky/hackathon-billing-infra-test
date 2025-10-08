# Работа и отладка на локали

Для того чтобы пересобрать все контейнеры используйте:

`docker compose up -d --force-recreate --build`

Для того чтобы удалить все данные с вольюмов:

`docker compose down -v`

Доступные ресурсы:
- http://localhost/api/billing/swagger-ui/index.html Billing-service Swagger
- http://localhost/api/billing/v3/api-docs Billing-service Open Api Doc (V3)
- http://localhost/kafka-ui Kafka UI

# Разворачивание на сервере

1. Каждый пуш в `master` создаёт action, который собирает образы и отправляет их в приватный репозиторий
2. Каждый пуш в `master` задаёт тег комиту, который далее может быть использован для запуска action, который развернёт указанную версию на сервере.

### Доступные ресурсы на сервере

* ФРОНТ https://faas-billing.vpolkhovsky.net/
* Kafka UI - https://faas-billing.vpolkhovsky.net/kafka-ui/
* Docker Registry UI - https://docker-ui.vpolkhovsky.net/
* Swarmpit - http://faas-billing.vpolkhovsky.net:888/ 
  * Логи `viewer` / пароль `viewer-viewer`
  * За админским доступом к владу.