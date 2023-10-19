package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.ElectionPositionDto;
import com.clicks.fulafiacampuselectionsystem.exceptions.EntityExistException;
import com.clicks.fulafiacampuselectionsystem.exceptions.ResourceNotFoundException;
import com.clicks.fulafiacampuselectionsystem.model.Client;
import com.clicks.fulafiacampuselectionsystem.model.ElectionPosition;
import com.clicks.fulafiacampuselectionsystem.repository.ElectionPositionRepository;
import com.clicks.fulafiacampuselectionsystem.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectionPositionService {

    private final ElectionPositionRepository positionRepository;
    private final ClientService clientService;
    private final DtoMapper mapper;

    public void add(ElectionPositionDto positionDto) {

        long clientId = positionDto.client();
        String positionTitle = positionDto.title();

        if(positionRepository.existsByTitleAndOwnerId(positionTitle, clientId)) {
            throw new EntityExistException("Position " + positionTitle + " for client " + positionTitle + " exists");
        }

        Client client = clientService.getClientById(clientId);
        positionRepository.save(
                ElectionPosition.builder()
                        .title(positionTitle)
                        .fee(positionDto.fee())
                        .owner(client)
                        .ussdCode(positionRepository.countByOwner_Id(clientId) + 1)
                        .requirements(positionDto.requirements())
                        .build());
    }

    public ElectionPositionDto get(Long id) {
        return mapper.electionPositionDto(getById(id));
    }

    public List<ElectionPositionDto> getPositions(Long client) {
        return clientService.getClientById(client)
                .getElectionPositions()
                .stream()
                .map(mapper::electionPositionDto)
                .collect(Collectors.toList());
    }

    public void update(Long id, ElectionPositionDto positionDto) {
        ElectionPosition electionPosition = getById(id);
        electionPosition.setFee(positionDto.fee());
        electionPosition.setTitle(positionDto.title());
        electionPosition.setRequirements(positionDto.requirements());
    }

    public void delete(Long id) {
        positionRepository.delete(getById(id));
    }

    public ElectionPosition getById(long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election position with ID " + id + " not found"));
    }

    public ElectionPosition getByUssdCode(long clientId, long code) {
        return positionRepository.findByOwner_IdAndUssdCode(clientId, code)
                .orElseThrow(() -> new ResourceNotFoundException("Election position with USSD code " + code + " not found"));
    }
}
