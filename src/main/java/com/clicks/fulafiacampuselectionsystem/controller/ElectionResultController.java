package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.ElectionResultDto;
import com.clicks.fulafiacampuselectionsystem.service.ElectionResultService;
import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/result")
public class ElectionResultController {

    private final ElectionResultService electionResultService;

    @GetMapping("/{electionId}")
    public ResponseEntity<CustomResponse> get(@PathVariable Long electionId) {
        ElectionResultDto electionResult = electionResultService.get(electionId);
        return ResponseEntity.ok(new CustomResponse(true, electionResult));
    }

}
