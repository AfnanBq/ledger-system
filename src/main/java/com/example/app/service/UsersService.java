package com.example.app.service;

import com.example.app.model.dto.accountsDto.AccountBasic;
import com.example.app.model.dto.usersDto.CreateUserRequest;
import com.example.app.model.dto.usersDto.UserBasic;
import com.example.app.model.entity.Users;
import com.example.app.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final AccountsService accountsService;

    public UsersService(UsersRepository userRepository, AccountsService accountsService) {
        this.usersRepository = userRepository;
        this.accountsService = accountsService;
    }

    public UserBasic addUser(CreateUserRequest user) {
        Users userEntity = new Users();
        userEntity.setName(user.name());
        userEntity.setEmail(user.email());
        Users userRecord = usersRepository.save(userEntity);
        AccountBasic accountRecord = accountsService.addAccount(userRecord, user.accountType());
        List<AccountBasic> accountDtos = List.of(accountRecord);

        return new UserBasic(userRecord.getId(), userRecord.getName(), userRecord.getEmail(), userRecord.getCreatedAt(),
                accountDtos);
    }

    public UserBasic getUserById(Long id) {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // get all user's accounts
        List<AccountBasic> accountBasics = user.getAccounts()
                .stream()
                .map(account -> new AccountBasic(
                        account.getId(),
                        account.getAccountType(),
                        account.getCurrency(),
                        account.getActive(),
                        account.getCreatedAt(),
                        account.getUpdatedAt(),
                        account.getUser().getId()))
                .toList();

        return new UserBasic(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                accountBasics);
    }

    public Page<UserBasic> getAllUsers(Pageable pageable) {

        Page<Users> usersPage = usersRepository.findAll(pageable);

        return usersPage.map(user -> {

            List<AccountBasic> accountBasics = user.getAccounts()
                    .stream()
                    .map(account -> new AccountBasic(
                            account.getId(),
                            account.getAccountType(),
                            account.getCurrency(),
                            account.getActive(),
                            account.getCreatedAt(),
                            account.getUpdatedAt(),
                            account.getUser().getId()))
                    .toList();

            return new UserBasic(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    accountBasics);
        });
    }
}
