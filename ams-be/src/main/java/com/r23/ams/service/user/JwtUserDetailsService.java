package com.r23.ams.service.user;

import com.r23.ams.models.entities.AppUser;
import com.r23.ams.models.entities.SessionUser;
import com.r23.ams.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private AppUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AppUser> usr = userRepository.findByEmail(username);
        if(usr.size()>0) {
            return new SessionUser(usr.get(0));// new User(usr.get(0).getEmail(), usr.get(0).getPassword(),new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
