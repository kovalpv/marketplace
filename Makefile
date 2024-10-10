# MAKEFLAGS += -j3

BASE_DIR := $(CURDIR)
LOGS_DIR := $(BASE_DIR)/logs

CONFIG_SERVICE_NAME=config-server
GATEWAY_NAME=gateway-api
MARKET_NAME=market
INVENTORY_NAME=inventory

CONFIG_SERVICE_PORT=8888

# Start all services
.PHONY: all start_config start_gateway start_market start_inventory stop_services wait_for_config

all: create_dirs start_config start_gateway start_market start_inventory

create_dirs:
	@echo "Creating logs directory if it doesn't exist..."
	mkdir -p $(LOGS_DIR)

# Start the Config Service
start_config:
	@echo "Starting Config Service..."
	@cd $(CONFIG_SERVICE_NAME) && mvn spring-boot:run > $(LOGS_DIR)/config-service.log 2>&1 & echo $$!

# Wait for Config Service to be up
wait_for_config:
	@echo "Waiting for Config Service to start..."
	@while ! nc -z localhost $(CONFIG_SERVICE_PORT); do sleep 1; done
	@echo "Config Service is up!"

# Start the Gateway Service
start_gateway: wait_for_config
	@echo "Starting Gateway Service..."
	@cd $(GATEWAY_NAME) && mvn spring-boot:run > $(LOGS_DIR)/gateway.log 2>&1 & echo $$!

# Start the Market Service
start_market: wait_for_config
	@echo "Starting Market Service..."
	@cd $(MARKET_NAME) && mvn spring-boot:run > $(LOGS_DIR)/market.log 2>&1 & echo $$!

# Start the Inventory Service
start_inventory: wait_for_config
	@echo "Starting Inventory Service..."
	@cd $(INVENTORY_NAME) && mvn spring-boot:run > $(LOGS_DIR)/inventory.log 2>&1 & echo $$!

# Stop all services based on their "spring-boot:run"
stop_services:
	@kill $(ps aux | grep 'spring-boot:run' | grep -v grep | awk '{print $$2}')
#kill $(ps aux | grep "spring-boot:run" | awk '{print $1}')
#	ps  | grep 'spring-boot:run' | grep -v grep | awk '{print $2}'



