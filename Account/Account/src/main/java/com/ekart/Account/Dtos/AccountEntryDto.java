package com.ekart.Account.Dtos;

import com.ekart.Account.Entity.Account;
import com.ekart.Account.enums.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntryDto {
    @NotBlank
    @Email(message = "Please enter a valid email id!")
    private String emailId;
    @NotBlank(message = "Please enter a valid name!")
    @Pattern(regexp = "^[a-zA-Z ]+$",message = "Name must contain only alphabets!")
    private String name;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{8,}$",message = "Password doesn't meet the required criteria. Please try again!")
    private String password;
    @NotNull(message = "Please enter a valid Account type!")
    private String accountType;

    public static Account mapToAccount(AccountEntryDto accountEntryDto){
        Account account= Account.builder()
                .accountType(AccountType.valueOf(accountEntryDto.getAccountType()))
                .emailId(accountEntryDto.getEmailId())
                .name(accountEntryDto.getName())
                .password(accountEntryDto.getPassword())
                .build();

        return account;
    }
}
