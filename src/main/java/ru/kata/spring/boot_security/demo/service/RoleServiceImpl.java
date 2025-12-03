package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
