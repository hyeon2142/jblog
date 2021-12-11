package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public boolean addPost(PostVo postVo) {
		if(postVo != null && (postVo.getTitle().equals("") || postVo.getContents().equals("") ) ) {
			return false;
		}
		return postRepository.insert(postVo);
	}

	public List<PostVo> getAll(String blogId) {
		return postRepository.findAll(blogId);
	}

	public List<PostVo> getAllByCategoryNo(String blogId, Long catergoryNo) {
		return postRepository.findAllByCategoryNo(blogId,catergoryNo);
	}

	public PostVo getLastPostVo() {
		return postRepository.findLastPostVo();
	}
}
