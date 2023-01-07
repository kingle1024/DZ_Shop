package com.dz.shop.member;

import com.dz.shop.entity.MailVO;
import com.dz.shop.entity.MemberVO;
import com.dz.shop.service.MailService;
import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@EnableAsync
@RestController("memberAPI")
@RequestMapping("/api/member/*")
public class MemberAPI {
    private static final Logger logger = LoggerFactory.getLogger(MemberAPI.class);
    @Autowired
    MemberService memberService;
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(
            @RequestBody MemberVO memberVO, HttpSession session,
            HttpServletRequest request
            ){
        System.out.println("MemberAPI.userStatus");

        MemberVO member = memberService.login(memberVO);
        Map<String, Object> map = new HashMap<>();

        if(member == null){
            map.put("status", false);
            return map;
        }

        session.setAttribute("isLogin", true);
        session.setAttribute("sessionUserId", member.getUserId());
        session.setAttribute("sessionName", member.getName());
        System.out.println("member = " + member.getName());
        map.put("status", true);
        map.put("href", request.getContextPath());

        return map;
    }

    @RequestMapping(value = "/dupUidCheck", method = RequestMethod.GET)
    public Map<String, Object> dupUidCheck(HttpServletRequest request){
        System.out.println("MemberAPI.dupUidCheck");

        String userId = request.getParameter("userId");
        MemberVO member = memberService.dupUidaCheck(userId);

        Map<String, Object> result = new HashMap<>();
        if (member == null) {
            result.put("status", true);
            result.put("message", "해당 아이디는 사용 가능합니다.");
        } else {
            result.put("status", false);
            result.put("message", "해당 아이디는 사용 불가능합니다.");
        }

        return result;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Map<String, Object> insert(
            @RequestBody MemberVO member,
            HttpServletRequest request
    ){
        System.out.println("MemberAPI.insert");
        long result = memberService.insert(member);
        mailService.welcomSendMail(member);

        Map<String, Object> resultMap = new HashMap<>();
        if (result < 0) {
            resultMap.put("status", false);
            resultMap.put("message", "회원가입 실패!");
        } else {
            resultMap.put("status", true);
            resultMap.put("message", "회원가입 성공!");
            resultMap.put("url", request.getContextPath()+"/member/loginForm.do");
        }

        return resultMap;
    }

    @RequestMapping(value = "/searchId", method = RequestMethod.POST)
    public Map<String, Object> searchId(
            @RequestBody Map<String, Object> map
    ) {
        System.out.println("MemberAPI.searchId");
        System.out.println("map = " + map);

        long result;
        try {
            result = mailService.sendSearchIdMail(map);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 아이디, 이름 맞으면 이메일 전송
        // 이메일 임시번호 client로 전달
        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0){
            resultMap.put("status", true);
            resultMap.put("message", "입력하신 메일로 아이디가 전송되었습니다.");
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/searchPwd", method = RequestMethod.POST)
    public Map<String, Object> searchPwd(
            @RequestBody Map<String, Object> map
    ) {
        logger.info("MemberAPI.searchPwd");
        System.out.println("map = " + map);
        long result;
        try {
            result = mailService.sendSearchPwdMail(map);
            System.out.println("result = " + result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();
        if(result > 0){
            resultMap.put("status", true);
            resultMap.put("message", "전송되었습니다.");
        }else{
            resultMap.put("status", false);
        }

        return resultMap;
    }

    @RequestMapping(value = "/checkValue", method = RequestMethod.POST)
    public Map<String, Object> checkValue(
            @RequestBody Map<String, Object> map
    ) {
        logger.info("MemberAPI.checkValue");
        System.out.println("map = " + map);

        MailVO mail = mailService.checkValue(map);
        System.out.println("mail = " + mail);
        Map<String, Object> resultMap = new HashMap<>();

        if(mail != null){
            LocalDateTime create_date = mail.getCreate_date();
            if(create_date.plusMinutes(5).isAfter(LocalDateTime.now())){
                String retValue = mailService.createRetValue(map);
                if(retValue != null) {
                    resultMap.put("message", "성공");
                    resultMap.put("retValue", retValue);
                } else {
                    resultMap.put("message", "인증번호 발급 실패");
                }
            }else{
                resultMap.put("message", "인증 시간이 지났습니다.");
            }
            resultMap.put("status", true);
        }else{
            resultMap.put("status", false);
            resultMap.put("message", "인증번호가 다릅니다.");
        }

        return resultMap;
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public Map<String, Object> password(
            @RequestBody Map<String, Object> map,
            HttpServletRequest request
            // Param : retValue, email, userId
    ) {
        Map<String, Object> resultMap = new HashMap<>();
        // retValue 체크
        System.out.println("MemberAPI.password");
        if(mailService.validRetValue(map) < 0){
            resultMap.put("status", false);
            return resultMap;
        }

        // edit
        if(memberService.edit(map) > 0){
            resultMap.put("status", true);
            resultMap.put("message", "비밀번호가 변경되었습니다.");
            resultMap.put("url", request.getContextPath()+"/member/loginForm.do");
        }

        return resultMap;
    }
}
