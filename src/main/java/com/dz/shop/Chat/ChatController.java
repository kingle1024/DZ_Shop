package com.dz.shop.Chat;

import com.dz.shop.Utility.ChatUtil;
import com.dz.shop.entity.ChatMessage;
import com.dz.shop.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    ChatService chatService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model){
        logger.info("ChatController.list");
        String chatSessionId = ChatUtil.getSessionIdFromCookie(request, response);
        session.setAttribute("sessionChat", chatSessionId);

        model.addAttribute("roomName", chatSessionId);
        ChatMessage chatMessage = ChatMessage.builder()
                .chat_room(chatSessionId)
                .build();
        chatService.add(chatMessage);

        return "chat/list";
    }

}
