# Automation Framework Docker

Maven Selenium TestNG framework (`org.example:AutomationFrameworkDocker:1.0-SNAPSHOT`). Tests can run locally or on Selenium Grid. The Docker image `uk040193/selenium-docker` packages the tests so they can run against a Grid hub.

Demo applications (from `src/test/resources/config/default.properties`):

- [Flight Reservation](https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html#page-top)
- [Vendor Portal](https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/vendor-app/index.html)

## Tech stack (`pom.xml`)

| Area | Version |
| --- | --- |
| Java (compiler) | 21 |
| Selenium | 4.33.0 |
| TestNG | 7.11.0 |
| Jackson | 2.19.0 |
| Logback | 1.5.18 |
| WebDriverManager | 6.1.0 |
| Artifact name | `selenium-docker` |
| Package output | `target/docker-resources` |

## `src` layout

```text
src/
├── main/
│   ├── java/org/example/Pages/
│   │   ├── BasePage.java
│   │   ├── fightreservation/
│   │   │   ├── RegistrationPage.java
│   │   │   ├── RegistrationConfirmationPage.java
│   │   │   ├── FlightSearchPage.java
│   │   │   ├── searchResultsPage.java
│   │   │   └── FlightsConfirmationPage.java
│   │   └── vendorportal/
│   │       ├── loginPage.java
│   │       └── dashboardPage.java
│   └── resources/logback.xml
└── test/
    ├── java/
    │   ├── com/example/tests/
    │   │   ├── BaseTest.java
    │   │   ├── flightreservation/
    │   │   └── vendorPortal/
    │   ├── com/example/Listener/TestListener.java
    │   └── utils/
    └── resources/
        ├── config/default.properties
        ├── test-data/
        └── test-suites/
```

### Page objects

`BasePage` stores `WebDriver`, a 30-second `WebDriverWait`, and initializes PageFactory. Every page implements `isPageLoaded()`.

**Flight reservation**

1. `RegistrationPage` — open URL, enter user/address details, register
2. `RegistrationConfirmationPage` — confirm registration, go to flight search
3. `FlightSearchPage` — select passenger count, search
4. `searchResultsPage` — select departure and arrival flights
5. `FlightsConfirmationPage` — read total price

**Vendor portal**

1. `loginPage` — open URL, login
2. `dashboardPage` — monthly/annual earnings, profit margin, inventory, table search, logout

### Tests

`BaseTest` loads config, creates the driver, stores it on the TestNG context, and quits after the test. Driver choice:

- `selenium.grid.enabled=false` → local Chrome via WebDriverManager
- `selenium.grid.enabled=true` → `RemoteWebDriver` at `http://<hub-host>:4444/wd/hub` (Chrome or Firefox from the `browser` property)

`@Listeners(TestListener.class)` is set on `BaseTest`. On failure, `TestListener` attaches a Base64 screenshot to the TestNG report.

**FlightReservationTest** (`dependsOnMethods` chain): registration → confirmation → search → results → price assert.

**VendorPortalTest**: login → dashboard values, search, logout.

Test data comes from JSON, mapped to records `FlightReservationTestData` and `VendorPortalTestData` via `JsonUtils` (Jackson). TestNG passes the file with parameter `testDataPath`.

### Configuration

`utils.Config` loads `config/default.properties`. JVM `-D` properties override file values.

| Property | Default | Meaning |
| --- | --- | --- |
| `selenium.grid.enabled` | `false` | Local vs Grid |
| `selenium.grid.url` | `http://%s:4444/wd/hub` | Hub URL template |
| `selenium.grid.hub.host` | `localhost` | Inserted into the URL |
| `browser` | `chrome` | `chrome` or `firefox` |
| `flightReservation.url` | CloudFront reservation app | Flight suite URL |
| `vendorPortal.url` | CloudFront vendor app | Vendor suite URL |

Keys are also listed in `utils.Constants`.

### Test data

**Flight reservation** (`src/test/resources/test-data/flightreservation/`)

| File | Passengers | Expected price |
| --- | --- | --- |
| `passenger-one.json` | One | `$584 USD` |
| `passenger-two.json` | Two | `$1169 USD` |
| `passenger-three.json` | Three | `$1753 USD` |
| `passenger-four.json` | Four | `$2338 USD` |

**Vendor portal** (`src/test/resources/test-data/vendorportal/`)

| File | User | Search |
| --- | --- | --- |
| `sam.json` | sam / sam | adams (8 results) |
| `mike.json` | mike / mike | miami (10 results) |
| `john.json` | john / john | 2024/01/01 (0 results) |

### TestNG suites

| Suite | File | Parallelism |
| --- | --- | --- |
| Flight Reservation | `src/test/resources/test-suites/flight-reservation.xml` | 4 tests, `thread-count="5"` |
| Vendor Portal | `src/test/resources/test-suites/vendor-portal.xml` | 3 tests, `thread-count="3"` |

### Logging

`logback.xml` logs INFO to the console (`test.log` file appender is defined but not attached to root).

### Helpers

- `ResourceLoader` — classpath first, then filesystem
- `JsonUtils` — generic JSON → Java
- `Demo` — sample of loading JSON and overriding `browser`

## Maven (`pom.xml`)

**Dependencies:** selenium-java, logback-classic, webdrivermanager (test), jackson-databind (test), testng (test).

**Surefire** (`mvn test`):

- `browser=chrome`
- `selenium.grid.enabled=true`
- Suites: vendor-portal.xml, flight-reservation.xml
- Thread count: 3
- Reports: `target/test-output`

Local Chrome (override Grid):

```bash
mvn clean test -Dselenium.grid.enabled=false
```

**Package** (`mvn package`) fills `target/docker-resources`:

- `maven-dependency-plugin` — jars into `libs/`
- `maven-jar-plugin` — main + test-jar class files into `libs/`
- `maven-resources-plugin` — copies `src/test/resources` (config, data, suites)

## Docker (`Dockerfile`)

Image: `bellsoft/liberica-openjdk-alpine:22`  
Workdir: `/home/selenium-docker`  
Installs `curl` and `jq` (to poll Grid status).  
Copies `target/docker-resources` and `runner.sh`.  
Entry point: `sh runner.sh`

The image has **no browser**. Tests must use Selenium Grid (`selenium.grid.enabled=true`). Inside a container, `localhost:4444` is the container itself, not the host. Use `host.docker.internal` (or the host IP) as the hub host.

Build (after `mvn package`):

```bash
docker build -t=uk040193/selenium-docker .
```

Run (from Dockerfile comments):

```bash
docker run -e HOST=host.docker.internal -e BROWSER_NAME=chrome -e THREAD_COUNT=3 -e TEST_SUITE_NAME=flight-reservation -v C:\Users\u1127187\AutomationFrameworkDocker\reports-docker:/home/selenium-docker/test-output uk040193/selenium-docker
```

Env vars expected by the image: `HOST`, `BROWSER_NAME`, `THREAD_COUNT`, `TEST_SUITE_NAME`. Reports are written under `/home/selenium-docker/test-output` (bind-mount that folder to keep HTML reports).

Equivalent Java command inside the image:

```bash
java "-Dselenium.grid.enabled=true" "-Dselenium.grid.hub.host=host.docker.internal" -cp "libs/*" org.testng.TestNG test-suites/flight-reservation.xml
```

## Jenkins (`Jenkinsfile`)

Jenkins checks out the project into the node workspace and runs three stages (`agent any`, Windows `bat`):

| Stage | Command |
| --- | --- |
| Build-jar | `mvn clean package -DskipTests` |
| Build-Docker-Image | `docker build -t=uk040193/selenium-docker .` |
| Push-Image | `docker push uk040193/selenium-docker` |
