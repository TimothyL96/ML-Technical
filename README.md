## ML Technical Assessment

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/43ceeb6d4cff4d8ea830d8e03bb7da90)](https://app.codacy.com/gh/TimothyL96/ML-Technical?utm_source=github.com&utm_medium=referral&utm_content=TimothyL96/ML-Technical&utm_campaign=Badge_Grade_Settings)
[![Java CI with Gradle](https://github.com/TimothyL96/ML-Technical/actions/workflows/gradle.yml/badge.svg)](https://github.com/TimothyL96/ML-Technical/actions/workflows/gradle.yml)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/43ceeb6d4cff4d8ea830d8e03bb7da90)](https://app.codacy.com/gh/TimothyL96/ML-Technical?utm_source=github.com&utm_medium=referral&utm_content=TimothyL96/ML-Technical&utm_campaign=Badge_Grade_Settings)
[![Maintainability](https://api.codeclimate.com/v1/badges/99781e4c1dcc7d590ffe/maintainability)](https://codeclimate.com/github/TimothyL96/ML-Technical/maintainability)
[![codecov](https://codecov.io/gh/TimothyL96/ML-Technical/branch/master/graph/badge.svg?token=fOMdBiCwEg)](https://codecov.io/gh/TimothyL96/ML-Technical)

### APIs Requirement:

1. **POST** /feature
2. **GET** /feature?email=XXX&featureName=XXX

OpenAPI Rapidoc: http://localhost:8080/swagger/rapidoc/

### Database:

The application is using H2 in-memory DB.

Access the database through web browser: http://localhost:8082/

```
url: jdbc:h2:mem:devDb;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
username: sa
password: ''
```

### Migration:

Generate changeset of diff between a base and a main DB (see gradle.properties):

`./gradlew liquibaseDiffChangelog -PrunList='diff' -Pdesc=description`


---

## Micronaut 3.7.0 Documentation

- [User Guide](https://docs.micronaut.io/3.7.0/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.7.0/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.7.0/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)

## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature liquibase documentation

- [Micronaut Liquibase Database Migration documentation](https://micronaut-projects.github.io/micronaut-liquibase/latest/guide/index.html)

- [https://www.liquibase.org/](https://www.liquibase.org/)

## Feature mockito documentation

- [https://site.mockito.org](https://site.mockito.org)

## Feature github-workflow-ci documentation

- [https://docs.github.com/en/actions](https://docs.github.com/en/actions)

## Feature data-jdbc documentation

- [Micronaut Data JDBC documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc)


