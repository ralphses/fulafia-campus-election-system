package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.ElectionDto;
import com.clicks.fulafiacampuselectionsystem.exceptions.EntityExistException;
import com.clicks.fulafiacampuselectionsystem.exceptions.InvalidParamsException;
import com.clicks.fulafiacampuselectionsystem.exceptions.ResourceNotFoundException;
import com.clicks.fulafiacampuselectionsystem.model.Client;
import com.clicks.fulafiacampuselectionsystem.model.Election;
import com.clicks.fulafiacampuselectionsystem.repository.ElectionRepository;
import com.clicks.fulafiacampuselectionsystem.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final ClientService clientService;
    private final DtoMapper mapper;
    private final ElectionResultService electionResultService;

    public void create(ElectionDto electionDto) {

        Client client = clientService.getClientById(electionDto.clientId());

        String clientName = client.getName();
        String electionTitle = electionDto.title();

        if(electionRepository.existsByTitleAndOwnerName(electionTitle, clientName)) {
            throw new EntityExistException("Election with title " + electionTitle + " exist for clientId " + clientName);
        }
        try {

            LocalTime startTime = LocalTime.parse(electionDto.startTime());
            LocalTime stopTime = LocalTime.parse(electionDto.stopTime());
            LocalDate startDate = LocalDate.parse(electionDto.startDate());
            LocalDate stopDate = LocalDate.parse(electionDto.stopDate());

            Election election = electionRepository.save(
                    Election.builder()
                            .title(electionTitle)
                            .createTime(LocalDateTime.now())
                            .owner(client)
                            .ussdCode(electionRepository.count() + 1)
                            .startDate(startDate)
                            .startTime(startTime)
                            .stopDate(stopDate)
                            .stopTime(stopTime)
                            .build());

            electionResultService.create(election);
        } catch (DateTimeParseException exception) {
            throw new InvalidParamsException("Invalid date or time provided");
        }


    }

    public ElectionDto get(Long id) {
        return mapper.electionDto(getById(id));
    }

    public List<ElectionDto> getElections(Long clientId, Integer page) {
        return electionRepository.findByOwner(clientId, PageRequest.of(page, 10))
                .map(mapper::electionDto)
                .getContent();
    }

    public void update(Long id, ElectionDto electionDto) {

        Election election = getById(id);

        election.setTitle(electionDto.title());
        election.setStartDate(LocalDate.parse(electionDto.startDate()));
        election.setStartTime(LocalTime.parse(electionDto.startTime()));
        election.setStopDate(LocalDate.parse(electionDto.stopTime()));
        election.setStopTime(LocalTime.parse(electionDto.stopTime()));
    }

    public void delete(Long id) {
        electionRepository.delete(getById(id));
    }

    public Election getById(long id) {
        return electionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election with ID " + id + " not found"));
    }

    public Election getByUssdCode(long ussdCode) {
        return electionRepository.findByUssdCode(ussdCode)
                .orElseThrow(() -> new ResourceNotFoundException("Election with USSD Code " + ussdCode + " not found"));
    }
}
