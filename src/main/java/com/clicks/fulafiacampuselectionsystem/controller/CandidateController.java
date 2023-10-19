package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.CandidateDto;
import com.clicks.fulafiacampuselectionsystem.service.CandidateService;
import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/candidate")
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("")
    public ResponseEntity<CustomResponse> add(@RequestBody CandidateDto candidateDto) {
        candidateService.register(candidateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> get(@PathVariable Long id) {
        CandidateDto candidateDto = candidateService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, candidateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        candidateService.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

}
