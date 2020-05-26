package controller.data.redis.communication;

import controller.data.redis.Redis;

/**
 * Created by Ethan on 9/25/2018.
 */
public class RedisPub {

    private Redis redis;

    /**
     * Redis publisher, allows you to publish messages
     * to specific channels.
     * @param redis Redis instance
     */
    public RedisPub(Redis redis) {
        this.redis = redis;
    }

    /**
     * Redis instance
     * @return reds
     */
    public Redis getRedis() {
        return redis;
    }

    /**
     * Send a message to any channel within the redis instance.
     * @param channel Channel
     * @param message Message
     */
    public void send(String channel, String message) {
        getRedis().getJedis().publish(channel, message);
    }
}
