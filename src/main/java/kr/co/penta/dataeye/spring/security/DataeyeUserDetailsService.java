package kr.co.penta.dataeye.spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

public class DataeyeUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
            throws UsernameNotFoundException {

        String userName = (String) token.getPrincipal();
        Object credentials = token.getCredentials();

        List<String> string_authorities = new ArrayList<>();
        List<GrantedAuthority> authoritiesx = new ArrayList<GrantedAuthority>();
        for (String authority : string_authorities) {
            authoritiesx.add(new SimpleGrantedAuthority(authority));
        }
        User user = new User(userName, "N/A", authoritiesx);

        return user;
    }

}