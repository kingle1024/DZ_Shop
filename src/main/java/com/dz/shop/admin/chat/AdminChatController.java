package com.dz.shop.admin.chat;

import com.dz.shop.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/chat")
public class AdminChatController {
    private static final Logger logger = LoggerFactory.getLogger(AdminChatController.class);

    @Autowired
    ChatService chatService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, HttpSession session){
        logger.info("AdminChatController.list");
        model.addAttribute("list", chatService.list());
        session.setAttribute("admin", "admin");
        session.setAttribute("sessionChat","admin");
        return "admin/chat/list";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@RequestParam String chatRoom, Model model, HttpSession session){
        System.out.println("AdminChatController.view");
        model.addAttribute("chatRoom", chatRoom);

        String chatSessionId = "admin_" + session.getAttribute("sessionUserId");
        session.setAttribute("sessionChat", chatSessionId);
        session.setAttribute("toJoinRoom", chatRoom);

        model.addAttribute("toJoinRoom", chatRoom);


        return "admin/chat/view";
    }

}
