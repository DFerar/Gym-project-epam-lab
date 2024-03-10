# Gym-project-epam-lab
Project, which is developing with task providing by EPAM Lab

Running the Gym CRM system in IntelliJ IDEA:
1. Clone the repository
2. Open project in IntelliJ
3. Navigate to project/docker and run docker compose up in your terminal
4. Build the project (In gradle tool window choose "build")
5. Go to resources/db.migration and launch the migration, then refresh database
6. Run the application (open GymCRMApp class, choose "Run")
7. Basic app commands are providing creating, updating and deleting entities in one launch, if you want to watch on difference comment out some blocks, or add some commands
8. To simplify logging in, db.migrations providing creation of admin user with id 0, you can use any users credentials, but need to know password of user