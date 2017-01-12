package com.mywebapp.redis;


import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by gaorui on 17/1/9.
 */
public class JedisDao {


    public static List<String> getProxyIp() {

        Jedis jedis = RedisStorage.getInstance();
        Set<String> set = jedis.keys("*");
        List<String> list = new ArrayList<String>();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String proxyIp = iterator.next().toString().substring(8);

//            map.put(proxyIp, proxyPort);
            list.add(proxyIp);

        }
        return list;
    }

    public int get(){

        return 1;
    }
}
