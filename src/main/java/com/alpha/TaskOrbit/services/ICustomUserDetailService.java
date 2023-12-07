package com.alpha.TaskOrbit.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICustomUserDetailService {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public Long loadUserIdByUsername(String username) throws UsernameNotFoundException;
}
