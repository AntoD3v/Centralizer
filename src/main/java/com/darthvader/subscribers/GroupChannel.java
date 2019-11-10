package com.darthvader.subscribers;

import com.darthvader.Centralizer;
import com.darthvader.queues.Group;
import redis.clients.jedis.JedisPubSub;

public class GroupChannel extends JedisPubSub {

    private final Centralizer centralizer;

    public GroupChannel(Centralizer centralizer) {
        this.centralizer = centralizer;
    }

    @Override
    public void onMessage(String channel, String message) {
        Group group = new Group(message);
        centralizer.getQueues().get(group.getType()).addGroupQueue(group);
    }
}
