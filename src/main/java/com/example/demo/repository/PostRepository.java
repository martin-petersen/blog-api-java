package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("from Post i where (i.title) like :title")
    List<Post> findByTitle(String title);
    @Query("from Post i where (i.content) like :content")
    List<Post> findByContent(String content);
}
