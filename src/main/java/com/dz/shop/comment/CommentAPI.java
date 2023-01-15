
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
    // 제 성격상 조직에 누가 되면 제가 ㅁㄴ저 나간다.
    // 이 조직이 성장해야 내가 성장한다고 생각하고, 거기에 도움이 되어야 한다고 생각한다.
    // 조직에서 개인의 이익을 찾는게 득이 되지 않는다고 생각한다.
    // 단기적으로 개인의 이익을 찾으면 득이 된다고 생각하는 사람들도 있지만,
    // 그렇게 해서 잘 된사람을 보지 못했다.
    // 나는 이 조직이 성장하는데에 도움을 주고, 나도 함께 성장해 나가고 싶다.
}
