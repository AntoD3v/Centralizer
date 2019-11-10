package com.darthvader.server;

import com.darthvader.support.Logs;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Server {

    public String name;
    public ServerType serverType;
    public ServerStatus serverStatus;
    public String ip;
    public int port;
    public int maxPlayers;
    public int onlinePlayers;

    public Server(String name, ServerType serverType, ServerStatus serverStatus, String ip, int port, int maxPlayers, int onlinePlayers) {
        this.name = name;
        this.serverType = serverType;
        this.serverStatus = serverStatus;
        this.ip = ip;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public Server(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        if(jsonObject.has("name")
                && jsonObject.has("type")
                && jsonObject.has("status")
                && jsonObject.has("ip")
                && jsonObject.has("port")
                && jsonObject.has("max-players")
                && jsonObject.has("starting-at")){
            this.name = jsonObject.get("name").getAsString();
            this.serverType = ServerType.valueOf(jsonObject.get("type").getAsString());
            this.serverStatus = ServerStatus.valueOf(jsonObject.get("status").getAsString());
            this.ip = jsonObject.get("ip").getAsString();
            this.port = jsonObject.get("port").getAsInt();
            this.maxPlayers = jsonObject.get("max-players").getAsInt();
            this.onlinePlayers = jsonObject.get("online-players").getAsInt();
        }else
            Logs.warn("Parsing server invalid");
    }

    public String getName() {
        return name;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void addOnlinePlayers(int i){
        onlinePlayers += i;
    }

    public JsonObject toJson(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", getName());
        jsonObject.addProperty("type", getServerType().name());
        jsonObject.addProperty("status", getServerStatus().name());
        jsonObject.addProperty("ip", ip);
        jsonObject.addProperty("port", getPort());
        jsonObject.addProperty("max-players", getMaxPlayers());
        jsonObject.addProperty("online-players", onlinePlayers);
        return jsonObject;

    }
}
