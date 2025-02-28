package com.example.schoolapp.security;

import com.example.schoolapp.model.Person;
import com.example.schoolapp.model.Roles;
import com.example.schoolapp.repository.PersonRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Profile("prod")
public class UsernamePwdAuthProvider implements AuthenticationProvider {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public UsernamePwdAuthProvider(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Person person = personRepository.findByEmail(email);

        if (person != null && person.getPersonId() > 0 && passwordEncoder.matches(password, person.getPwd())) {
            return new UsernamePasswordAuthenticationToken(email, password, getGrantedAuthorities(person.getRole()));
        } else {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }


    private List<GrantedAuthority> getGrantedAuthorities(Roles role) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        return grantedAuthorities;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
