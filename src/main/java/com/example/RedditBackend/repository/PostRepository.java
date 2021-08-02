package com.example.RedditBackend.repository;

import com.example.RedditBackend.model.Post;
import com.example.RedditBackend.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.RedditBackend.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
