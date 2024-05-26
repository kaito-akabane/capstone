package com.example.ProjectCC;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> users = new ArrayList<WebSocketSession>();
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 세션을 맵에 추가합니다.
        users.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LocalTime time = LocalTime.now();
        String now = time.getHour() + ":" + time.getMinute();
        // 메시지 객체 생성

        JSONObject object = new JSONObject(message.getPayload());
        String type = object.getString("type");

        if(type != null && type.equals("register") ) {
            //등록 요청 메시지
            String user = object.getString("userid");
            sessions.put(user, session);
        }
        else {
            //채팅 메시지 : 상대방 아이디를 포함해서 메시지를 보낼것이기 때문에
            //Map에서 상대방 아이디에 해당하는 WebSocket 꺼내와서 메시지 전송
            String target = object.getString("target");
            String sender = object.getString("sender");
            String msg = object.getString("message");

            WebSocketSession ws = (WebSocketSession)sessions.get(target);
            if(ws != null) {
                if(ws.isOpen()) {
                    ws.sendMessage(new TextMessage(msg));
                } else {
                    sessions.remove(target);
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println(session);;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}


