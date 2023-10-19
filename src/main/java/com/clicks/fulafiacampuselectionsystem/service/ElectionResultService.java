package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.ElectionResultDto;
import com.clicks.fulafiacampuselectionsystem.exceptions.ResourceNotFoundException;
import com.clicks.fulafiacampuselectionsystem.model.Election;
import com.clicks.fulafiacampuselectionsystem.model.ElectionResult;
import com.clicks.fulafiacampuselectionsystem.repository.ElectionResultRepository;
import com.clicks.fulafiacampuselectionsystem.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectionResultService {

    private final ElectionResultRepository resultRepository;
    private final DtoMapper mapper;

    public void create(Election election) {
        resultRepository.save(
                ElectionResult.builder()
                        .election(election)
                        .totalVoteCast(0)
                        .build());
    }

    public ElectionResultDto get(Long id) {
        return mapper.electionResultDto(getByElectionId(id));
    }

    private ElectionResult getByElectionId(Long id) {
        return resultRepository.findByElection_Id(id).orElseThrow(() -> new ResourceNotFoundException("Election result not found"));
    }
}
