.PHONY: dev prod stop build logs db-shell app-shell migrate reset-db clean help

# ── Development ───────────────────────────────────────────────────────────────
dev: ## Start app in dev mode (with live reload)
	docker compose up db dev

prod: ## Build and start app in production mode
	docker compose up db app --build

stop: ## Stop all containers
	docker compose down

build: ## Build the uberjar image
	docker compose build app

# ── Logs ──────────────────────────────────────────────────────────────────────
logs: ## Tail all logs
	docker compose logs -f

logs-app: ## Tail app logs only
	docker compose logs -f dev

logs-db: ## Tail db logs only
	docker compose logs -f db

# ── Shells ────────────────────────────────────────────────────────────────────
db-shell: ## Open psql shell
	docker compose exec db psql -U tempus -d tempus_fugit

app-shell: ## Open bash in app container
	docker compose exec dev sh

repl: ## Start nREPL (connect on port 7002)
	docker compose run --rm dev lein repl

# ── Database ──────────────────────────────────────────────────────────────────
migrate: ## Run migrations
	docker compose run --rm dev lein run migrate

reset-db: ## Drop and recreate DB (WARNING: deletes all data)
	docker compose down -v
	docker compose up -d db
	sleep 3
	docker compose run --rm dev lein run migrate

# ── Cleanup ───────────────────────────────────────────────────────────────────
clean: ## Remove containers, volumes and build artifacts
	docker compose down -v
	rm -rf target .shadow-cljs node_modules

# ── Help ──────────────────────────────────────────────────────────────────────
help: ## Show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-15s\033[0m %s\n", $$1, $$2}'

.DEFAULT_GOAL := help