package com.example.demo.controller;

import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.dto.UserTokenDTO;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<TokenDTO> createUser(@RequestBody @Valid UserDTO userDTO,
                                               UriComponentsBuilder uriComponentsBuilder) throws ServiceException {
        UserTokenDTO userTokenDTO= userService.create(userDTO);
        URI uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(userTokenDTO.getUserId()).toUri();
        return ResponseEntity.created(uri).body(userTokenDTO.getTokenDTO());
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> listAllUsers() {
        return ResponseEntity.ok(userService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> findUser(@PathVariable Long id) throws ServiceException {
        return ResponseEntity.ok(userService.readOne(id));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> removeUser(HttpServletRequest request) {
        userService.delete(request);
        return ResponseEntity.noContent().build();
    }
}
