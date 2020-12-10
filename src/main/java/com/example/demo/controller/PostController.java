package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<ResponsePostDTO> createPost(@RequestBody @Valid PostDTO postDTO,
                                                      UriComponentsBuilder uriComponentsBuilder,
                                                      HttpServletRequest request) {
        PostDetailsDTO postDetailsDTO = postService.create(postDTO,request);
        URI uri = uriComponentsBuilder.path("/post/{id}").buildAndExpand(postDetailsDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(new ResponsePostDTO(postDetailsDTO.getTitle(),postDetailsDTO.getContent(),postDetailsDTO.getUserId()));
    }

    @GetMapping
    public ResponseEntity<List<ResponsePostDetailsDTO>> listAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostDetailsDTO> findOne(@PathVariable Long id) throws ServiceException {
        return ResponseEntity.ok(postService.findOne(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponsePostDetailsDTO>> findTitleOrContent(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(postService.findTitleOrContent(q));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePostDTO> updatePost(@PathVariable Long id,
                                                      @RequestBody @Valid PostDTO postDTO,
                                                      HttpServletRequest request) throws ServiceException {
        ResponsePostDTO responsePostDTO = postService.update(id,postDTO,request);
        return ResponseEntity.ok(responsePostDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        HttpServletRequest request) throws ServiceException {
        postService.delete(id,request);
        return ResponseEntity.noContent().build();
    }
}
