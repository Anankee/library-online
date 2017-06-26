package pl.biblioteka.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.biblioteka.dao.UserDao;
import pl.biblioteka.entity.User;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User findByLogin = userDao.findByLogin(login);
		Set<GrantedAuthority> role = new HashSet<>();		
		return new org.springframework.security.core.userdetails.User(findByLogin.getLogin(), findByLogin.getPassword(), role);
	}

}
