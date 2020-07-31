package com.github.savitoh.centralerroapi.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
public class UserResource {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResource(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponsePayload> getUserById(@PathVariable("id") Integer id) {
        return userRepository.findById(id)
            .map(user -> ResponseEntity.ok().body(user.toDetalheUserResponsePayload()))
            .orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody @Valid NovoUserRequestPayload novoUserRequestPayload) {
        User user = novoUserRequestPayload.toUser(passwordEncoder);
        userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
        return ResponseEntity.created(location).build();
    }


    
}