
package com.dz.shop.comment;

import com.dz.shop.entity.CommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/comment/*")
public class CommentAPI {
    private static final Logger logger = LoggerFactory.getLogger(CommentAPI.class);
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> list(@RequestBody CommentVO commentVO){
        logger.info("CommentAPI.list");
        CommentVO comment = commentService.add(commentVO);

        Map<String, Object> resultMap = new HashMap<>();
        if(comment != null){
            resultMap.put("status", true);
            resultMap.put("param_comment", comment);
            resultMap.put("register_date", comment.register_date.toString());
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

}
