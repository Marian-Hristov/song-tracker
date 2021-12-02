package dawson.songtracker;

import dawson.songtracker.types.Components.Recording;
import dawson.songtracker.types.Roles.CompilationRole;
import dawson.songtracker.types.Roles.*;

import java.sql.*;

public class ObjectUploader{
    private final Connection connection;

    public ObjectUploader(Connection connection) throws SQLException {
        this.connection = connection;
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
            PreparedStatement deleteRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.REMOVEROLE(?, ?)");
            deleteRole.setString(1, Character.toString(category));
            deleteRole.setString(2, name);

            if(deleteRole.executeUpdate() != 1){
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
            PreparedStatement updateRole = this.connection.prepareStatement("EXECUTE ROLE_MGMT.UPDATEROLE(?, ?, ?)");
            updateRole.setString(1, Character.toString(category));
            updateRole.setString(2, oldName);
            updateRole.setString(3, newName);

            if(updateRole.executeUpdate() != 1){
                throw new SQLException("Could update role");
            }

            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    public void addContributor(String name) throws SQLException{
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given name is empty or null");
        }
        try{
            PreparedStatement insertContributor = this.connection.prepareStatement("EXECUTE CONTRIBUTOR_MGMT.ADDCONTRIBUTOR(?)");
            insertContributor.setString(1, name);

            if(insertContributor.executeUpdate() != 1){
                throw new SQLException("Could not add contributor");
            }

            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    public void deleteContributor(String name) throws SQLException{
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given name is empty or null");
        }
        try{
            PreparedStatement deleteContributor = this.connection.prepareStatement("EXECUTE CONTRIBUTOR_MGMT.REMOVECONTRIBUTOR(?)");
            deleteContributor.setString(1, name);

            if(deleteContributor.executeUpdate() != 1){
                throw new SQLException("Could not delete contributor");
            }

            this.connection.commit();
        } catch (Exception e) {
            this.connection.rollback();
            e.printStackTrace();
        }
    }

    public void updateContributor(String oldName, String newName) throws SQLException{

    }
}