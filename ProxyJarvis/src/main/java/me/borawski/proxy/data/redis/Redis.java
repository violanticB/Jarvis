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

    /**
     * Connects to redis server via Jedis
     * @param host Host string
     * @param port port
     */
    public Redis(String host, int port) {
        this.host = host;
        this.port = port;
        this.jedis = new Jedis(host, port);
        this.pub = new RedisPub(this);
        this.sub = new RedisSub(this);
    }

    /**
     * Jedis Connection
     * @return jedis
     */
    public Jedis getJedis() {
        return this.jedis;
    }

    /**
     * Connection host
     * @return Host string
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Connection port
     * @return
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Redis publisher
     * @return pub
     */
    public RedisPub getPub() {
        return pub;
    }

    /**
     * Redis subscriber
     * @return sub
     */
    public RedisSub getSub() {
        return sub;
    }
}
