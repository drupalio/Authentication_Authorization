package com.melhamra.api.services;

import com.melhamra.api.dtos.UserDto;
import com.melhamra.api.entities.RoleEntity;
import com.melhamra.api.entities.UserEntity;
import com.melhamra.api.repositories.RoleRepository;
import com.melhamra.api.repositories.UserRepository;
import com.melhamra.api.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Utils utils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        userEntity.orElseThrow(
                () -> new UsernameNotFoundException("User not found with this email: " + email));
        UserEntity user = userEntity.get();

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<RoleEntity> roles = user.getRoles();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        return new User(user.getEmail(), user.getEncryptedPassword(), authorities);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();

        Optional<UserEntity> userEntity = userRepository.findByEmail(userDto.getEmail());
        userEntity.ifPresent( user -> {throw new RuntimeException("User already exits with this email !");});

        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setUserId(utils.generateStringId(10));
        UserEntity user = mapper.map(userDto, UserEntity.class);

        Set<RoleEntity> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
             roles.add(roleRepository.findByRole(role.getRole()));
        });
        user.getRoles().clear();
        roles.forEach(role -> {
            user.getRoles().add(role);
        });

        UserEntity createdUser = userRepository.save(user);
        return mapper.map(createdUser, UserDto.class);
    }

    public UserDto getUserByEmail(String email){
        Optional<UserEntity> user = userRepository.findByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found with this email: " + email));
        UserDto userDto = new ModelMapper().map(user.get(), UserDto.class);
        return userDto;
    }

}
