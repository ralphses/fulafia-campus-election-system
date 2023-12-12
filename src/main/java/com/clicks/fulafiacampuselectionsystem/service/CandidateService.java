package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.CandidateDto;
import com.clicks.fulafiacampuselectionsystem.exceptions.EntityExistException;
import com.clicks.fulafiacampuselectionsystem.exceptions.ResourceNotFoundException;
import com.clicks.fulafiacampuselectionsystem.model.Candidate;
import com.clicks.fulafiacampuselectionsystem.model.Election;
import com.clicks.fulafiacampuselectionsystem.model.ElectionPosition;
import com.clicks.fulafiacampuselectionsystem.repository.CandidateRepository;
import com.clicks.fulafiacampuselectionsystem.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final DtoMapper mapper;
    private final ElectionService electionService;
    private final AppUserService userService;

    public void register(CandidateDto candidateDto) {

        Election election = electionService.getById(candidateDto.electionId());

        Optional<ElectionPosition> positionOptional = election.getOwner()
                .getElectionPositions()
                .stream()
                .filter(electionPosition -> electionPosition.getTitle().equalsIgnoreCase(candidateDto.position()))
                .findFirst();

        if(positionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Election position not found for specified electionId");
        }

        String electionTitle = election.getTitle();
        String candidateName = candidateDto.name();

        if(candidateRepository.existsByUserNameAndElectionTitle(candidateName, electionTitle)) {
            throw new EntityExistException("Candidate with fullName " + candidateName + " already registered for " + electionTitle);
        }

        candidateRepository.save(
                Candidate.builder()
                        .election(election)
                        .user(userService.getByPhone(candidateDto.phone()))
                        .position(positionOptional.get())
                        .ussdCode(candidateRepository.countByElection_Id(election.getId()) + 1)
                        .voteCount(0)
                        .build());
    }

    public CandidateDto get(Long id) {
        return mapper.candidateDto(getById(id));
    }


    public void delete(Long id) {
        candidateRepository.delete(getById(id));
    }

    public Candidate getById(long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with ID " + id + " not found"));
    }

    public Candidate getByUssdCode(long electionCode, long candidateCode) {
        return candidateRepository.findByElectionAndUssdCode(electionCode, candidateCode)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with USSD code " + candidateCode + " not found"));
    }
}
