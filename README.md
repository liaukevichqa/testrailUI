# TestRail UI Automation

Учебный проект UI-автотестов для TestRail. Проект автоматизирует 10 ручных тест-кейсов C46–C55 и построен на Java 17, Gradle, JUnit 5 и Selenide.

## Стек

- Java 17
- Gradle Wrapper
- JUnit 5
- Selenide
- Selenium WebDriver
- Google Chrome
- Page Object

## Что проверяется

В проекте реализованы сценарии авторизации, навигации по TestRail, открытия проекта и набора тестов, работы с карточкой тест-кейса, изменения приоритета, валидации обязательного поля, глобального поиска и удаления временных данных.

Тесты, изменяющие данные, создают собственные временные кейсы с уникальными именами. Где возможно, созданные данные удаляются в `finally`, чтобы тесты не зависели от порядка выполнения и повторных запусков.

## Структура

```text
src/test/java/qa/testrail/ui
├── BaseTest.java
├── LoginTest.java
├── ProjectNavigationTest.java
├── TestCaseCrudTest.java
├── config
│   └── TestConfig.java
└── pages
    ├── LoginPage.java
    ├── ProjectOverviewPage.java
    ├── SearchResultsPage.java
    ├── TestCaseFormPage.java
    ├── TestCasePage.java
    └── TestCasesPage.java
```

Локаторы и действия со страницами находятся в Page Object-классах. Тестовые классы содержат сценарии и проверки на уровне пользовательских действий.

## Требования для запуска

- JDK 17
- Google Chrome
- доступ к тестируемому TestRail
- учётная запись TestRail

Устанавливать Gradle отдельно не требуется: в проекте есть Gradle Wrapper.

## Настройка авторизации

Секреты не хранятся в репозитории. Перед запуском задайте переменные окружения:

```text
TESTRAIL_EMAIL
TESTRAIL_PASSWORD
```

### PowerShell

Для текущего окна PowerShell:

```powershell
$env:TESTRAIL_EMAIL="your-email@example.com"
$env:TESTRAIL_PASSWORD="your-password"
```

Для сохранения переменных в Windows:

```powershell
setx TESTRAIL_EMAIL "your-email@example.com"
setx TESTRAIL_PASSWORD "your-password"
```

После `setx` откройте новое окно терминала.

## Настройка адреса приложения

По умолчанию используется:

```text
https://potok4.testrail.io
```

Адрес можно переопределить без изменения исходного кода через переменную окружения:

```powershell
$env:TESTRAIL_BASE_URL="https://example.testrail.io"
.\gradlew.bat clean test
```

Либо через system property Gradle:

```powershell
.\gradlew.bat clean test -DbaseUrl=https://example.testrail.io
```

## Запуск

Все тесты на Windows:

```powershell
.\gradlew.bat clean test
```

На Linux/macOS:

```bash
./gradlew clean test
```

Один класс:

```powershell
.\gradlew.bat test --tests "qa.testrail.ui.TestCaseCrudTest"
```

Один тест:

```powershell
.\gradlew.bat test --tests "qa.testrail.ui.TestCaseCrudTest.deleteOwnTemporaryTestCase"
```

## Отчёт и диагностика

Стандартный HTML-отчёт Gradle после запуска находится здесь:

```text
build/reports/tests/test/index.html
```

При падении Selenide сохраняет screenshot и page source в:

```text
build/reports/tests/
```

В репозиторий папка `build/` не коммитится.

## Соответствие ручных и автоматизированных кейсов

| TestRail ID | Название | Класс | Метод |
|---|---|---|---|
| C46 | Успешная авторизация | `LoginTest` | `successfulLogin` |
| C47 | Авторизация с неверным паролем | `LoginTest` | `loginWithWrongPassword` |
| C48 | Открытие проекта Automation Homework | `ProjectNavigationTest` | `openAutomationHomeworkProject` |
| C49 | Открытие набора тестов | `ProjectNavigationTest` | `openTestSuite` |
| C50 | Отображение секции Test Cases | `ProjectNavigationTest` | `openTestCaseSection` |
| C51 | Открытие карточки тест-кейса | `ProjectNavigationTest` | `openTestCaseCard` |
| C52 | Изменение приоритета временного кейса | `TestCaseCrudTest` | `changeTemporaryCasePriority` |
| C53 | Нельзя создать кейс без обязательного заголовка | `TestCaseCrudTest` | `cannotCreateCaseWithoutTitle` |
| C54 | Поиск временного кейса по уникальному названию | `TestCaseCrudTest` | `searchTemporaryCaseByUniqueTitle` |
| C55 | Удаление собственного временного кейса | `TestCaseCrudTest` | `deleteOwnTemporaryTestCase` |

Каждый тест содержит ID ручного тест-кейса в `@DisplayName`.

## Независимость тестов

- Каждый тест самостоятельно выполняет необходимую авторизацию.
- Порядок запуска не используется как предусловие.
- Временные сущности получают уникальные имена через `UUID`.
- C52 и C54 удаляют созданные данные в `finally`.
- C55 удаляет только созданный им временный кейс.
- Фиксированные `Thread.sleep()` и `Selenide.sleep()` не используются; ожидания реализованы условиями Selenide.

## Известные ограничения

Текущие сценарии используют учебные сущности TestRail:

- project ID: `2` (`Automation Homework`)
- suite ID: `6`
- section ID: `42`
- кейсы C46–C65 должны существовать согласно учебным данным
- C56 используется в C55 как контрольный существующий кейс

Если структура учебного TestRail или идентификаторы будут изменены, соответствующие тестовые данные потребуется обновить.

## Безопасность репозитория

В репозиторий не должны попадать:

- пароли;
- API-токены;
- `.env` файлы;
- папка `build/`;
- `.gradle/`;
- `.idea/`;
- временные файлы IDE.

Правила исключения находятся в `.gitignore`.
