package com.clicks.fulafiacampuselectionsystem;

import com.clicks.fulafiacampuselectionsystem.enums.UserRole;
import com.clicks.fulafiacampuselectionsystem.model.*;
import com.clicks.fulafiacampuselectionsystem.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class FulafiaCampusElectionSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FulafiaCampusElectionSystemApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(ElectionRepository electionRepository,
                                        ElectionPositionRepository electionPositionRepository,
                                        CandidateRepository candidateRepository,
                                        ClientRepository clientRepository,
                                        AppUserRepository userRepository) {
        return args -> {

            AppUser felixHeenga = AppUser.builder()
                    .name("Felix Heenga")
                    .phone("+2349036642659")
                    .email("felix@gmail.com")
                    .role(UserRole.CLIENT_ADMIN)
                    .identity("2031800065")
                    .build();

            AppUser joyAyikpo = userRepository.save(AppUser.builder()
                    .name("Joy Heenga")
                    .phone("+2349036642658")
                    .email("joy@gmail.com")
                    .role(UserRole.VOTER)
                    .identity("2031800066")
                    .build());


            Client client =
                    Client.builder()
                            .address("Federal University of Lafia")
                            .name("NACOS")
                            .owner(felixHeenga)
                            .ussdCode(20L)
                            .members(List.of(felixHeenga))
                            .build();

            Election election = Election.builder()
                    .owner(client)
                    .ussdCode(10L)
                    .startTime(LocalTime.now())
                    .stopTime(LocalTime.now().plus(3, ChronoUnit.HOURS))
                    .startDate(LocalDate.now())
                    .stopDate(LocalDate.now().plus(3, ChronoUnit.DAYS))
                    .createTime(LocalDateTime.now())
                    .title("NACOS 2021/2022 Elections")
                    .build();

            ElectionPosition positionPresident = ElectionPosition.builder()
                    .owner(client)
                    .ussdCode(40L)
                    .title("President")
                    .fee(200.0)
                    .build();

            Candidate candidate = Candidate.builder()
                    .election(election)
                    .ussdCode(30L)
                    .user(joyAyikpo)
                    .voteCount(0)
                    .position(positionPresident)
                    .build();

            userRepository.saveAll(List.of(felixHeenga, joyAyikpo));
            clientRepository.save(client);
            electionRepository.save(election);
            electionPositionRepository.save(positionPresident);
            candidateRepository.save(candidate);


        };
    }
}
