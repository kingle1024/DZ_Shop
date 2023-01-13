package com.dz.shop.Dao;

import com.dz.shop.entity.ChatMessage;
import com.dz.shop.entity.ChatRoomVO;

import java.util.List;

public interface ChatDAO {
    long add(ChatMessage chatMessage);

    List<ChatMessage> list();

    ChatRoomVO isChatRoom(Object sessionId);
}
