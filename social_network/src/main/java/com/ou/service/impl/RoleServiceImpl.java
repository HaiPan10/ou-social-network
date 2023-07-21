package com.ou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ou.pojo.Role;
import com.ou.repository.interfaces.RoleRepository;
import com.ou.service.interfaces.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role retrieve(Integer id) {
        return roleRepository.retrieve(id);
    }
    
}
