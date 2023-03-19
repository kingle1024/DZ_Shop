package com.dz.shop.Todo;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/todo/*")
public class TodoController {
    @RequestMapping("list")
    public Map<String, Object> list(){
        HashMap<String, Object> resultMap = new HashMap<>();

        resultMap.put("status", true);
        return resultMap;
    }
}
