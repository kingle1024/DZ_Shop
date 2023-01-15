package com.dz.shop.admin.comment;

import com.dz.shop.comment.CommentService;
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
@RequestMapping("/admin/comment")
public class AdminCommentController {
    private static final Logger logger = LoggerFactory.getLogger(AdminCommentController.class);

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        logger.info("AdminCommentController.list");
        model.addAttribute("list", commentService.commentList());
        return "admin/comment/list";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(
            @RequestParam String no,
            Model model){
        model.addAttribute("comment", commentService.getProduct(no));
        model.addAttribute("detail", commentService.findByParentNo(no));

        return "admin/comment/view";
    }
}
