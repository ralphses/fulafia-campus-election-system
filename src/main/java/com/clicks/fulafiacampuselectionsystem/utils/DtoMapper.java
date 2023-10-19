package com.clicks.fulafiacampuselectionsystem.utils;

import com.clicks.fulafiacampuselectionsystem.dto.*;
import com.clicks.fulafiacampuselectionsystem.model.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public UserDto userDto(AppUser user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getIdentity(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name());
    }

    public ElectionPositionDto electionPositionDto(ElectionPosition electionPosition) {
        return new ElectionPositionDto(
                electionPosition.getRequirements(),
                electionPosition.getTitle(),
                electionPosition.getFee(),
                electionPosition.getOwner().getId());
    }

    public CandidateDto candidateDto(Candidate candidate) {
        return new CandidateDto(
                candidate.getUser().getName(),
                candidate.getUser().getPhone(),
                candidate.getVoteCount(),
                candidate.getPosition().getTitle(),
                candidate.getElection().getId());
    }

    public ElectionDto electionDto(Election election) {
        return new ElectionDto(
                election.getCreateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a")),
                election.getTitle(),
                election.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                election.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm a")),
                election.getStopDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                election.getStopTime().format(DateTimeFormatter.ofPattern("HH:mm a")),
                election.getCandidates().stream().map(this::candidateDto).collect(Collectors.toList()),
                election.getOwner().getOwner().getId(),
                election.getUssdCode());
    }

    public ClientDto clientDto(Client client) {
        return new ClientDto(
                client.getMembers().stream().map(this::userDto).collect(Collectors.toList()),
                client.getElections().stream().map(this::electionDto).collect(Collectors.toList()),
                client.getElectionPositions().stream().map(this::electionPositionDto).collect(Collectors.toList()),
                client.getUssdCode(),
                client.getAddress(),
                client.getName(),
                client.getOwner().getId());
    }

    public ElectionWinnerDto electionWinnerDto(ElectionWinner electionWinner) {
        return new ElectionWinnerDto(candidateDto(electionWinner.getCandidate()), electionPositionDto(electionWinner.getPosition()));
    }

    public ElectionResultDto electionResultDto(ElectionResult electionResult) {
        return new ElectionResultDto(
                electionDto(electionResult.getElection()),
                electionResult.getTotalVoteCast(),
                electionResult.getWinners().stream().map(this::electionWinnerDto).toList());
    }

}
