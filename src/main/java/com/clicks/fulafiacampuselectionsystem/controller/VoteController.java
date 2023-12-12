package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.UssdRequest;
import com.clicks.fulafiacampuselectionsystem.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "/handle", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String handleUssdRequest(UssdRequest ussdRequest) {
        return voteService.vote(ussdRequest);
    }

}
