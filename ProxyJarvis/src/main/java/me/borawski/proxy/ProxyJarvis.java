package me.borawski.proxy;

import me.borawski.proxy.config.ConfigHelper;
import me.borawski.proxy.data.redis.Redis;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;

/**
 * Created by Ethan on 9/25/2018.
 */
public class ProxyJarvis extends Plugin {

    private static ProxyJarvis instance;

    private Redis redis;

    public void onEnable() {
        instance = this;

        try {
            redis = new Redis("localhost", 0000);
            redis.getSub().listen("bungee");
        } catch (Exception e) {
            System.out.println("Couldn't connect to the Redis server.");
        }
    }

    public static ProxyJarvis getInstance() {
        return instance;
    }

    public Redis getRedis() {
        return redis;
    }

    public void addServer(String name, InetSocketAddress address) {
        ServerInfo info = getProxy().constructServerInfo(name, address, "Individual instance on network: " + name, false);
        ConfigHelper.addToConfig(info);
        getProxy().getServers().put(name, info);
        System.out.println("Registered individual instance '" + name + "' @ " + address.getAddress().getHostAddress() + ":" + address.getPort());
    }

}
