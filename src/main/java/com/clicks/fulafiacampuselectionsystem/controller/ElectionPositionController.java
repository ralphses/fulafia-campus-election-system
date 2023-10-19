package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.ElectionPositionDto;
import com.clicks.fulafiacampuselectionsystem.service.ElectionPositionService;
import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/position")
public class ElectionPositionController {

    private final ElectionPositionService positionService;

    @PostMapping("")
    public ResponseEntity<CustomResponse> add(@RequestBody ElectionPositionDto positionDto) {
        positionService.add(positionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getClient(@PathVariable Long id) {
        ElectionPositionDto positionDto = positionService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, positionDto));
    }

    @GetMapping("")
    public ResponseEntity<CustomResponse> getUsers(
            @RequestParam(name = "client", required = false) Long client) {
        List<ElectionPositionDto> positionDtos = positionService.getPositions(client);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, positionDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> update(
            @RequestBody ElectionPositionDto positionDto,
            @PathVariable Long id) {
        positionService.update(id, positionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

}
