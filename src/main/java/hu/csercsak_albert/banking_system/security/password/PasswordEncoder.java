package hu.csercsak_albert.banking_system.security.password;

public interface PasswordEncoder {

    String encode(CharSequence rawPw);

    boolean matches(CharSequence rawPw, String encodedPw);
}
