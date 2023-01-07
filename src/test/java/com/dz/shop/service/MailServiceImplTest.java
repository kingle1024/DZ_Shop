package com.dz.shop.service;

import com.dz.shop.Dao.MailDAO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/mail-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
class MailServiceImplTest {
    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private MailDAO mailDAO;

    @Test
    void validRetValue() {
//        let param = {
//                "retValue" : document.getElementById("retValue").value,
//                "email" : document.getElementById("email").value,
//                "userId" : document.getElementById("userId").value,
//                "pwd" : document.getElementById("pwd").value
//        }
        Map<String, Object> map = new HashMap<>();
        map.put("retValue", "retValue");
        map.put("email", "teran1024@naver.com");
        map.put("userId", "123");
        map.put("pwd", "123");

        mailService.validRetValue(map);


    }
}