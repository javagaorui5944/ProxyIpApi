package com.mywebapp.redis;


import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by gaorui on 17/1/9.
 */
public class JedisDao {


    public static Set<String> getProxyIp(int size) {

        Jedis jedis = RedisStorage.getInstance();
        long length = jedis.scard("httpProxy");//返回集合的元素个数
        System.out.println("length:" + length);
        Set<String> list = new HashSet<String>();
        if (length >= size) {

            while (list.size() < size) {

                list.add(jedis.srandmember("httpProxy"));
            }

        } else {

            list = jedis.smembers("httpProxy");
        }
        return list;
    }


    public static Set<String> getAllProxyIp() {

        Jedis jedis = RedisStorage.getInstance();
        //long length = jedis.scard("httpProxy");//返回集合的元素个数

        Set<String> list = new HashSet<String>();

        list = jedis.smembers("httpProxy");

        return list;
    }

    public int get() {

        return 1;
    }
}
