package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo) == 1;
	}

	public List<PostVo> findAll(String blogId) {
		return sqlSession.selectList("post.findAll", blogId);
	}

	public List<PostVo> findAllByCategoryNo(String blogId, Long catergoryNo) {
		Map<String,Object> map = new HashMap<>();
		map.put("id", blogId);
		map.put("no", catergoryNo);
		return sqlSession.selectList("post.findAllByCategoryNo", map);
	}

	public PostVo findLastPostVo() {
		return sqlSession.selectOne("post.findLastPostVo");
	}
}
