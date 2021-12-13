# Our song tracker

Important: They are predefined production roles and musicians roles. When the user wants to add a new role in either categories,
the user must be vigilant that they are adding the new role in the appropriate category. Our application does not check
if the user adds the role to the appropriate category.

Ex: Let's say that the guitarist role isn't predefined. When the user wants to add it as a new role, they have to check
that they're adding it to the musician category instead of the production category. The application will not check if
the new guitarist role is in the musician category instead of the production category.

# Limitations

- For each type, there can only be 99999 different instance in total for each type
- Example: Recordings are a type. Only 99999 recordings can be created in total during the lifetime of the database
- A song can only be 9999.9 seconds long
- A recording can only my 9999.9 seconds long
- For the uploader tests, you need to reset the database for each test
- For the downloader tests, you need to have the 3 sample songs on the database
- The GUI needs a few moments to load everything from the database

# Requirements

- Oracle Database 19C
- Java 16
- Intellij Idea Community Edition 2021.2.3

#Install Process

1. Clone the git repository from https://gitlab.com/marian.hristov/song-tracker into your local machine
2. Open it using a preferred code editor (for this example it's vs code)
3. Open the folder in vscode
4. Make sure you have MicroSoft's "Extension pack for java" installed in your vs code extensions
5. CTR + SHIFT + P => Create Java project and select maven as a build tool
6. Select Maven as a build

# Initial Setup of Intellij Idea Project


- Open Get From VCS -> Repository URL, paste the url of the git repo and choose the folder in which you want to setup the project -> Clone
- If prompt click Trust Project 
- File -> Close Project -> Right click on project -> Remove from recent projects 
- New Project -> JavaFX -> Select folder location to be the folder that you created previously -> click yes to the warning -> finish 
- Delete src/main folder 
- Click Project on the left to open the file explorer -> open the first java folder in src -> right click on it -> mark directory as -> source root 
- Still in the file explorer -> right click on resources folder in src -> Mark directory as -> Resources root 
- Navigate to this file: src/java/dawson/songtracker/back/dbObjects/DBConnection.java and open it 
- Sent the username and password to your database credentials 
- Project Structure -> Click + in the middle panel => Java => Go into your project folder -> lib -> select all the jar files by shift clicking the first and last one -> Ok -> Ok -> Ok 
- Navigate to this file: src/java/dawson/songtracker/MVCApp.java 
- Run MVCApp.java with the green triangle on the right of the line

# Initial Setup of database

1. Enter your database credentials in the <code>src/java/dawson/songtracker/back/dbObjects/DBConnection.java</code> file it should look like this:
    
    <code>
    private static String username = "ThisIsMyUsername";
    <br>
    private static String password = "ThisIsMyPassword";
    </code>
2. Setup the database
    - Run install.sql onto your database server to install the database needed for the app
3. Compile the app
    - Open the folder of the project and run this command:  
    <code>compiling command</code>
4. Run the app with this command: 
    - Still in the folder of the project, run this command:  
    <code>running command</code>

# Uninstall process

1. Make sure the app is not running anymore
2. Delete the database
    - Run uninstall.sql onto your database to remove the database needed for the app
# Assumptions

#### Recordings
- A recording's creation time cannot be modified

#### Compilations
- A compilation's creation time cannot be modified
- A compilation's duration cannot be directly modified. It is dependent on the segment that it uses
- If a compilation is deleted and is also inside a collection, it will be removed from that collection

#### Collections
- Once a collection is distributed, it can no longer be deleted.

# Usage
Different panes are available to the user in order to modify the database. You can modify an item present in a table by double-clicking on it.
