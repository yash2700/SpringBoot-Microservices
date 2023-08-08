package com.ekart.Account.Dtos;

import com.ekart.Account.Entity.Account;
import com.ekart.Account.Entity.Address;
import com.ekart.Account.enums.AccountType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private String emailId;
    private String name;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;
    private List<Address> addresses;
    public static AccountResponseDto mapToResponseDto(Account account){
        return AccountResponseDto.builder()
                .emailId(account.getEmailId())
                .name(account.getName())
                .password(account.getPassword())
                .accountType(account.getAccountType())
                .build();
    }
}
