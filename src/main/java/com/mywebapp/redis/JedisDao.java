package com.mywebapp.redis;


import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Created by gaorui on 17/1/9.
 */
public class JedisDao {


    public static Set<String> getProxyIp() {

        Jedis jedis = RedisStorage.getInstance();
        long length = jedis.scard("httpProxy");//返回集合的元素个数
        System.out.println("length:"+length);
        Set<String> list = new HashSet<String>();
        if(length >= 10){

            while(list.size()<10){

                list.add(jedis.srandmember("httpProxy"));
            }

        }
        else{

            list = jedis.smembers("httpProxy");
        }
        //List<String> set = jedis.lrange("mykey",0,-1);

        /*Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String proxyIp = iterator.next().toString().substring(8);

//            map.put(proxyIp, proxyPort);
            list.add(proxyIp);

        }*/
        return list;
    }

    public static Set<String> getAllProxyIp() {

        Jedis jedis = RedisStorage.getInstance();
        //long length = jedis.scard("httpProxy");//返回集合的元素个数

        Set<String> list = new HashSet<String>();

        list = jedis.smembers("httpProxy");

        return list;
    }

    public int get(){

        return 1;
    }
}
