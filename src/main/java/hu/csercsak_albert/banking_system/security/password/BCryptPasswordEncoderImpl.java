package hu.csercsak_albert.banking_system.security.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderImpl implements PasswordEncoder{

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public BCryptPasswordEncoderImpl(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(CharSequence rawPw) {
        return encoder.encode(rawPw);
    }

    @Override
    public boolean matches(CharSequence rawPw, String encodedPw) {
        return encoder.matches(rawPw, encodedPw);
    }
}
