# Gym-project-epam-lab

<!-- TOC -->
* [Gym-project-epam-lab](#gym-project-epam-lab)
  * [How to run](#how-to-run)
<!-- TOC -->

Project, which is developing with task providing by EPAM Lab

## How to run

Running the Gym CRM system in IntelliJ IDEA:

1. Clone the repository
2. Open project in IntelliJ
3. Build the project (In gradle tool window choose "build")
4. Navigate to project/docker and run docker compose up in your terminal
   (if you already have volume, that presented in docker-compose, please,
    delete it for init.sql initialization and all databases creation)
5. Run GymCrmApp.
6. All endpoints might be tested from test.http
7. To simplify logging in, db.migrations providing creation of admin user with id 0,
   you can use any users credentials, but need to know password of user

