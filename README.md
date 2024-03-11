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
3. Navigate to project/docker and run docker compose up in your terminal
4. Build the project (In gradle tool window choose "build")
5. Run the application (open GymCRMApp class, choose "Run")
6. Basic app commands are providing creating, updating and deleting entities in one launch,
   if you want to watch on difference comment out some blocks, or add some commands
7. To simplify logging in, db.migrations providing creation of admin user with id 0,
   you can use any users credentials, but need to know password of user