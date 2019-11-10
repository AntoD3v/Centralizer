package com.darthvader.support;

import redis.clients.jedis.Jedis;

import java.util.HashMap;

public class RedisConnection {

    public HashMap<Integer, Jedis> jedisConnections = new HashMap<>();

    public Jedis getJedis(int i){
        if(!jedisConnections.containsKey(i))
            jedisConnections.put(i, new Jedis("antozzz.fr"));
        return jedisConnections.get(i);
    }

}
