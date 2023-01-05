package com.dz.shop.Product;

import com.dz.shop.entity.Popularity;
import com.dz.shop.service.PopularityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/product/*")
public class ClientProductAPI {
    private static final Logger logger = LoggerFactory.getLogger(ClientProductAPI.class);
    @Autowired
    PopularityService popularityService;

    @RequestMapping(value = "/popularity", method = RequestMethod.POST)
    public Map<String, Object> popularity(HttpSession session,
                             @RequestBody Popularity popularity
    ){
        logger.info("ClientProductAPI.popularity");
        String userId = (String) session.getAttribute("sessionUserId");
        popularity.setUserId(userId);

        Map<String, Object> resultMap = popularityService.add(popularity);

        return resultMap;
    }
}
