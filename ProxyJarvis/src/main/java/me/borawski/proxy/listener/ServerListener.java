package me.borawski.proxy.listener;

import me.borawski.proxy.data.redis.Redis;

public class ServerListener {

    private Redis redis;

    public ServerListener(Redis redis) {
        this.redis = redis;
    }

    public void listen() {
        new Thread(() -> {
            redis.getSub().listen("bungee");
        }, "bungeeThread");
    }

}
