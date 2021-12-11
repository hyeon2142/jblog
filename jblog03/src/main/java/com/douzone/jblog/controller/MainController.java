package com.douzone.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;

@Controller
public class MainController {
	@Autowired
	private BlogService blogService;
	
	@RequestMapping({"","/main"})
	public String main(Model model) {
		
		List<BlogVo> blogList = blogService.getAll();
		model.addAttribute("blogList",blogList);
		return "main/index";
	}
}
