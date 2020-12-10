package com.example.demo.service;

import com.example.demo.config.TokenService;
import com.example.demo.dto.*;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    public PostDetailsDTO create(PostDTO postDTO, HttpServletRequest request) {
        User user = getUserByToken(request);
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user);
        post.setPublished(LocalDateTime.now());
        post.setUpdated(post.getPublished());
        postRepository.save(post);
        return new PostDetailsDTO(post.getId(),post.getUser().getId(),post.getTitle(),post.getContent());
    }

    public List<ResponsePostDetailsDTO> findAll() {
        List<ResponsePostDetailsDTO> listPostDTOS = new ArrayList<>();
        List<Post> listPosts= postRepository.findAll();
        for (Post p:
             listPosts) {

            listPostDTOS.add(new ResponsePostDetailsDTO(p.getId(),p.getTitle(),p.getContent(),p.getPublished(),p.getUpdated(),
                    new UserDetailsDTO(p.getUser().getId(),p.getUser().getDisplayName(),p.getUser().getEmail(),p.getUser().getImage())));
        }
        return listPostDTOS;
    }

    public ResponsePostDetailsDTO findOne(Long id) throws ServiceException {
        Post post;
        if(postRepository.findById(id).isPresent()) {
            post = postRepository.findById(id).get();
        } else {
            throw new ServiceException(HttpStatus.NOT_FOUND,"Post","Post não existe");
        }
        return new ResponsePostDetailsDTO(post.getId(),post.getTitle(),post.getContent(),post.getPublished(),post.getUpdated(),
                new UserDetailsDTO(post.getUser().getId(),post.getUser().getDisplayName(),post.getUser().getEmail(),post.getUser().getImage()));
    }

    public ResponsePostDTO update(Long id, PostDTO postDTO, HttpServletRequest request) throws ServiceException {
        User user = getUserByToken(request);
        Post post = new Post();
        if(postRepository.findById(id).isPresent()) {
            post = postRepository.findById(id).get();
        }
        if(post.getUser() != user) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED,"User","Usuário não autorizado");
        }
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUpdated(LocalDateTime.now());
        postRepository.save(post);
        return new ResponsePostDTO(post.getTitle(),post.getContent(),post.getUser().getId());
    }

    private User getUserByToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        Long userId = tokenService.getIdUsuario(token);
        User user = new User();
        if(userRepository.findById(userId).isPresent()) {
            user = userRepository.findById(userId).get();
        }
        return user;
    }

    public List<ResponsePostDetailsDTO> findTitleOrContent(String q) {
        String busca = "%" + q + "%";
        List<Post> titleListPost = postRepository.findByTitle(busca);
        List<Post> contentListPost = postRepository.findByContent(busca);
        List<ResponsePostDetailsDTO> responsePostDetailsDTO = new ArrayList<>();
        for (Post p:
             titleListPost) {
            responsePostDetailsDTO.add(new ResponsePostDetailsDTO(p.getId(),p.getTitle(),p.getContent(),p.getPublished(),p.getUpdated(),
                    new UserDetailsDTO(p.getUser().getId(),p.getUser().getDisplayName(),p.getUser().getEmail(),p.getUser().getImage())));
        }
        for (Post p:
                contentListPost) {
            responsePostDetailsDTO.add(new ResponsePostDetailsDTO(p.getId(),p.getTitle(),p.getContent(),p.getPublished(),p.getUpdated(),
                    new UserDetailsDTO(p.getUser().getId(),p.getUser().getDisplayName(),p.getUser().getEmail(),p.getUser().getImage())));
        }
        return responsePostDetailsDTO;
    }

    public void delete(Long id, HttpServletRequest request) throws ServiceException {
        User user = getUserByToken(request);
        Post post;
        if(postRepository.findById(id).isPresent()) {
            post = postRepository.findById(id).get();
        } else {
            throw new ServiceException(HttpStatus.NOT_FOUND,"Post","Post não existe");
        }
        if(post.getUser() != user) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED,"User","Uuário não autorizado");
        }
        postRepository.delete(post);
    }
}
