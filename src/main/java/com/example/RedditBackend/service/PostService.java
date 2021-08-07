package com.example.RedditBackend.service;


import com.example.RedditBackend.dto.PostRequest;
import com.example.RedditBackend.dto.PostResponse;
import com.example.RedditBackend.exception.PostNotFoundException;
import com.example.RedditBackend.exception.SubredditNotFoundException;
import com.example.RedditBackend.mapper.PostMapper;
import com.example.RedditBackend.model.Post;
import com.example.RedditBackend.model.Subreddit;
import com.example.RedditBackend.model.User;
import com.example.RedditBackend.repository.PostRepository;
import com.example.RedditBackend.repository.SubredditRepository;
import com.example.RedditBackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {


  private final SubredditRepository subredditRepository;
  private final PostRepository postRepository;
  private final AuthService authService;
  private final PostMapper postMapper;
  private final UserRepository userRepository;
  public void save(PostRequest postRequest){
    Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
            .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
    postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    return postRepository.findAll()
            .stream()
            .map(postMapper::mapToDto)
            .collect(toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsBySubreddit(Long subredditId) {
    Subreddit subreddit = subredditRepository.findById(subredditId)
            .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
    List<Post> posts = postRepository.findAllBySubreddit(subreddit);
    return posts.stream().map(postMapper::mapToDto).collect(toList());
  }
  @Transactional(readOnly = true)
  public PostResponse getPost(Long id) {
    Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id.toString()));
    return postMapper.mapToDto(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    return postRepository.findByUser(user)
            .stream()
            .map(postMapper::mapToDto)
            .collect(toList());
  }

}
