package com.expensemanager.expensemanager.security;

import com.expensemanager.expensemanager.mapper.RoleMapper;
import com.expensemanager.expensemanager.model.UserEntity;
import com.expensemanager.expensemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleMapper roleMapper;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        return new User(user.getEmail(), user.getPassword(), roleMapper.mapRolesToAuthorities(user.getRoles()));
    }

}
