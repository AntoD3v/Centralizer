package com.darthvader;

import com.darthvader.queues.QueueGame;
import com.darthvader.server.ServerManager;
import com.darthvader.server.ServerType;
import com.darthvader.subscribers.GroupChannel;
import com.darthvader.subscribers.ServerChannel;
import com.darthvader.support.Logs;
import com.darthvader.support.RedisConnection;

import java.util.concurrent.ConcurrentHashMap;

public class Centralizer extends RedisConnection {

    private final ServerManager serverManager;
    private ConcurrentHashMap<ServerType, QueueGame> queues = new ConcurrentHashMap<>();

    public Centralizer() {
        serverManager = new ServerManager();
        start();
    }

    private void start(){
        for (ServerType serverType : ServerType.values())
            queues.put(serverType, new QueueGame(serverManager, serverType));
        getJedis(0).subscribe(new GroupChannel(this), "queues_add");
        getJedis(1).subscribe(new ServerChannel(serverManager), "server_add");
    }

    public static void main(String[] args){
        Logs.info(" * Centralizer/Queue");
        new Centralizer();
    }

    public ConcurrentHashMap<ServerType, QueueGame> getQueues() {
        return queues;
    }
}
