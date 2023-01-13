package com.dz.shop.service;

import com.dz.shop.entity.ChatMessage;
import com.dz.shop.entity.ChatRoomVO;

import java.util.List;

public interface ChatService {
    long add(ChatMessage chatMessage);
    List<ChatMessage> list();

    ChatRoomVO getOrCreateChatRoom(Object sessionId);
}
