def pytest_addoption(parser):
    parser.addoption("--drop-test-db")
    parser.addoption("--drop-test-collections")
    parser.addoption("--cache-rules")
    parser.addoption("--random-trades-count", default=1)
    # parser.addoption("--drop", action="store", default="default name")
