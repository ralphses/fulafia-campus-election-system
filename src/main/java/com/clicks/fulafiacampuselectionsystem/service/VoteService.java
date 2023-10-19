package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.UssdRequest;
import com.clicks.fulafiacampuselectionsystem.model.Candidate;
import com.clicks.fulafiacampuselectionsystem.model.Election;
import com.clicks.fulafiacampuselectionsystem.model.ElectionPosition;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {

    private final ElectionService electionService;
    private final CandidateService candidateService;
    private final ElectionPositionService electionPositionService;
    private final AppUserService userService;

    public String vote(UssdRequest ussdRequest) {

        String[] splitText = ussdRequest.text().split("\\*");

        if (splitText.length > 4 || splitText.length < 3) {
            return prepareResponse("Bad request", true);
        }

        long electionCode = Long.parseLong(splitText[0]);
        long positionCode = Long.parseLong(splitText[1]);
        long candidateCode = Long.parseLong(splitText[2]);

        try {

            Election election = electionService.getByUssdCode(electionCode);
            Candidate candidate = candidateService.getByUssdCode(electionCode, candidateCode);

            if (splitText.length == 3) {

                userService.getByPhone(ussdRequest.phoneNumber());

                if (!isStillOpen(election)) {
                    return prepareResponse("Election not yet available or is over", true);
                }

                ElectionPosition position = electionPositionService.getByUssdCode(election.getOwner().getId(), positionCode);

                String voteDetails = responseBuilder("VOTE DETAILS",
                        "Election: ".concat(election.getTitle()),
                        "Candidate: ".concat(candidate.getUser().getName()),
                        "Position: ".concat(position.getTitle()),
                        "1. Proceed",
                        "0. Cancel");
                return prepareResponse(voteDetails, false);
            }
            else if ("1".equals(splitText[3])) {
                candidate.setVoteCount(candidate.getVoteCount() + 1);
                return prepareResponse("Vote cast successful", true);
            }
            else {
                return prepareResponse("Vote cast cancelled", true);
            }
        } catch (NumberFormatException e) {
            return prepareResponse("Phone number " + ussdRequest.phoneNumber() + " not registered to use this service", true);
        } catch (Exception e) {
            return prepareResponse("Invalid request", true);
        }
    }

    private boolean isStillOpen(Election election) {
        return isWithinDate(LocalDate.now(), election.getStartDate(), election.getStopDate()) &&
                isWithinTime(LocalTime.now(), election.getStartTime(), election.getStopTime());
    }

    private String prepareResponse(String message, boolean lastResponse) {
        return (lastResponse) ?
                "END ".concat(message) :
                "CON ".concat(message);
    }

    private String responseBuilder(String... inputs) {
        return String.join("\n", inputs);
    }

    private boolean isWithinDate(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }

    private boolean isWithinTime(LocalTime timeToCheck, LocalTime startTime, LocalTime endTime) {
        return !timeToCheck.isBefore(startTime) && !timeToCheck.isAfter(endTime);
    }

}
