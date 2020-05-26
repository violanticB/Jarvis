package me.borawski.proxy;

import me.borawski.proxy.config.ConfigHelper;
import me.borawski.proxy.data.redis.Redis;
import me.borawski.proxy.listener.ServerListener;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.violantic.api.data.SQLManager;

import java.net.InetSocketAddress;
import java.util.logging.Level;

public class ProxyJarvis extends Plugin {

    private static ProxyJarvis instance;
    private Redis redis;
    private SQLManager sqlManager;
    private ServerListener listener;

    /**
     * Runs on proxy start
     */
    public void onEnable() {
        instance = this;
        redis = new Redis("localhost", 6379);

        new Thread(() -> {
            redis.getSub().listen("bungee");
        }, "redisBungeeThread").start();
    }

    /**
     * Running instance of ProxyJarvis
     * @return instance
     */
    public static ProxyJarvis getInstance() {
        return instance;
    }

    /**
     * Redis data connector
     * @return redis
     */
    public Redis getRedis() {
        return redis;
    }

    /**
     * Add a server to network. Input details into the config.
     * @param name Server name
     * @param address Server address
     */
    public void addServer(String name, InetSocketAddress address) {
        ServerInfo info = getProxy().constructServerInfo(name, address, "Individual instance on network: " + name, false);
        ConfigHelper.addToConfig(info);

        getProxy().getServers().put(name, info);
        getProxy().getLogger().log(Level.INFO, "Registered individual instance '" + name + "' @ " + address.getAddress().getHostAddress() + ":" + address.getPort());
    }

}
