package dawson.songtracker.DBObjects.objectLoaders.uploader;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class RoleUploader {
    private final Connection connection;

    public RoleUploader(Connection connection) {
        this.connection = connection;
    }

    public void addRole(char category, String name) throws Exception{
        if (name == null || name.equals("")){
            throw new IllegalArgumentException("One or many given arguments are null or empty");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }

        try{
            CallableStatement insertRole = this.connection.prepareCall("{call ROLE_MGMT.ADDROLE(?, ?)}");
            insertRole.setString(1, Character.toString(category));
            insertRole.setString(2, name);

            if(insertRole.executeUpdate() != 1){
                throw new SQLException("Couldn't create role");
            }

            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't create role");
        }
    }

    public void deleteRole(char category, String name) throws Exception {
        if(name == null || name.equals("")){
            throw new IllegalArgumentException("Given id is invalid or null");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }
        try{
            CallableStatement deleteRole = this.connection.prepareCall("{call ROLE_MGMT.REMOVEROLE(?, ?)}");
            deleteRole.setString(1, Character.toString(category));
            deleteRole.setString(2, name);
            if(deleteRole.executeUpdate() != 1){
                throw new SQLException("Could not remove role");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't delete role");
        }
    }

    public void updateRole(char category, String oldName, String newName) throws Exception{
        if(oldName == null || newName == null || oldName.equals("") || newName.equals("")){
            throw new IllegalArgumentException("One or more given names are invalid or null");
        } else if (category != 'c' || category != 'm' || category != 'p'){
            throw new IllegalArgumentException("Given category doesn't exist");
        }
        try{
            CallableStatement updateRole = this.connection.prepareCall("{call ROLE_MGMT.UPDATEROLE(?, ?, ?)}");
            updateRole.setString(1, Character.toString(category));
            updateRole.setString(2, oldName);
            updateRole.setString(3, newName);
            if(updateRole.executeUpdate() != 1){
                throw new SQLException("Could not update role");
            }
            this.connection.commit();
        } catch (Exception e){
            this.connection.rollback();
            throw new Exception("Couldn't update role");
        }
    }
}
