package com.darthvader.queues;

import com.darthvader.queues.Group;
import com.darthvader.server.Server;
import com.darthvader.server.ServerManager;
import com.darthvader.server.ServerType;
import com.darthvader.support.RedisConnection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueGame extends RedisConnection implements Runnable{

    private final ServerManager serverManager;
    private BlockingQueue<Group> blockingQueue = new ArrayBlockingQueue<Group>(1);
    private ServerType type;

    public QueueGame(ServerManager serverManager, ServerType type) {
        this.serverManager = serverManager;
        this.type = type;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (;;){
            Server[] servers = serverManager.getCollectionType(type);
            while(!blockingQueue.isEmpty()){
                try {
                    Group client = blockingQueue.take();
                    for (Server server : servers){
                        if(server.getMaxPlayers() - (client.getClients().size() + server.getOnlinePlayers()) >= 0){
                            server.addOnlinePlayers(client.getClients().size());
                            getJedis(0).publish("queues_return", client.toJson(server).toString());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public BlockingQueue<Group> getBlockingQueue() {
        return blockingQueue;
    }

    public void addGroupQueue(Group group){
        blockingQueue.add(group);
    }
}
