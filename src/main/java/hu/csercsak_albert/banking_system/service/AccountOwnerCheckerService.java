package hu.csercsak_albert.banking_system.service;

public interface AccountOwnerCheckerService {

    boolean check(Long accountNumber, String username);
}
