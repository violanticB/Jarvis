package me.borawski.proxy.data.redis.pubsub;

import me.borawski.proxy.ProxyJarvis;
import me.borawski.proxy.data.redis.Redis;
import redis.clients.jedis.JedisPubSub;

import java.net.InetSocketAddress;

/**
 * Created by Ethan on 9/25/2018.
 */
public class RedisSub {

    private Redis redis;

    public RedisSub(Redis redis) {
        this.redis = redis;
    }

    public Redis getRedis() {
        return redis;
    }

    private void listen(String channel) {
        getRedis().getJedis().subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                super.onMessage(channel, message);
                handle(message);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                super.onPMessage(pattern, channel, message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                super.onSubscribe(channel, subscribedChannels);
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                super.onUnsubscribe(channel, subscribedChannels);
            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {
                super.onPUnsubscribe(pattern, subscribedChannels);
            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                super.onPSubscribe(pattern, subscribedChannels);
            }
        }, channel);
    }

    private void handle(String message) {
        if(message.startsWith("instance setup")) {
            String[] msg = message.split(" ");

            if(msg.length == 5) {
                String instanceType = msg[2];
                int id = Integer.valueOf(msg[3]);
                int port = Integer.valueOf(msg[4]);

                ProxyJarvis.getInstance().addServer(instanceType.toUpperCase() + "-" + id, new InetSocketAddress("localhost", port));
                System.out.println("Added server '" + instanceType.toUpperCase() + "-" + id + "' to the proxy's config");
            }
        }

        else if(message.equalsIgnoreCase("network restart")) {

        }

        else if(message.equalsIgnoreCase("network stop")) {

        }
    }
}
