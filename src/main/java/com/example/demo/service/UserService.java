package com.example.demo.service;

import com.example.demo.config.TokenService;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.dto.UserTokenDTO;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public UserTokenDTO create(UserDTO userDTO) throws ServiceException {
        User user = new User();
        user.setDisplayName(userDTO.getDisplayName());
        user.setImage(userDTO.getImage());
        if(!userDTO.getEmail().contains("@") || userDTO.getEmail().indexOf("@") == 0 || userDTO.getEmail().indexOf("@") == userDTO.getEmail().length()-1)
            throw new ServiceException(HttpStatus.BAD_REQUEST,"User","must be a valid email");
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent())
            throw new ServiceException(HttpStatus.CONFLICT,"User","usuário já existente");
        user.setEmail(userDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        if(roleRepository.findById(1L).isPresent()) {
            user.setRoles(roleRepository.findById(1L).get());
        }
        user.setDateCreated(LocalDateTime.now());
        user.setLastUpdate(LocalDateTime.now());
        userRepository.save(user);

        //Primeiro Login

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.createToken(authentication);
        TokenDTO tokenDTO = new TokenDTO(token,"Bearer");
        return new UserTokenDTO(user.getId(),tokenDTO);
    }

    public UserDetailsDTO readOne(Long id) throws ServiceException {
        if(userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            return new UserDetailsDTO(user.getId(),user.getDisplayName(),user.getEmail(),user.getImage());
        } else {
            throw new ServiceException(HttpStatus.NOT_FOUND,"User","Usuário não existe");
        }
    }

    public List<UserDetailsDTO> readAll() {
        List<User> users = userRepository.findAll();
        List<UserDetailsDTO> userDetailsDTOList = new ArrayList<>();
        for (User u:
             users) {
            userDetailsDTOList.add(new UserDetailsDTO(u.getId(),u.getDisplayName(),u.getEmail(),u.getImage()));
        }
        return userDetailsDTOList;
    }

    public void delete(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        Long userId = tokenService.getIdUsuario(token);
        User user = new User();
        if(userRepository.findById(userId).isPresent()) {
            user = userRepository.findById(userId).get();
        }
        userRepository.delete(user);
    }
}