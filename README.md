Automated tests for the Yandex Scooter educational service.

## Quick Start for Reviewer

### Prerequisites
- Java 11
- Maven
- Chrome/Firefox browsers

### Run Tests
```bash
# Clone repository
git clone https://github.com/kotprokaza/Sprint_4_NEW.git
cd Sprint_4_NEW

# Switch to develop branch
git checkout develop

# Run all tests
mvn test

# Run specific test groups
mvn test -Dtest=FaqTest
mvn test -Dtest=OrderTest
mvn test -Dtest=SimpleFaqTest
mvn test -Dtest=SimpleOrderTest
mvn test -Dtest=MetroTest