package com.dz.shop.service;

import com.dz.shop.admin.MemberEnum;
import com.dz.shop.mappers.AdminDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:root-context.xml"})
//@WebAppConfiguration
public class AdminServiceImplTest {

    @Mock
    AdminDAO adminDAO;

    @InjectMocks
    AdminService adminService;

    @Test
    public void memberStatus(){
        MockitoAnnotations.initMocks(this);
        adminService = new AdminServiceImpl(adminDAO);
        System.out.println();
        when(adminService.userStatus("user06", MemberEnum.STOP.name()));
        verify(adminService).userStatus("user06",MemberEnum.STOP.name());
//        assertEquals(1,
//                adminService.userStatus("user06", MemberEnum.STOP.name()));

    }

    @Test
    void pageUtil() {
//        MockitoAnnotations.initMocks(this);

        adminService = new AdminServiceImpl(adminDAO);
        System.out.println("adminDAOreal = " + adminService);
        System.out.println(adminService.pageUtil("qwe","1", ""));

    }

    //    @Autowired
//    private AdminDAO dao;

//    @Test
//    public void userStatus(){
//        AdminService adminService = mock(AdminService.class);
//        System.out.println("adminService = " + adminService);
//
//        System.out.println(adminService.userStatus("user06", "USE"));
//        System.out.println("success");
//
//        System.out.println(verify(adminService).userStatus("user06","USE"));
//
//    }
//    @Test
//    public void test(){
//        ApplicationContext context = new GenericXmlApplicationContext("root-context.xml");
//        System.out.println(context.getApplicationName());
//        AdminDAO dao = context.getBean("adminDAO",AdminDAO.class);
//
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", "user06");
//        map.put("userStatus", "stop");
//
//        System.out.println(dao.userStatus(map));
//    }
}