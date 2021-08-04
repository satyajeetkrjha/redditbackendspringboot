package com.example.RedditBackend.service;


import com.example.RedditBackend.dto.SubredditDto;
import com.example.RedditBackend.exception.SpringRedditException;
import com.example.RedditBackend.mapper.SubredditMapper;
import com.example.RedditBackend.model.Subreddit;
import com.example.RedditBackend.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;


    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
      Subreddit subreddit= subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
      subredditDto.setId(subreddit.getId());
      return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());

    }

    private SubredditDto mapToDto(Subreddit subreddit) {
       return SubredditDto.builder().name(subreddit.getName()).id(subreddit.getId()).numberOfPosts(subreddit.getPosts().size()).build();
    }


    private Subreddit mapSubredditdto(SubredditDto subredditDto){
        return  Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
    }
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

}
