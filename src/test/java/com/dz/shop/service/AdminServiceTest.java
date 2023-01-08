package com.dz.shop.service;

import com.dz.shop.Dao.Del_MemberDAO;
import com.dz.shop.Page.PageUtil;
import com.dz.shop.entity.DelMember;
import com.dz.shop.entity.MemberEnum;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/mail-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class AdminServiceTest {
    @Autowired
    AdminService adminService;
    @Autowired
    Del_MemberDAO del_memberDAO;

    @Test
    public void pageUtil() {
        PageUtil pageUtil = adminService.pageUtil("", "", "");
        Assertions.assertNotNull(pageUtil);
    }

    @Test
    public void userStatus_ChangeUSE() {
        String userStatus = adminService.userStatus("user06", MemberEnum.STOP.name());
        assertEquals(userStatus, "USE");
    }

    @Test
    public void userStatus_ChangeSTOP() {
        String userStatus = adminService.userStatus("user06", MemberEnum.USE.name());
        assertEquals(userStatus, "STOP");
    }

    @Test
    public void del_OK(){
        String userId = "user06";
        long user06 = adminService.del(userId);
        assertEquals(user06, 1);
        DelMember byUserId = del_memberDAO.findByUserId(userId);
        assertEquals(userId, byUserId.getUserId());

        // clean
        long result = del_memberDAO.deleteByUserId(userId);
        assertTrue(result > 0);
    }

    @Test
    public void del_FAIL(){
        long user06 = adminService.del("user06qweqweqweqweqwasdasdasd");
        assertEquals(user06, -1);
    }
}