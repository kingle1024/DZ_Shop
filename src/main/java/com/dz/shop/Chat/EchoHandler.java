package com.dz.shop.Chat;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class EchoHandler extends TextWebSocketHandler implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    private static final Map<Object, WebSocketSession> userAndWebSocket = new ConcurrentHashMap<>();
    private static final Map<Object, HashSet<Object>> roomAndSessionNames = new ConcurrentHashMap<>();

    public EchoHandler(){
        super();
        logger.info("create SocketHandler instance!");
    }

    //WebSocket 연결이 닫혔을 때 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        logger.info("remove session!");
    }

    //WebSocket 연결이 열리고 사용이 준비될 때 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        logger.info("add session! > " + session.getId());
        Map<String,Object> webSession = session.getAttributes();
        Object sessionId = webSession.get("sessionChat");

        Object isAdmin = webSession.get("sessionIsAdmin");
        if(isAdmin == null){
            HashSet<Object> roomInUsers = roomAndSessionNames.get(sessionId);
            if (roomInUsers == null) {
                roomInUsers = new HashSet<>();
                roomInUsers.add(sessionId);
                roomInUsers.add("admin");
                roomAndSessionNames.put(sessionId, roomInUsers);
            }
            userAndWebSocket.put(sessionId, session);
        }else{
            userAndWebSocket.put("admin", session);
        }
    }

    //    클라이언트로부터 메시지가 도착했을 때 호출
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        Map<String,Object> map = session.getAttributes();
        String receive = message.getPayload().toString();
        JSONObject jsonObject = new JSONObject(receive);

        String roomName = (String) jsonObject.get("roomName");

        Object sender = map.get("sessionChat");
        Object isAdmin = map.get("sessionIsAdmin");
        if(isAdmin != null) sender = "admin";
        sendMessage(sender, receive, roomName);
        logger.info(" receive message:" + message.getPayload());
    }

    //전송 에러 발생할 때 호출
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        this.logger.error("web socket error!", exception);
    }

    //WebSocketHandler가 부분 메시지를 처리할 때 호출
    @Override
    public boolean supportsPartialMessages() {
        this.logger.info("call method!");
        return super.supportsPartialMessages();
    }


    public void sendMessage(Object sender, String message, String roomName) throws IOException {
        WebSocketSession senderSession = userAndWebSocket.get(sender);
        Set<Object> sessionNameLists = roomAndSessionNames.get(roomName);
        if(sessionNameLists == null){
            System.out.println("sessionNameLists is null = " + sessionNameLists);
            return;
        }

        for(Object s : sessionNameLists){
            System.out.println("s = " + s);
            WebSocketSession webSocketSession = userAndWebSocket.get(s);
            if(webSocketSession == senderSession) continue;
            if(webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(message));
            }
        }
    }

    //BeanFactory클래스에 의해 객체 생성 후  사용자자 초기화 작업을 수행 하기 위한 부분
    @Override
    public void afterPropertiesSet() throws Exception {

//        Thread thread = new Thread() {
//            int i = 0;
//
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        sendMessage("send message index " + i++);
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                }
//            }
//        };
//        thread.start();
    }
}