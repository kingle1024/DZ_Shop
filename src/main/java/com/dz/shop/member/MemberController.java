package com.dz.shop.member;

import com.dz.shop.Page.PageUtil;
import com.dz.shop.mappers.MemberDAO;
import com.dz.shop.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

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
	public String login(
			@RequestParam String userId,
			@RequestParam String pwd,
			Locale locale, Model model) {
		return "home";
	}

	@RequestMapping("/register.do")
	public String login() {
		return "/member/register";
	}
}
