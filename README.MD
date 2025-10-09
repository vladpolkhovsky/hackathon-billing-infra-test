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

1. Каждый пуш в `master` создаёт Action `Tag, Build and Push Images`, который собирает образы и отправляет их в приватный репозиторий. Задается тег комиту.
2. Запустив Action `Deploy tag to server` и выбрав **ТЕГ!!!**, который успешно собрался и запушился в регистри. Сервисы обновятся на стенде.
3. Для того чтобы обновить конфигурацию swarm необходимо запустить Action `Docker stack redeploy` он снесёт прошлый стек и обновит репозиторий на сервере. Запустит все сервисы с тегом latest.


### Доступные ресурсы на сервере

* ФРОНТ https://faas-billing.vpolkhovsky.net/
* Kafka UI - https://faas-billing.vpolkhovsky.net/kafka-ui/
* Docker Registry UI - https://docker-ui.vpolkhovsky.net/
* Swarmpit - http://faas-billing.vpolkhovsky.net:888/ 
  * Логи `viewer` / пароль `viewer-viewer`
  * За админским доступом к владу.