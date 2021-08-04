package com.example.RedditBackend.service;


import com.example.RedditBackend.dto.SubredditDto;
import com.example.RedditBackend.model.Subreddit;
import com.example.RedditBackend.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

   @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
      Subreddit subreddit= subredditRepository.save(mapSubredditdto(subredditDto));
      subredditDto.setId(subreddit.getId());
      return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    private SubredditDto mapToDto(Subreddit subreddit) {
       return SubredditDto.builder().name(subreddit.getName()).id(subreddit.getId()).numberOfPosts(subreddit.getPosts().size()).build();
    }


    private Subreddit mapSubredditdto(SubredditDto subredditDto){
        return  Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
    }

}
