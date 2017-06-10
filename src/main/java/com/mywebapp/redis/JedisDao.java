package com.mywebapp.redis;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

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


    public static JSONArray getAllProxyIp() {

        Jedis jedis = RedisStorage.getInstance();

        Set<String> list = new HashSet<String>();
        List<String> list_show = new ArrayList<>();
        List<String> list_view = new ArrayList<>();

        list = jedis.smembers("httpProxy");
        Iterator iterator = list.iterator();

        JSONArray array = new JSONArray();
        int i = 0;
        while(iterator.hasNext()){

            i++;
            JSONObject json = new JSONObject();
            String ProxyIp = iterator.next().toString();
            String ip = ProxyIp.split("/")[1].split(":")[0];
            String port = ProxyIp.split("/")[1].split(":")[1];
            json.put("code",i);
            json.put("ip",ip);
            json.put("port",port);
            json.put("delete","<div id="+i+" style=display:none>"+ProxyIp+"</div><button type='button' onclick=deleteProxyIp("+i+") class='btn-primary'>删除</button>");
            array.add(json);
        }
        return array;
    }

    public long getLength() {

        Jedis jedis = RedisStorage.getInstance();
        long length = jedis.scard("httpProxy");//返回集合的元素个数

        return length;
    }

    public static Long  deleteProxyIp(String proxy) {

        Jedis jedis = RedisStorage.getInstance();
        Long length = jedis.srem("httpProxy",proxy);//返回集合的元素个数

        return length;
    }
    @org.junit.Test
    public  void Test(){
        SortingParams sortingParameters =  new  SortingParams();
        sortingParameters.desc();
        Jedis jedis = RedisStorage.getInstance();

        // 修饰符(modifier)进行排序。
        sortingParameters.limit( 0 ,  2 ); // 可用于分页查询
        List<String> list = jedis.sort( "httpProxy", sortingParameters); // 默认是升序
        for  ( int  i =  0 ; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
