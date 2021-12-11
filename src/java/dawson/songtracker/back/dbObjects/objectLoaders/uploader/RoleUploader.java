package dawson.songtracker.back.dbObjects.objectLoaders.uploader;

import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.Downloader;
import dawson.songtracker.back.dbObjects.objectLoaders.dowloader.objectDownloaders.ObjectDownloader;
import dawson.songtracker.back.types.distributions.Collection;
import dawson.songtracker.back.types.roles.CompilationRole;
import dawson.songtracker.back.types.roles.MusicianRole;
import dawson.songtracker.back.types.roles.ProductionRole;
import dawson.songtracker.back.types.roles.Role;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

class RoleUploader implements IDBUploader<Role> {
    private final Connection connection;
    private final Downloader dl;

    public RoleUploader(Connection connection) throws SQLException {
        this.connection = connection;
        this.dl = Downloader.getInstance();
    }

    private void addRole(char category, String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("One or many given arguments are null or empty");
        } else if (category == 'c' || category == 'm' || category == 'p') {
            CallableStatement insertRole = this.connection.prepareCall("{call ROLE_MGMT.ADDROLE(?, ?)}");
            try {
                insertRole.setString(1, Character.toString(category));
                insertRole.setString(2, name);
                if (insertRole.executeUpdate() != 1) {
                    throw new SQLException("Couldn't create role");
                }
                this.connection.commit();
                insertRole.close();
            } catch (Exception e) {
                this.connection.rollback();
                insertRole.close();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Given category doesn't exist");
        }
    }

    private void deleteRole(char category, String name) throws Exception {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Given id is invalid or null");
        } else if (category == 'c' || category == 'm' || category == 'p') {
            CallableStatement deleteRole = this.connection.prepareCall("{call ROLE_MGMT.REMOVEROLE(?, ?)}");
            try {
                deleteRole.setString(1, Character.toString(category));
                deleteRole.setString(2, name);
                if (deleteRole.executeUpdate() != 1) {
                    throw new SQLException("Could not remove role");
                }
                this.connection.commit();
                deleteRole.close();
            } catch (Exception e) {
                this.connection.rollback();
                deleteRole.close();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Given category doesn't exist");
        }
    }

    private void updateRole(char category, String oldName, String newName) throws Exception {
        if (oldName == null || newName == null || oldName.equals("") || newName.equals("")) {
            throw new IllegalArgumentException("One or more given names are invalid or null");
        } else if (category == 'c' || category == 'm' || category == 'p') {
            CallableStatement updateRole = this.connection.prepareCall("{call ROLE_MGMT.UPDATEROLE(?, ?, ?)}");
            try {
                updateRole.setString(1, Character.toString(category));
                updateRole.setString(2, oldName);
                updateRole.setString(3, newName);
                if (updateRole.executeUpdate() != 1) {
                    throw new SQLException("Could not update role");
                }
                this.connection.commit();
                updateRole.close();
            } catch (Exception e) {
                this.connection.rollback();
                updateRole.close();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Given category doesn't exist");
        }
    }

    @Override
    public void add(Role role) throws Exception {
        if(role == null){
            throw new Exception("Role is null");
        }
        if(role instanceof CompilationRole){
            this.addRole('c', role.getName());
        } else if (role instanceof ProductionRole){
            this.addRole('p', role.getName());
        } else if (role instanceof MusicianRole) {
            this.addRole('m', role.getName());
        } else {
            throw new Exception("Type of role not supported");
        }
    }

    @Override
    public void remove(Role role) throws Exception{
        if(role == null){
            throw new Exception("Role is null");
        }
        if(role instanceof CompilationRole){
            this.deleteRole('c', role.getName());
        } else if (role instanceof ProductionRole){
            this.deleteRole('p', role.getName());
        } else if (role instanceof MusicianRole) {
            this.deleteRole('m', role.getName());
        } else {
            throw new Exception("Type of role not supported");
        }
    }

    @Override
    public void update(Role newRole) throws Exception {
        if(newRole == null){
            throw new Exception("Role is null");
        }
        if(newRole instanceof CompilationRole){
            ObjectDownloader<CompilationRole> dl = (ObjectDownloader<CompilationRole>) Downloader.getInstance().getLoader(CompilationRole.class);
            CompilationRole oldRole = dl.load(newRole.getId());
            if(!oldRole.getName().equals(newRole.getName())) this.updateRole('c', oldRole.getName(), newRole.getName());
        } else if (newRole instanceof ProductionRole){
            ObjectDownloader<ProductionRole> dl = (ObjectDownloader<ProductionRole>) Downloader.getInstance().getLoader(ProductionRole.class);
            ProductionRole oldRole = dl.load(newRole.getId());
            if(!oldRole.getName().equals(newRole.getName())) this.updateRole('p', oldRole.getName(), newRole.getName());
        } else if (newRole instanceof MusicianRole) {
            ObjectDownloader<MusicianRole> dl = (ObjectDownloader<MusicianRole>) Downloader.getInstance().getLoader(MusicianRole.class);
            MusicianRole oldRole = dl.load(newRole.getId());
            if(!oldRole.getName().equals(newRole.getName())) this.updateRole('m', oldRole.getName(), newRole.getName());
        } else {
            throw new Exception("Type of role not supported");
        }
    }
}
