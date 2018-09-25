package me.borawski.proxy.data.redis.pubsub;

import me.borawski.proxy.data.redis.Redis;

/**
 * Created by Ethan on 9/25/2018.
 */
public class RedisPub {

    private Redis redis;

    public RedisPub(Redis redis) {
        this.redis = redis;
    }

    public Redis getRedis() {
        return redis;
    }

    public void send(String channel, String message) {
        getRedis().getJedis().publish(channel, message);
    }
}
