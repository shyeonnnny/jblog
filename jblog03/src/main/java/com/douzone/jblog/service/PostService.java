package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public boolean write(PostVo vo, String id) {
		return postRepository.insert(vo, id);
	}
	
	public List<PostVo> getPost(Long no){
		return postRepository.findAll(no);
	}
	
	public PostVo getContents(Long pno) {
		return postRepository.findContents(pno);
	}

}
