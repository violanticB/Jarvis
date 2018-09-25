package controller.data.redis.pubsub;

import controller.Jarvis;
import controller.data.redis.Redis;
import redis.clients.jedis.JedisPubSub;

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
        if(message.startsWith("instance create")) {
            String[] msg = message.split(" ");

            if(msg.length == 3) {
                String instanceType = msg[2];

                if(Jarvis.getInstance().getInstanceManager().isType(instanceType)) {
                    Jarvis.getInstance().getInstanceManager().createInstance(instanceType);

                } else {
                    Jarvis.log("Could not start instance type '" + instanceType + "'");

                }
            }
        }

        else if(message.equalsIgnoreCase("network restart")) {

        }

        else if(message.equalsIgnoreCase("network stop")) {

        }
    }
}
