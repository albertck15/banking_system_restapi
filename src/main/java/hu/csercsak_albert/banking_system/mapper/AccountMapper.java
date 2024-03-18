package hu.csercsak_albert.banking_system.mapper;

import hu.csercsak_albert.banking_system.dto.AccountDto;
import hu.csercsak_albert.banking_system.entity.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto) {
        return Account.builder()
                .user(UserMapper.mapToUser(accountDto.getUser()))
                .accountNumber(accountDto.getAccountNumber())
                .balance(accountDto.getBalance())
                .build();
    }

    public static AccountDto mapToAccountDto(Account account) {
        return AccountDto.builder()
                .user(UserMapper.mapToUserDto(account.getUser()))
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();
    }

    public static AccountDto mapToAccountDtoNotOwned(Account account) {
        return AccountDto.builder()
                .user(null)
                .accountNumber(account.getAccountNumber())
                .balance(null)
                .build();
    }
}
