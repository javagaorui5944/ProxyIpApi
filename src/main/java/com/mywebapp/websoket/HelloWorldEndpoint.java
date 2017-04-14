package com.mywebapp.websoket;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;




@ServerEndpoint("/hello")
@Component
public class HelloWorldEndpoint {

    static Session session1 = null;
    static HashMap<String, Session> Sessions = new HashMap<String, Session>();
    static boolean b;
    @OnMessage
    public static void hello(String message, Session session) {

        //System.out.println("hello"+session.getId());

        Session openSession = null;
        try {

            // 把消息发送给所有连接的会话
            Set<String> keys = Sessions.keySet();
            Iterator<String> temp = keys.iterator();
            while (temp.hasNext()) {
                String i = temp.next();

                //	for( int i= 0 ;i<Sessions.size();i++){
                openSession = Sessions.get(i);

                System.out.println("size" + Sessions.size());
                System.out.println("openSession.getId():" + openSession.getId()+":"+message);
                            /*System.out
									.println(session1.getOpenSessions()
											+ "session.getOpenSessions()"

														+ openSession.getId());*/
                openSession.getBasicRemote()
                        .sendText(message);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @OnOpen
    public void myOnOpen(Session session) {

        Sessions.put(session.getId(), session);
        System.out.println("WebSocket opened: " + session.getId());

        if(!b)
        {
            RedisPub.RedisPub();
        }
        //b = true;
        //hello("x", session, null);
        for (Session openSession : session.getOpenSessions()) {

            System.out.println("myOnOpen——openSession.getId():" + openSession.getId());
			

        }
    }

    @OnClose
    public void myOnClose(CloseReason reason, Session session) {
        Sessions.remove(session.getId());
        System.out.println("Closing a WebSocket due to "
                + session.getId());


    }

}