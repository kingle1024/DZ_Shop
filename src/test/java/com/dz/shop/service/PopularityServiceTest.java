package com.dz.shop.service;

import com.dz.shop.Dao.AdminProductDAO;
import com.dz.shop.Dao.PopularityDAO;
import com.dz.shop.entity.Popularity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/mail-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
//@WebAppConfiguration
public class PopularityServiceTest {

    @Mock
    PopularityService popularityService;

    @Test
    public void add() throws Exception {
        Popularity popularity = new Popularity();
        popularity.setBno("123");
        Map<String, Object> map = new HashMap<>();
        map.put("test","test");
        when(popularityService.add(popularity))
                .thenReturn((Map<String, Object>) map);

        assertEquals("test", map);
//
//        Popularity popularity = null;
//        Map<String, Object> add = popularityService.add(popularity);
//
//        assertEquals("성공", add.get("message") );
//        assertEquals("add", add.get("status") );
    }

    @Test
    public void findByBnoAndUserIdAndIsDelete() {

    }
}