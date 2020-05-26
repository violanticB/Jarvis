package controller.data.redis;

import controller.data.redis.communication.RedisPub;
import controller.data.redis.communication.RedisSub;
import redis.clients.jedis.Jedis;

public class Redis {

    private Jedis jedis;
    private String host;
    private int port;

    private RedisPub pub;
    private RedisSub sub;

    /**
     * Object for handling redis communication pipelines
     * @param host Redis host
     * @param port port
     */
    public Redis(String host, int port) {
        this.host = host;
        this.port = port;

        this.jedis = new Jedis(host, port);

        pub = new RedisPub(this);
        sub = new RedisSub(this);
    }

    /**
     * Jedis instance
     * @return jedis
     */
    public Jedis getJedis() {
        return this.jedis;
    }

    /**
     * Redis host
     * @return host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Redis port
     * @return port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Redis publisher pipelines
     * @return publisher
     */
    public RedisPub getPub() {
        return pub;
    }

    /**
     * Redis subscriber pipeline
     * @return subscriber
     */
    public RedisSub getSub() {
        return sub;
    }
}
