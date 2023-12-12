package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.UssdRequest;
import com.clicks.fulafiacampuselectionsystem.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String vote(@RequestBody UssdRequest ussdRequest) {
        return voteService.vote(ussdRequest);
    }

}
