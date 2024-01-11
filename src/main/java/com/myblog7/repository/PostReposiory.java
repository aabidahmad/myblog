package com.myblog7.repository;

import com.myblog7.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostReposiory  extends JpaRepository<Post,Long> {
}
