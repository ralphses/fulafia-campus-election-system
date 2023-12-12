package com.clicks.fulafiacampuselectionsystem.controller;

import com.clicks.fulafiacampuselectionsystem.dto.ClientDto;
import com.clicks.fulafiacampuselectionsystem.service.ClientService;
import com.clicks.fulafiacampuselectionsystem.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientController {

    private final ClientService clientService;
    @PostMapping("")
    public ResponseEntity<CustomResponse> add(@RequestBody ClientDto clientDto) {
        clientService.add(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getClient(@PathVariable Long id) {
        ClientDto clientDto = clientService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, clientDto));
    }

    @GetMapping("")
    public ResponseEntity<CustomResponse> getUsers(
            @RequestParam(name = "owner", required = false) Long owner) {
        List<ClientDto> clientDtos = clientService.getClients(owner);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse(true, clientDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> update(
            @RequestBody ClientDto clientDto,
            @PathVariable Long id) {
        clientService.update(id, clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(true, "Success"));
    }
}
