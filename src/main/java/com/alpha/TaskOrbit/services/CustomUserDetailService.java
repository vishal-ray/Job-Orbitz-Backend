package com.alpha.TaskOrbit.services;

import com.alpha.TaskOrbit.modules.User;
import com.alpha.TaskOrbit.repositories.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService,ICustomUserDetailService {
    @Autowired
    public RepoUser repoUser;
    //  I am keeping the emailID as Username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // User loading from Database
        return repoUser.findByEmailID(username).orElseThrow(() -> new RuntimeException("User not found!!"));
    }
    public Long loadUserIdByUsername(String username) throws UsernameNotFoundException{
        User user = repoUser.findByEmailID(username).orElseThrow(() -> new RuntimeException("User not found!!"));
        return user.getUserId();
    }
}