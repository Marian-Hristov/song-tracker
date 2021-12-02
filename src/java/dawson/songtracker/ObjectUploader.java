package dawson.songtracker;

import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.*;

import java.sql.*;

public class ObjectUploader{
    private final Connection connection;
    private final ObjectDownloader downloader;

    public ObjectUploader(Connection connection){
        this.connection = connection;
        this.downloader = new ObjectDownloader(connection);
    }

//     if (user == null) {
//        throw new IllegalArgumentException("Cannot add user that is null");
//    }
//        try {
//        PreparedStatement insertUser = this.connection.prepareStatement("INSERT INTO JLUSERS VALUES (?, ?, ?, ?)");
//        insertUser.setString(1, user.getUser());
//        insertUser.setBytes(2, user.getSalt());
//        insertUser.setLong(3, user.getFailedLoginCount());
//        insertUser.setBytes(4, user.getHash());
//
//        if (insertUser.executeUpdate() != 1) {
//            throw new SQLException("Could not insert user");
//        }
//        System.out.println("User has been successfully created");
//        this.connection.commit();
//    } catch (SQLException e) {
//        this.connection.rollback();
//        System.out.println("An error occured, could not add user, please try again");
//        e.printStackTrace();
//    }

    // Production roles
    public void createRole(char category, String name) throws SQLException{
        if (name == null || name.equals("")){
            throw new IllegalArgumentException("One or many given arguments are null or empty");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }

        try{
            PreparedStatement insertRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.ADDROLE(?, ?)");
            insertRole.setString(1, Character.toString(category));
            insertRole.setString(2, name);

            if(insertRole.executeUpdate() != 1){
                throw new SQLException("Could insert role");
            }

            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    public void deleteRole(char category, String name) throws SQLException {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given id is invalid or null");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }

        try{
            PreparedStatement insertRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.REMOVEROLE(?, ?)");
            insertRole.setString(1, Character.toString(category));
            insertRole.setString(2, name);

            if(insertRole.executeUpdate() != 1){
                throw new SQLException("Could remove role");
            }

            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    public void updateRole(char category, String oldName, String newName) throws SQLException{
        if(oldName == null || newName == null || oldName.equals("") || newName.equals("")){
            throw new IllegalArgumentException("One or more given names are invalid or null");
        }
        try{
            PreparedStatement insertRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.REMOVEROLE(?, ?, ?)");
            insertRole.setString(1, Character.toString(category));
            insertRole.setString(2, oldName);
            insertRole.setString(3, newName);

            if(insertRole.executeUpdate() != 1){
                throw new SQLException("Could update role");
            }

            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            e.printStackTrace();
        }
    }
}