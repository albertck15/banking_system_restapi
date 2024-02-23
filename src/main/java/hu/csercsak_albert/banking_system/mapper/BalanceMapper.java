package hu.csercsak_albert.banking_system.mapper;

import hu.csercsak_albert.banking_system.dto.BalanceDto;
import hu.csercsak_albert.banking_system.entity.Balance;

public class BalanceMapper {

    public static BalanceDto mapToBalanceDto(Balance balance) {
        return BalanceDto.builder()
                .id(balance.getId())
                .balance(balance.getBalance())
                .build();
    }

    public static Balance mapToBalance(BalanceDto balanceDto) {
        return Balance.builder()
                .id(balanceDto.getId())
                .balance(balanceDto.getBalance())
                .build();
    }
}
