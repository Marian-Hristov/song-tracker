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

# Requirements

- Oracle Database 19C
- Java 16

# Initial Setup

1. Enter your database credentials in the <code>src/java/dawson.songtracker/utils/Credentials.java</code>
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
=======
# Assumptions

#### Recordings
- A recording's creation time cannot be modified

#### Compilations
- A compilation's creation time cannot be modified
- A compilation's duration cannot be directly modified. It is dependent on the segment that it uses

#### Collections
- Once a collection is distributed, it can no longer be deleted.
