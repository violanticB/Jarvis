package net.violantic.api;

import net.violantic.api.data.SQLManager;
import net.violantic.api.player.NetworkPlayerDAO;

public class NetworkAPI {

    private SQLManager sqlManager;
    private NetworkPlayerDAO networkPlayerDAO;

    public NetworkAPI(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
        this.networkPlayerDAO = new NetworkPlayerDAO(sqlManager);
    }

    public SQLManager getSqlManager() {
        return sqlManager;
    }

    public NetworkPlayerDAO getNetworkPlayerDAO() {
        return networkPlayerDAO;
    }
}
