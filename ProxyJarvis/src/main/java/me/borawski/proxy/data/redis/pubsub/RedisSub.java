package me.borawski.proxy.data.redis.pubsub;

import me.borawski.proxy.ProxyJarvis;
import me.borawski.proxy.data.redis.Redis;
import redis.clients.jedis.JedisPubSub;

import java.net.InetSocketAddress;

/**
 * Created by Ethan on 9/25/2018.
 */
public class RedisSub extends JedisPubSub {

    private Redis redis;

    public RedisSub(Redis redis) {
        this.redis = redis;
    }

    public Redis getRedis() {
        return redis;
    }

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
        handle(message);
    }

    public void listen(String channel) {
        this.redis.getJedis().subscribe(this, channel);
    }

    private void handle(String message) {
        System.out.println("Receiving message: " + message);
        if(message.startsWith("instance setup")) {
            String[] msg = message.split(" ");

            if(msg.length == 4) {
                String instanceType = msg[2];
                int port = Integer.parseInt(msg[3]);

                ProxyJarvis.getInstance().addServer(instanceType.toUpperCase(), new InetSocketAddress("localhost", port));
                System.out.println("Added server '" + instanceType.toUpperCase()  + "' to the proxy's config");
            }
        }

        else if(message.equalsIgnoreCase("network restart")) {

        }

        else if(message.equalsIgnoreCase("network stop")) {

        }

    }
}
