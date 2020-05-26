package net.violantic.api.player;

import net.violantic.api.data.DAO;
import net.violantic.api.data.SQLManager;
import net.violantic.api.data.exception.ExistingRecordException;
import net.violantic.api.data.exception.NonExistentRecordException;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NetworkPlayerDAO implements DAO<NetworkPlayer> {

    private SQLManager sql;
    private final String RETRIEVE_PLAYER      = "SELECT * FROM `network_player` WHERE {field}={fieldValue};";
    private final String RETRIEVE_ALL_PLAYERS = "SELECT * FROM `network_player`;";
    private final String INSERT_PLAYER        = "INSERT INTO `network_player` VALUES {values};";
    private final String UPDATE_PLAYER        = "UPDATE `network_player` SET {field}={fieldValue} WHERE id=?";
    private final List<String> COLUMNS = new ArrayList<String>();

    public NetworkPlayerDAO(SQLManager sql) {
        this.sql = sql;
        for(Field field : NetworkPlayer.class.getFields()) {
            COLUMNS.add(field.getName());
        }
    }

    public List<String> getFields() {
        return COLUMNS;
    }

    public List<NetworkPlayer> findAll()
            throws SQLException {

        List<NetworkPlayer> list = new ArrayList<>();
        ResultSet set = sql.prepareStatement(RETRIEVE_ALL_PLAYERS).executeQuery();
        while(set.next()) {
            list.add(new NetworkPlayer(set));
        }

        return list;
    }

    public List<NetworkPlayer> findByField(String field, Object fieldValue)
            throws SQLException {

        List<NetworkPlayer> list = new ArrayList<>();
        ResultSet set = sql.prepareStatement(RETRIEVE_PLAYER.replace("{field}", field).replace("{fieldValue}", fieldValue.toString())).executeQuery();
        while(set.next()) {
            list.add(new NetworkPlayer(set));
        }

        return list;
    }

    public boolean exists(String field, Object fieldValue)
            throws SQLException {
        ResultSet fetch = sql.prepareStatement(RETRIEVE_PLAYER
                .replace("{field}", field)
                .replace("{fieldValue}", fieldValue.toString()))
                .executeQuery();

        return fetch.next();
    }

    public NetworkPlayer query(String field, Object fieldValue)
            throws SQLException {

        PreparedStatement statement = sql.prepareStatement(RETRIEVE_PLAYER.replace("{field}", field).replace("{fieldValue}", fieldValue.toString()));
        ResultSet set = statement.executeQuery();
        return set.next() ? new NetworkPlayer(set) : null;
    }

    /**
     * Insert new NetworkPlayer object into database
     * @param networkPlayer NetworkPlayer
     * @return Inserted NetworkPlayer
     * @throws SQLException
     * @throws ExistingRecordException
     */
    public NetworkPlayer insert(NetworkPlayer networkPlayer)
            throws SQLException, ExistingRecordException {

        if(exists("uuid", networkPlayer.uuid.toString())) {
            throw new ExistingRecordException("Data already exists for '" + networkPlayer.uuid.toString() + "'" +
                    " on Violantic Network");
        }

        String values = "(NULL,?,?,?,?,?)";

        PreparedStatement statement = sql.prepareStatement(INSERT_PLAYER.replace("{values}", values));
        statement.setString(0, networkPlayer.getUuid().toString());
        statement.setString(1, networkPlayer.getRank());
        statement.setString(2, networkPlayer.getTag());
        statement.setInt(3, networkPlayer.getXp());
        statement.setInt(4, networkPlayer.getGold());
        statement.executeUpdate();

        return query("uuid", networkPlayer.getUuid().toString());
    }

    /**
     * Update info for specific NetworkPlayer ojbect.
     *
     * @param networkPlayer Network Player
     * @param field Field being updated
     * @param value Value being used
     * @return Successful
     * @throws SQLException
     * @throws NonExistentRecordException
     */
    @Override
    public void update(NetworkPlayer networkPlayer, String field, Object value)
            throws SQLException, NonExistentRecordException {

        if(!exists("id", networkPlayer.id + "")) {
            throw new NonExistentRecordException("There is no record existing for '" + networkPlayer.uuid + "'");
        }

        PreparedStatement statement = sql.prepareStatement(UPDATE_PLAYER.replace("{field}", field).replace("{fieldValue}", value.toString()));
        statement.setInt(0, networkPlayer.getId());
        statement.executeUpdate();
    }

    public boolean delete(NetworkPlayer networkPlayer) {
        return false;
    }
}
