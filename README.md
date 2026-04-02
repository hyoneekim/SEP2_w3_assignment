# Shopping Cart App

A JavaFX shopping cart application with multi-language support and MySQL database integration.

## Features
- Multi-language UI: English, Finnish, Swedish, Japanese, Arabic (RTL supported)
- Dynamic item entry with price & quantity inputs
- Total cost calculation with DB persistence
- Localization strings loaded from database

## Prerequisites
- Docker & Docker Compose
- XQuartz (Mac only, for GUI display)

## Setup

### 1. XQuartz (Mac only)
```bash
brew install --cask xquartz
open -a XQuartz
xhost +localhost
```
Log out and log back in after installing XQuartz.

### 2. Clone the repository
```bash
git clone https://github.com/hyoneekim/SEP2_w3_assignment.git
```

### 3. Run with Docker Compose
```bash
docker-compose up -d
```
This will start:
- `shopping-cart-db` — MariaDB with the schema and seed data loaded automatically from `init.sql`
- `shopping-cart-app` — The JavaFX application

### 4. Stop
```bash
docker-compose down
```

## Running Locally (without Docker)

### Requirements
- JDK 21
- Maven
- MySQL running on `localhost:3306`

### Steps
```bash
# Create the database
mysql -u root -p < init.sql

# Build and run
mvn clean javafx:run
```

## Database
| Table | Description |
|---|---|
| `cart_records` | Stores each cart session (total items, cost, language) |
| `cart_items` | Stores individual items per cart record |
| `localization_strings` | Stores UI translations for all languages |

## Project Structure
```
src/
├── main/
│   ├── java/org/example/shopping_cart/
│   │   ├── ShoppingCartApp.java
│   │   ├── ShoppingCartController.java
│   │   ├── ShoppingCartCalculator.java
│   │   ├── dao/
│   │   │   ├── DatabaseConnection.java
│   │   │   ├── CartRecordDAO.java
│   │   │   ├── CartItemDAO.java
│   │   │   └── LocalizationStringDAO.java
│   │   ├── model/
│   │   │   ├── CartRecord.java
│   │   │   └── CartItem.java
│   │   └── service/
│   │       └── LocalizationService.java
│   └── resources/
│       ├── shopping-cart-view.fxml
│       ├── style.css
│       └── i18n/
│           └── MessagesBundle_*.properties
└── test/
    └── java/org/example/shopping_cart/
        └── ShoppingCartCalculatorTest.java
```
