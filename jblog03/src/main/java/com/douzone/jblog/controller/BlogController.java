package com.douzone.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!main|assets|user|blog|logoImgs).*}")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private PostService postService;
	
	@GetMapping({"","/{catergoryNo:(?!admin).*}","/{catergoryNo:(?!admin).*}/{postNo}"})
	public String blogMain(
			Model model,
			@PathVariable(value = "id", required = true) String blogId,
			@PathVariable(value = "catergoryNo", required = false) Long catergoryNo,
			@PathVariable(value = "postNo", required = false) Long postNo,
			@ModelAttribute @AuthUser UserVo authUser) {
		
		if(blogId == null) {
			return "redirect:/";
		}
		
		BlogVo blogVo = blogService.getBlog(blogId);
		
		//블로그아이디존재하는지확인
		if(blogVo == null){
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("blogId", blogId);
		
		
		List<CategoryVo> categoryList =  categoryService.getCategory(blogId);
		model.addAttribute("categoryList", categoryList);

		//id 만 넘어올때
		if (catergoryNo == null) {
			List<PostVo> postList = postService.getAll(blogId);
			if(postList.size() == 0) {
				return "blog/blog-main";
			}
			PostVo postVo = postList.get(0);
			model.addAttribute("postList", postList);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
		}
		
		// id/catergoryNo
		if (postNo == null) {
			List<PostVo> postList = postService.getAllByCategoryNo(blogId,catergoryNo);
			model.addAttribute("postList", postList);
			
			if(postList.size() == 0) {
				return "blog/blog-main";
			}
			PostVo postVo = postList.get(0);
			model.addAttribute("postVo", postVo);
			
			return "blog/blog-main";
		}
		
		// id/catergoryNo/postNo
		List<PostVo> postList = postService.getAllByCategoryNo(blogId,catergoryNo);
		model.addAttribute("postList", postList);
		
		PostVo postVo = null;
		for(PostVo vo : postList) {
			if(vo.getNo() == postNo) {
				postVo = vo;
				break;
			}
		}
		model.addAttribute("postVo", postVo);
		
		return "blog/blog-main";
	}
	
	@Auth
	@GetMapping("/admin/basic")
	public String adminBasic(Model model,@ModelAttribute @AuthUser UserVo authUser) {
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@PostMapping("/admin/basic")
	public String adminBasic(
			Model model, BlogVo blogVo,
			@RequestParam(value="logo-file") MultipartFile multipartFile,
			@ModelAttribute @AuthUser UserVo authUser) {
		String logo = fileUploadService.restoreImg(multipartFile);
		if(logo != null ) {
			blogVo.setLogo(logo);
		}
		blogService.updateBlog(blogVo);
		
		return "redirect:/" + authUser.getId();
	}
	
	@Auth
	@GetMapping("/admin/category")
	public String adminCategory(Model model,@ModelAttribute @AuthUser UserVo authUser) {
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo> list =  categoryService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	@Auth
	@PostMapping("/admin/category")
	public String adminCategory(Model model, CategoryVo categoryVo,
			@ModelAttribute @AuthUser UserVo authUser) {
		categoryVo.setBlogId(authUser.getId());
		
		categoryService.addCategory(categoryVo);
		
		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	
	@Auth
	@DeleteMapping("/admin/category/{no}")
	public String adminCategory(Model model,
			@PathVariable(value="no") Long no,
			@ModelAttribute @AuthUser UserVo authUser) {
		System.out.println("delete to Controller");
		List<PostVo> postList = postService.getAllByCategoryNo(authUser.getId(), no);
		
		//post가 없다면
		if(postList.isEmpty()) {
			categoryService.deleteCategory(authUser.getId(),no);
		}
		return "redirect:/" + authUser.getId() + "/admin/category";
	}
	
	@Auth
	@GetMapping("/admin/write")
	public String adminWrite(Model model,
			@ModelAttribute @AuthUser UserVo authUser) {
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo> list =  categoryService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}
	
	@Auth
	@PostMapping("/admin/write")
	public String adminWrite(Model model,PostVo postVo,
			@ModelAttribute @AuthUser UserVo authUser) {
		
		boolean success = postService.addPost(postVo);
		if(success) {
			return "redirect:/" + authUser.getId();
		}
		
		BlogVo blogVo = blogService.getBlog(authUser.getId());
		model.addAttribute("blogVo", blogVo);
		List<CategoryVo> list =  categoryService.getCategory(authUser.getId());
		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}

	
}
