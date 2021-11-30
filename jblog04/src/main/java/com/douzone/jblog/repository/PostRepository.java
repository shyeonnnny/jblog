package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(PostVo vo, String id) {
		int count = sqlSession.insert("post.insert", vo);
		return count == 1;
	}
	
	public List<PostVo> findAll(Long no) {
		return sqlSession.selectList("post.findAll", no);
	}
	
	public PostVo findContents(Long pno){
		return sqlSession.selectOne("post.findContents", pno);
	}
}
