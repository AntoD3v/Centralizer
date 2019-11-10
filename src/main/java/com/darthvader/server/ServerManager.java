package com.darthvader.server;

import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {

    private ConcurrentHashMap<String, Server> servers = new ConcurrentHashMap<>();

    public void addServer(Server server){
        servers.put(server.getName(), server);
    }

    public void removeServer(String name){
        servers.remove(name);
    }

    public void getServer(String name){
        servers.get(name);
    }

    public ConcurrentHashMap<String, Server> getServers() {
        return servers;
    }

    public Server[] getCollectionType(ServerType type){
        return (Server[]) servers.values().stream().filter(a -> {
            return a.getServerType() == type;
        }).toArray();
    }
}
