package com.dz.shop.member;

import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	@Autowired
	MemberService memberService;

	@RequestMapping(value = "/loginForm.do", method = RequestMethod.GET)
	public String loginForm() {
		System.out.println("MemberController.loginForm");
		return "/member/loginForm";
	}

	@RequestMapping("/login.do")
	public String login() {
		return "main";
	}

	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/register.do")
	public String register() {
		return "/member/register";
	}

	@RequestMapping("/searchId.do")
	public String searchId() {
		System.out.println("MemberController.searchId");
		return "/member/searchId";
	}

	@RequestMapping("/searchPwd.do")
	public String searchPwd() {
		logger.debug("MemberController.searchPwd");
		return "/member/searchPwd";
	}
}
