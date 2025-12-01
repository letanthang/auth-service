# Auth Service

## Installation
- You need to install docker engine first : recommending orbstack
- You need to start user-service first
- You need to know to use docker
- You need to have a mysql db (docker-compose is recommended)
- You will need make tool (please install it by brew)
- Please update docker-compose.yaml with your database info, url, name, port, ...
- Maven sync to fix ide : right click on project -> Maven -> Sync Project

1. Build docker multi arch  
   make build/multi
2. Run:
   make run
3. Run by docker
   make up

## Test and Deploy

