package com.douzone.jblog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	public boolean createBlog(UserVo userVo) {
		String url = "/assets/images/comodo.jpg";
		
		BlogVo blogVo = new BlogVo(); 
		blogVo.setId(userVo.getId());
		blogVo.setTitle(userVo.getName() + "'s Blog");
		blogVo.setLogo(url);
		
		return blogRepository.insert(blogVo);
	}

	public BlogVo getBlog(String blogId) {
		return blogRepository.findById(blogId);
	}

	public boolean updateBlog(BlogVo blogVo) {
		return blogRepository.update(blogVo);
	}

	public List<BlogVo> getAll() {
		return blogRepository.findAll();
	}

	
	
	
}
