package com.clicks.fulafiacampuselectionsystem.service;

import com.clicks.fulafiacampuselectionsystem.dto.UserDto;
import com.clicks.fulafiacampuselectionsystem.enums.UserRole;
import com.clicks.fulafiacampuselectionsystem.exceptions.EntityExistException;
import com.clicks.fulafiacampuselectionsystem.exceptions.InvalidParamsException;
import com.clicks.fulafiacampuselectionsystem.exceptions.ResourceNotFoundException;
import com.clicks.fulafiacampuselectionsystem.model.AppUser;
import com.clicks.fulafiacampuselectionsystem.repository.AppUserRepository;
import com.clicks.fulafiacampuselectionsystem.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.clicks.fulafiacampuselectionsystem.enums.UserRole.VOTER;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository userRepository;
    private final DtoMapper mapper;

    public void register(UserDto userDto) {

        String phone = userDto.phone();
        String email = userDto.email();

        if(!isValidRequest(phone, email)) {
            throw new EntityExistException("Phone number " + phone +
                    " or email " + email + " already used by another user");
        }

        AppUser newUser = AppUser.builder()
                .name(userDto.fullName())
                .email(email)
                .phone(phone)
                .role(VOTER)
                .identity(userDto.identity())
                .build();

        userRepository.save(newUser);

    }

    private boolean isValidRequest(String phone, String email) {
        return userRepository.existsByEmailOrPhone(email, phone);
    }

    public UserDto getUser(Long id) {
        return mapper.userDto(getUserById(id));
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user with ID " + id + " Not found"));
    }

    public List<UserDto> getUsers(String role, String election, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10);

        if(role != null && election != null) {
            //Todo: fetch voters for this electionId
        }
        else if (role != null) {
            return userRepository.findByRole(role, pageRequest)
                    .map(mapper::userDto)
                    .getContent();
        }
        else if(election != null) {
            //Todo: to be implemented fetch users for this electionId
        }
        return userRepository.findAll(pageRequest)
                .map(mapper::userDto)
                .getContent();
    }

    public void update(Long id, UserDto userDto) {
        try {
            AppUser user = getUserById(id);
            user.setEmail(userDto.email());
            user.setIdentity(userDto.identity());
            user.setRole(UserRole.valueOf(userDto.role()));
            user.setPhone(user.getPhone());
            user.setName(userDto.fullName());
        } catch (IllegalArgumentException exception) {
            throw new InvalidParamsException("Invalid role " + userDto.role());
        }
    }

    public AppUser getByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("User with phone " + phone + " not found"));
    }
}
