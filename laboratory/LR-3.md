# Лабораторна робота №3 — «Налаштування IntelliJ + перша програма + Git/GitHub» (8 + 2 балів)

## Мета
- Встановити та налаштувати IDE (IntelliJ IDEA або WebStorm).
- Написати першу програму «Hello, World!» на будь-якій мові (приклад — JavaScript + Node.js).
- Підключити Git, створити репозиторій та завантажити код на GitHub.
- (Бонус +2) Оформити безкоштовну студентську підписку JetBrains: [Student Pack](https://www.jetbrains.com/academy/student-pack).

---

## Що здати (у Classroom)
1. **Посилання на GitHub-репозиторій** `se-basics-lab3-<прізвище>` з:
   - файлом коду `index.js` (або іншим, якщо інша мова),
   - `README.md` (ПІБ, група, мова/версії, інструкція запуску 2–3 рядки),
   - `.gitignore` (для Node — `node_modules/`).
2. **Скріншот із запущеною програмою** в IDE або терміналі (вивід «Hello, World!»).
3. (Бонус) **Короткий скріншот/підтвердження** активованої студентської підписки JetBrains.

---

## Оцінювання (макс. 10)
| Компонент | Бали |
|---|---:|
| IDE встановлено, створено та відкрито проект | 2 |
| Hello World працює (JS/Node або інша мова) | 2 |
| Git ініціалізовано, зроблено коміт(и) | 2 |
| Репозиторій на GitHub з коректною структурою та README | 2 |
| .gitignore додано та релевантний | 0.5 |
| Акуратна історія комітів (змістовні повідомлення) | 0.5 |
| **Бонус**: JetBrains Student Pack активовано | **+2** |

---

## Частина A. Встановлення та вибір IDE
- **Варіант 1 (рекомендовано з бонусом):** отримайте JetBrains Student Pack → встановіть **IntelliJ IDEA Ultimate** або **WebStorm**.
- **Варіант 2 (без бонусу):** встановіть **IntelliJ IDEA Community** (JS-підсвічування буде простішим; запускатимемо через термінал).

---

## Частина B. Встановлення Git (крок за кроком)

### Windows
```bash
# Завантажте Git for Windows, встановіть (типові налаштування підходять)
git config --global user.name "Ваше Ім'я"
git config --global user.email "you@example.com"
git --version
git config --list
```

### macOS
```bash
xcode-select --install
# або через Homebrew
brew install git
git config --global user.name "Ваше Ім'я"
git config --global user.email "you@example.com"
```

### Linux (Debian/Ubuntu)
```bash
sudo apt update
sudo apt install -y git
git config --global user.name "Ваше Ім'я"
git config --global user.email "you@example.com"
```

---

## Частина C. Створення та клонування GitHub репозиторію
1. Увійдіть на **github.com** → **New repository**:
   - Назва: `se-basics-lab3-<прізвище>`
   - Public або Private.
   - Додайте `README.md` та `.gitignore` → **Node**.
2. Клонування (HTTPS):
```bash
git clone https://github.com/<ваш_нікнейм>/se-basics-lab3-<прізвище>.git
cd se-basics-lab3-<прізвище>
```

---

## Частина D. Налаштування Node.js та запуск Hello World (JS)

### 1) Встановіть Node.js
- Завантажте LTS з [офіційного сайту](https://nodejs.org).
- Перевірте:
```bash
node -v
npm -v
```

### 2) Створіть файли проєкту
```bash
echo 'console.log("Hello, World!");' > index.js
```

Опційно:
```bash
npm init -y
```

Додайте до `package.json`:
```json
"scripts": { "start": "node index.js" }
```

### 3) Запуск програми
```bash
node index.js
```
Очікуваний результат:
```
Hello, World!
```

---

## Частина E. Інтеграція Git у IDE та публікація на GitHub

### 1) Перша фіксація
```bash
git add .
git commit -m "Init Lab 3: Hello World"
```

### 2) Підключення до GitHub (якщо локальний init)
```bash
git branch -M main
git remote add origin https://github.com/<ваш_нікнейм>/se-basics-lab3-<прізвище>.git
git push -u origin main
```

---

## Частина F. Бонус +2 бали — JetBrains Student Pack
1. Перейдіть: https://www.jetbrains.com/academy/student-pack  
2. Авторизуйтеся своїм студентським e-mail або завантажте підтвердження.
3. Активуйте ліцензію та встановіть **WebStorm** або **IntelliJ IDEA Ultimate**.
4. Додайте **скріншот підтвердження** у здачу.

---

## Рекомендації по структурі репозиторію
```
se-basics-lab3-<прізвище>/
 ├─ .gitignore
 ├─ README.md
 ├─ index.js
 └─ package.json
```

**Приклад `.gitignore` для Node:**
```
node_modules/
npm-debug.log*
yarn-debug.log*
yarn-error.log*
```

---

## Поради та поширені помилки
- Node не запускається → закрийте/відкрийте термінал, перевірте `node -v`.
- Push не працює → перевірте URL (HTTPS) та авторизацію GitHub.
- Порожній репозиторій → не забули `git add .` → `git commit` → `git push`?
- Кодування → використовуйте UTF-8 термінал.

---

## Академічна доброчесність
Код та інструкції — власного виконання. Допускається консультація, але **без копіювання** чужих репозиторіїв.

---

## Чек-лист
- [ ] IDE встановлена, проект відкритий.  
- [ ] `index.js` виводить **Hello, World!**.  
- [ ] Git налаштований (`user.name`, `user.email`).  
- [ ] Коміти зроблені; `.gitignore` додано.  
- [ ] Репозиторій на GitHub з README і посиланням у Classroom.  
- [ ] (Бонус) Оформлено Student Pack.
