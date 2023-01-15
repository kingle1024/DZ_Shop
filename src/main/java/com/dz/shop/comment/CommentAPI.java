
package com.dz.shop.comment;

import com.dz.shop.entity.CommentVO;
import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment/*")
public class CommentAPI {
    private static final Logger logger = LoggerFactory.getLogger(CommentAPI.class);
    @Autowired
    CommentService commentService;
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> list(HttpSession session, @RequestBody CommentVO commentVO){
        logger.info("CommentAPI.list");
        String userId = (String) session.getAttribute("sessionUserId");
        String name = (String) session.getAttribute("sessionName");
        commentVO.setStatus(CommentStatus.READY.name());
        commentVO.setWriter(name);
        commentVO.setWriter_id(userId);

        CommentVO comment = commentService.add(commentVO);

        Map<String, Object> resultMap = new HashMap<>();
        if(comment != null){
            resultMap.put("status", true);
            resultMap.put("register_date", comment.getRegister_date().toString());
            resultMap.put("comment", commentVO);
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public Map<String, Object> reply(@RequestBody CommentVO commentVO, HttpSession session){
        logger.info("CommentAPI.reply");
        String userId = (String) session.getAttribute("sessionUserId");
        String name = (String) session.getAttribute("sessionName");
        commentVO.setStatus(CommentStatus.DONE.name());
        commentVO.setWriter_id(userId);
        commentVO.setWriter(name);
        commentVO.setRegister_date(LocalDateTime.now());
        System.out.println("commentVO = " + commentVO);
        long result = commentService.reply(commentVO);

        Map<String, Object> resultMap = new HashMap<>();
        if(result != 0){
            resultMap.put("status", true);
            resultMap.put("comment", commentVO);
            resultMap.put("comment_register_date", commentVO.getRegister_date().toString());
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Map<String, Object> detail(
            @RequestParam String parentNo
            ){
        System.out.println("CommentAPI.detail");
        System.out.println("no = " + parentNo);
        List<CommentVO> comments = commentService.findByParentNo(parentNo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", comments);

        return resultMap;
    }
}
