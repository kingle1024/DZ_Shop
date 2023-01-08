package com.dz.shop.service;

import com.dz.shop.Dao.MemberDAO;
import com.dz.shop.entity.MemberEnum;
import com.dz.shop.entity.MemberVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/mail-context.xml"
        , "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberDAO memberDAO;

    @Test
    public void login_OK() {
        String userId = "user06";
        MemberVO memberVO = MemberVO.builder()
                .userId(userId)
                .pwd("pwd")
                .build();
        MemberVO login = memberService.login(memberVO);
        assertEquals(userId, login.getUserId());
    }

    @Test
    public void login_FAIL() {
        String userId = "user06qweqweqweqweqwasdasdasd";
        MemberVO memberVO = MemberVO.builder()
                .userId(userId)
                .pwd("pwd")
                .build();
        MemberVO login = memberService.login(memberVO);
        assertNull(login);
    }

    @Test
    public void dupUidaCheck_OK() {
        String userId = "user06";
        MemberVO memberVO = memberService.dupUidaCheck(userId);
        assertNotNull(memberVO);
    }

    @Test
    public void dupUidaCheck_FAIL() {
        String userId = "user06qweqweqweqweqwasdasdasd";
        MemberVO memberVO = memberService.dupUidaCheck(userId);
        assertNull(memberVO);
    }

    @Test
    public void insert_OK() {
        String userId = "user"+ UUID.randomUUID();
        String email = userId+"@naver.com";
        String phone = "010-0000-0000";
        MemberVO memberVO = MemberVO.builder()
                .userId(userId)
                .pwd(userId)
                .email(email)
                .name(userId)
                .phone(phone)
                .build();
        long insert = memberService.insert(memberVO);
        assertEquals(1, insert);

        MemberVO byId = memberDAO.findById(userId);
        assertEquals(userId, byId.getUserId());
        assertEquals(userId, byId.getPwd());
        assertEquals(email, byId.getEmail());
        assertEquals(phone, byId.getPhone());
        assertFalse(byId.isDelete_yn());
        assertFalse(byId.isAdmin());
        assertEquals(MemberEnum.USE.name(), byId.getUserStatus());

        memberDAO.deleteById(userId);
    }

    @Test
    public void insert_FAIL() {
        String userId = "user"+ UUID.randomUUID();
        String email = userId+"@naver.com";
        String phone = "010-0000-0000";
        MemberVO memberVO = MemberVO.builder()
                .pwd(userId)
                .email(email)
                .name(userId)
                .phone(phone)
                .build();
        long insert = memberService.insert(memberVO);
        assertEquals(-1, insert);
    }

    @Test
    public void edit_OK() {
        //given
        String user = "user"+ UUID.randomUUID();
        MemberVO memberVO = MemberVO.builder()
                .userId(user)
                .pwd(user)
                .name(user)
                .build();
        memberDAO.insert(memberVO);

        //when
        Map<String, Object> map = new HashMap<>();
        map.put("userId", memberVO.getUserId());
        String pwd = "changePassword";
        map.put("pwd", pwd);

        //then
        long edit = memberService.edit(map);
        assertEquals(1, edit);

        MemberVO byId = memberDAO.findById(user);
        assertEquals(pwd, byId.getPwd());

        // clean
        memberDAO.deleteById(user);
    }

    @Test
    public void edit_SearchID_FAIL() {
        //when
        Map<String, Object> map = new HashMap<>();
        map.put("temp","temp");

        //then
        long edit = memberService.edit(map);
        assertEquals(-1, edit);
    }
}