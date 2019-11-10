package com.darthvader.subscribers;

import com.darthvader.server.Server;
import com.darthvader.server.ServerManager;
import redis.clients.jedis.JedisPubSub;

public class ServerChannel extends JedisPubSub {

    private final ServerManager serverManager;

    public ServerChannel(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void onMessage(String channel, String message) {
        Server server = new Server(message);
        serverManager.addServer(server);
    }
}
