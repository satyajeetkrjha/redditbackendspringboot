package com.example.RedditBackend.repository;

import com.example.RedditBackend.model.Post;
import java.util.List;
import com.example.RedditBackend.model.Comment;
import com.example.RedditBackend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
