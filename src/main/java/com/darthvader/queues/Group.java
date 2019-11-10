package com.darthvader.queues;

import com.darthvader.server.Server;
import com.darthvader.server.ServerType;
import com.darthvader.support.Logs;
import com.darthvader.support.exception.GroupParsingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<String> clients = new ArrayList<String>();
    private ServerType type;

    public Group(List<String> clients, ServerType type) {
        this.clients = clients;
        this.type = type;
    }

    public Group(String json){

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        if(jsonObject.has("type") && jsonObject.has("group")){
            if((type = ServerType.valueOf(jsonObject.get("type").getAsString())) != null){
                JsonArray jsonArray = jsonObject.getAsJsonArray("group");
                for (int i = 0; i < jsonArray.size(); i++)
                    clients.add(jsonArray.get(i).getAsString());
            }else
                Logs.warn("Parsing type invalid ("+json+")");
        }else
            Logs.warn("Parsing group invalid ("+json+")");

    }

    public List<String> getClients() {
        return clients;
    }

    public ServerType getType() {
        return type;
    }

    public JsonObject toJson(Server server){
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        clients.forEach(s -> {
            jsonArray.add(s);
        });
        jsonObject.addProperty("server", server.getName());
        jsonObject.add("group", jsonArray);
        return jsonObject;
    }
}
