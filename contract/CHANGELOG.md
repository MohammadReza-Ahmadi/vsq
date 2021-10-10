# Changelog
All notable changes to this project will be documented in this file.

This project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.13] - 2021-5-26
### Added
- kafka configuration for all tests
- configuration for contract kafka producers in application.yml 

### Changed
- updated payment mapper to support model changes

## [0.1.12] - 2021-5-23
### Added
- new contract history controller
- delete for suggestions

### Changed
- improvement in tests
- updated dependencies for test classes
- resolved bugs in contract and suggestion create/edit


## [0.1.11] - 2021-5-15
### Added
- history endpoint in `CurrentContractController`
- repository methods to find contracts by buyer, seller, title and status to be used for getting list of current and history contracts in ContractRepository
- test for contract history
- test for search term 

### Changed  
- getAll in CurrentContractController to filter out done contracts
- problematic named query for findAllByUser in ContractService
- signature of findAllContractModels in ContractService

## [0.1.10] - 2021-5-10
### Changed
- GetAllContractTemplateResponse model to support numberOfRepeats

## [0.1.9] - 2021-5-10
### Added
- `\repeatable-and-viewable` endpoint to controller
- `UpdateContractTemplateRepeatableAndViewablePropertiesRequest` DTO
- `updateViewableAndRepeatable` to `ContractTemplateService` + implementation
- configuration documentation
- test for the update repeatable and viewable

### Changed
- validation for CreateContractTemplateRequest  
## [0.1.8] - 2021-4-28
### Added
- `SchedulingSwitch` configuration
- `SchedulingSwitch` to `ContractCleanUpService`
- tests for pinned suggestions

### Changed
- `buildPageableForSuggestions` implementation and moved it to `SuggestionServiceImpl`

### Removed
- `buildPageableForSuggestions` from `PaginationUtil`

## [0.1.7] - 2021-4-27
### Added
- Validation `NotNull` on City and Province + tests
- Validation `NotNull` on price only for *Commodity* and *Vehicle* contract templates

### Changed
- naming of `update()` method to `updateFavoriteStatus()` in *ContractTemplateService*

## [0.1.6] - 2021-4-26
### Added
- `DataInitializer` component and geo data initialization implementation
- `GeoInfoController` to expose the list of available provinces and cities
- `City` and `Province` model, repository and required *QueryMethods*
- `Config` model and repository for *configuration infrastructure*
- `GeographicInformationService` and its implementation
- Static resources (JSON) for cities and provinces data
- junit tests for geo info

## [0.1.5] - 2021-4-25
### Added
- *Deals filter* integration test
### Changed
- resolved minor bug in `ContractServiceImpl` that affected pagination
- `getAll` in `DealController` to support filters

## [0.1.4] - 2021-04-25
### Added
- new method in `ContractService` to filter *deals history*
- implementation for filtering *deals history*
- test for filtering *deals history* in ContractService
- `DealsHistoryTestUtil` utility class for testing deals
 
### Changed
- `RecentDealsModel` to support mapping from `DealHistory` internally and added toString()
- `DealHistoryRepository` to add *findByActionStateInAndSellerIdOrBuyerId*
- `ContractStateUtil` and externalized messages to properties files

## [0.1.3] - 2021-04-21
### Added
- `getAvailableFilters` endpoint to DealController
- unit test for new endpoint

## [0.1.3] - 2021-04-19
###  Added
- `UserScore` model
- `ScoringCommunicatorClient` feign client implementation
- `ScoringCommiunicatorMocks` for test
- scoring communicator client to `SuggestionServiceImpl`
- `UserScoreHelper` to _ContractWireMockConfig_ 
### Changed
- Tests to support scoring
- `application-test.yml` to make _scoring-communicator_ client available to ribbon for handling feign calls in test
- __Database url__ for _local_ and _dev_ profiles, application will store data in **contract** schema instead of **public**

## [0.1.2] - 2021-04-12
### Added
- Spring scheduling configuration
- `ContractCleanUpService` for managing stale contracts
- *schema.sql* file for initializing H2 db for shedlock in the test environment
- Shedlock and Awaitility dependencies to *pom.xml*
- Tests for Contract Cleanup Service
- Config properties for clean up service
### Changed
- `Contract` model to have *ManyToOne* relationship with `ContractTemplate`
- added *STALE* to `ContractState` enum
- `CurrentContractRepository` to support finding stale contracts. 

## [0.1.1] - 2021-04-05
### Changed
- `FileAddressRepository` to extend `JpaRepository` instead of `CrudRepositiry`
- `ContractServiceImpl` to use *saveAndFlush()* instead of *save()* and then *flush()*
- `RepeatableTemplateContactTest` to reduce duplicated code and improve readability
- *WireMock* services to support dynamic response template
- `ContractTestUtil` to make it compatible with new file upload and test scenario 

## [0.1.0] - 2021-03-16
### Added
- CHANGELOG.md
- local profile configuration
- Dockerfile
- required storage directories for resources in docker
- model mapper bean to application context
- model mapper configuration to convert `List<Long>` of file IDs to `List<FileAddress>` objects in `TemplateContractController`
- `repeatable` property to `ContractTemplate` model and `ContractTemplateMapper`
- dependencies for *WireMock* and *testcontainers* to pom.xml
- Postgres datasource configuration
- `vsq-logo.png` to static resources
- test configuration `application-test.yml` to test resources
- `ContractBasicTest` class to test creation of template contract and calling fegin clients
- `ContractTestUtil` to update current user in test environment
- configuration WireMock server for test environment
- json response files to test resources
- mock services for `BookkeepingService` and `ProfileService`
### Changed
- dev profile configuration
- `ContractTemplate` repository to support new repeatable model
- Cleaned up `ContractTemplateService` interface method signature and used `ContractTemplate` model
- `ContractTemplateServiceImpl`, `ContractServiceImpl` and `SuggestionServiceImpl` to support new repeatable logic
- Converted the configurations from properties to yml file
- renamed and relocated feign clients to `com.vosouq.contract.service.feign` package
- `Address` enum to point to new resource directories in docker
- eureka url in dev config to point to 192.168.88.2
- default server port for local config

### Removed
- unused imports from some classes
- `application-dev.properties`
- `application.properties`