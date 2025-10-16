# Работа и отладка на локали

## Запуск knative

1. [Пройти все шаги из инструкции](https://github.com/stanislav-pimenov/knative-hackathon/blob/main/README.md)
2. Далее необходимо установить Helm Для Windows `winget install Helm.Helm`
3. `helm repo add prometheus-community https://prometheus-community.github.io/helm-charts`
4. `helm install prometheus prometheus-community/kube-prometheus-stack --namespace monitoring --set prometheus.service.type=NodePort`
5. `kubectl apply -f .\knative-servicemonitors.yaml`
6. `kubectl apply -f .\knative-podmonitor.yaml`
7. `kubectl apply -f .\additional-knative-servicemonitors.yaml`
8. `kubectl apply -f .\config-observability.yml`
9. `kubectl port-forward -n monitoring svc/prometheus-kube-prometheus-prometheus 9090:9090 &` - форвардим порт прометеуса
10. Проверяем что на локалхосте по указанному порту работате UI прометеуса и работает запрос `sum(container_cpu_usage_seconds_total{pod=~"echo.+"})`

### ПРОЦЕСС ЗАПУСКА ФУНКЦИЙ
* Можно запустить любую функцию, дополнительной настройки, которая требовала бы наша биллинг система не требется
* Ручной запусе, стандартный для knative:

## Запуск биллинг сервиса

Для того чтобы пересобрать все контейнеры используйте:

`docker compose up -d --force-recreate --build`

### ЕСЛИ ВЫ ИЗМЕНИлИ ПОРТ ПРОМЕТУСА ТО ПОМЕНЯЙТЕ ЕГО В [docker-compose.yml](docker-compose.yml) настройка `metrics-collector.env.PROMETHEUS_URL` тут меняем нужный порт, если localhost

Для того чтобы удалить все данные с вольюмов:

`docker compose down -v`

Доступные ресурсы (ТРЕБУЕТСЯ АВТОРИЗАЦИЯ) `admin` / `admin` :
- http://localhost:8080/app/ Фронт
- http://localhost:8080/api/billing/swagger-ui/index.html Billing-service Swagger
- http://localhost:8080/api/billing/v3/api-docs Billing-service Open Api Doc (V3)

# Разворачивание на сервере

1. Каждый пуш в `master` создаёт Action `Tag, Build and Push Images`, который собирает образы и отправляет их в приватный репозиторий. Задается тег комиту.
2. Запустив Action `Deploy tag to server` и выбрав **ТЕГ!!!**, который успешно собрался и запушился в регистри. Сервисы обновятся на стенде.
3. Для того чтобы обновить конфигурацию swarm необходимо запустить Action `Docker stack redeploy` он снесёт прошлый стек и обновит репозиторий на сервере. Запустит все сервисы с тегом latest.

### Доступные ресурсы на сервере

* Для пользования ресурсами необходима авторизация `admin` / `admin` на сервисе лежит реплика данных, если что-то локално не работает.
* ФРОНТ https://faas-billing.vpolkhovsky.net/
* Billing-service Swagger https://faas-billing.vpolkhovsky.net/api/billing/swagger-ui/index.html 
* Docker Registry UI - https://docker-ui.vpolkhovsky.net/
* Swarmpit - http://faas-billing.vpolkhovsky.net:888/ 
  * Логи `viewer` / пароль `viewer-viewer`
  * За админским доступом к владу.