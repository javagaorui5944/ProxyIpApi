package com.mywebapp.timer;

import com.mywebapp.apicontroller.UserController;
import com.mywebapp.model.ProxyIp;
import com.mywebapp.redis.JedisDao;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gaorui on 17/5/7.
 */


public class MyTimer {


    public void Start() {
        // TODO todo.generated by zoer

        Timer timer = new Timer();
        timer.schedule(new MyTask(), 0, 1000 * 60 * 30);


    }
}

class MyTask extends TimerTask {

    @Override
    public void run() {

        UserController.proxyIp.setList(JedisDao.getProxyIp(10));
    }

}