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
4. Navigate to build/libs directory, rename .war file to ROOT.war
5. Replace it to docker/webapps
6. Navigate to project/docker and run docker compose up in your terminal
7. All endpoints might be tested from test.http
8. To simplify logging in, db.migrations providing creation of admin user with id 0,
   you can use any users credentials, but need to know password of user

