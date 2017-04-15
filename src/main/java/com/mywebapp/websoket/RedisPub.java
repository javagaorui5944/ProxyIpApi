package com.mywebapp.websoket;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import javax.websocket.Session;
import java.lang.reflect.Method;

/**
 * Created by gaorui on 17/4/14.
 */
public class RedisPub {




        public static void  RedisPub(){
            HelloWorldEndpoint.b = true;
            JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);

            Jedis jedis = pool.getResource();
            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onUnsubscribe(String channel, int number) {
                    System.out.println("channel: "+channel);
                    System.out.println("number :"+number);
                }
                @Override
                public void onSubscribe(String channel, int number) {
                    System.out.println("channel: "+channel);
                    System.out.println("number :"+number);
                }
                @Override
                public void onPUnsubscribe(String arg0, int arg1) {
                }
                @Override
                public void onPSubscribe(String arg0, int arg1) {
                }
                @Override
                public void onPMessage(String arg0, String arg1, String arg2) {
                }
                @Override
                public void onMessage(String channel, String msg) {
                    System.out.println("收到频道 : 【" + channel +" 】的消息 ：" + msg);
                    try {
                        Thread.sleep(1000 * 5);
                        HelloWorldEndpoint.hello(msg,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            jedis.subscribe(jedisPubSub, new String[]{"channel1"});
            pool.returnResource(jedis);
            //Session s = new S
            //new HelloWorldEndpoint().hello("ssss",null);


        }


}
