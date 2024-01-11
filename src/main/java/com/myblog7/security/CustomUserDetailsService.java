package com.myblog7.security;

import com.myblog7.entity.Role;

import com.myblog7.entity.User;
import com.myblog7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user=userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(
                ()->new UsernameNotFoundException("user not found with username or email"+usernameOrEmail)
        );
        System.out.println("dbuser"+user);
        System.out.println(new org.springframework.security.core.userdetails.User(user.getEmail()
                ,user.getPassword(),mapRoleToAuthorities(user.getRoles())));
        return new org.springframework.security.core.userdetails.User(user.getEmail()
                ,user.getPassword(),mapRoleToAuthorities(user.getRoles()));

    }
    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Set<Role> roles){

        List<SimpleGrantedAuthority> collect = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }
}
