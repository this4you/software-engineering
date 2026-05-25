# LR-22 — стартер: Horizontal Scaling

Швидкий старт для лабораторної №22 («Горизонтальне масштабування і
балансування»). Усі команди виконуються з цієї папки.

## Передумови

- Docker Desktop запущений.
- Порт `8080` на хості вільний (інакше — див. розділ «Якщо порт зайнятий»).

## Швидкий старт

### 1. Собрати образ

```bash
docker compose build
```

### 2. Baseline — 1 репліка

```bash
docker compose up -d
open http://localhost:8080
```

Оновлюй сторінку — `Hostname` не змінюється.

### 3. Scale out — 3 репліки

```bash
docker compose down
docker compose up -d --scale web=3
```

Оновлюй сторінку — `Hostname` змінюється на кожному оновленні.

### 4. Stateless trap — натисни «+1» багато разів

Лічильник буде «стрибати», бо кожна репліка має власний counter у пам'яті.

### 5. Fix через Redis

```bash
docker compose down
USE_REDIS=true docker compose up -d --scale web=3
```

Windows PowerShell:
```powershell
$env:USE_REDIS="true"
docker compose up -d --scale web=3
```

Тепер «+1» дає послідовне 1, 2, 3, …

### 6. Resilience — вбити одну репліку

```bash
docker compose ps                 # подивитись ID web-контейнерів
docker kill <container_id>        # вбити одну
```

Сайт продовжує працювати — nginx виключає мертву репліку з ротації.

### Прибирання

```bash
docker compose down -v
```

## Якщо щось не працює

**Порт 8080 зайнятий.**
Відредагуй `docker-compose.yml`, зміни `"8080:80"` на `"8081:80"`,
перезапусти. Адреса стане http://localhost:8081.

**Hostname не змінюється у Кроці 3.**
- Перевір, що справді масштабовано: `docker compose ps` має показати
  три рядки `web-1`, `web-2`, `web-3`.
- Перезапусти команду з `--scale web=3`.
- Жорстко оновлюй у браузері: `Cmd/Ctrl+Shift+R`.

**Лічильник не вирівнюється у Кроці 5.**
Перевір, що змінна потрапила в контейнер:
```
docker compose exec web env | grep USE_REDIS
```
Має бути `USE_REDIS=true`.

**502 Bad Gateway після `docker kill`.**
Зачекай 1-2 секунди й оновлюй ще раз. nginx має кеш DNS на 5 секунд, після
експірації він перестане намагатися йти на мертву репліку.

## Що всередині

| Файл                | Що робить                                                    |
|---------------------|--------------------------------------------------------------|
| `app.py`            | Flask-додаток: показує hostname, лічильник, кнопку «+1»      |
| `Dockerfile`        | Образ Python 3.12-slim з Flask                               |
| `requirements.txt`  | Flask + redis-py                                             |
| `docker-compose.yml`| Сервіси: web, nginx, redis                                   |
| `nginx.conf`        | nginx з resolver-варіантом upstream (нативне round-robin)    |
| `report-template.md`| Шаблон звіту, який треба здати                               |

## Як здавати

Заповни `report-template.md` (скопіюй у власний файл
`lab_scaling_<прізвище>.md`), додай скриншоти. Деталі — у файлі
завдання `LR-22.md`.
