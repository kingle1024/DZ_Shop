package com.dz.shop.service.Impl;

import com.dz.shop.Dao.ChatDAO;
import com.dz.shop.entity.ChatMessage;
import com.dz.shop.entity.ChatRoomVO;
import com.dz.shop.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatDAO chatDAO;

    @Override
    public long add(ChatMessage chatMessage) {
        ChatRoomVO chatRoom = chatDAO.isChatRoom(chatMessage.getChat_room());
        if(chatRoom == null) chatDAO.add(chatMessage);
        return 1;
    }

    @Override
    public List<ChatMessage> list() {
        return chatDAO.list();
    }

    @Override
    public ChatRoomVO getOrCreateChatRoom(Object sessionId) {
        ChatRoomVO chatRoomVO;
        chatRoomVO = chatDAO.isChatRoom(sessionId);
        if(chatRoomVO == null){
            ChatMessage chatMessage = ChatMessage.builder()
                    .chat_room((String) sessionId)
                    .build();
            chatDAO.add(chatMessage);
        }

        return chatRoomVO;
    }


}
