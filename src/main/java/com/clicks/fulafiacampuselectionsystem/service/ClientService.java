package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.ClientDto;
import com.clicks.fulafiacampuselectionsystem.exceptions.EntityExistException;
import com.clicks.fulafiacampuselectionsystem.exceptions.ResourceNotFoundException;
import com.clicks.fulafiacampuselectionsystem.model.AppUser;
import com.clicks.fulafiacampuselectionsystem.model.Client;
import com.clicks.fulafiacampuselectionsystem.repository.ClientRepository;
import com.clicks.fulafiacampuselectionsystem.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final AppUserService userService;
    private final DtoMapper mapper;

    public void add(ClientDto clientDto) {

        String clientName = clientDto.name();

        Optional<Client> clientOptional = clientRepository.findByName(clientName);

        if (clientOptional.isEmpty()) {
            clientRepository.save(
                    Client.builder()
                            .address(clientDto.address())
                            .ussdCode(clientRepository.count() + 1)
                            .name(clientName)
                            .build());
        } else
            throw new EntityExistException("A clientId is already registered with the fullName " + clientName);
    }

    public ClientDto get(Long id) {
        return mapper.clientDto(getClientById(id));
    }

    public List<ClientDto> getClients(Long owner) {
        if (owner == null) {
            return clientRepository.findAll(Sort.by("fullName"))
                    .stream().map(mapper::clientDto)
                    .collect(Collectors.toList());
        }
        AppUser user = userService.getUserById(owner);
        return clientRepository.findAll().stream().filter(client -> client.getOwner().getId().equals(user.getId())).map(mapper::clientDto).collect(Collectors.toList());

    }

    public void update(Long id, ClientDto clientDto) {
        Client client = getClientById(id);

        client.setName(clientDto.name());
        client.setAddress(clientDto.address());
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("clientId with ID " + id + "Not found"));
    }

    public void delete(Long id) {
        clientRepository.delete(getClientById(id));
    }

}
