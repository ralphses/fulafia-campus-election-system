package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.ElectionDto;
import com.clicks.fulafiacampuselectionsystem.service.ElectionService;
import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/election")
public class ElectionController {

    private final ElectionService electionService;

    @PostMapping("")
    public ResponseEntity<CustomResponse> add(@RequestBody ElectionDto electionDto) {
        electionService.create(electionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> get(@PathVariable Long id) {
        ElectionDto electionDto = electionService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, electionDto));
    }

    @GetMapping("/all/{page}")
    public ResponseEntity<CustomResponse> getElections(@PathVariable Integer page,
                                                       @RequestParam(name = "client") Long clientId) {
        List<ElectionDto> electionDtos = electionService.getElections(clientId, page);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, electionDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> update(
            @RequestBody ElectionDto electionDto,
            @PathVariable Long id) {
        electionService.update(id, electionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        electionService.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

}
