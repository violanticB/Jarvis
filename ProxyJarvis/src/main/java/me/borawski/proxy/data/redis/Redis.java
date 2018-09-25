package me.borawski.proxy.data.redis;

import me.borawski.proxy.data.redis.pubsub.RedisPub;
import me.borawski.proxy.data.redis.pubsub.RedisSub;
import redis.clients.jedis.Jedis;

/**
 * Created by Ethan on 9/25/2018.
 */
public class Redis {

    private Jedis jedis;
    private String host;
    private int port;

    private RedisPub pub;
    private RedisSub sub;

    /*
     * Methods
     */

    public Redis(String host, int port) {
        this.host = host;
        this.port = port;

        this.jedis = new Jedis(host, port);

        pub = new RedisPub(this);
        sub = new RedisSub(this);
    }

    /*
     * Getters
     */

    public Jedis getJedis() {
        return this.jedis;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public RedisPub getPub() {
        return pub;
    }

    public RedisSub getSub() {
        return sub;
    }
}
