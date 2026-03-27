package br.com.estudo.imageliteapi.application.users;

import br.com.estudo.imageliteapi.domain.entity.User;
import br.com.estudo.imageliteapi.domain.exception.DuplicatedTupleException;
import br.com.estudo.imageliteapi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto){
        try {
            User user = userMapper.mapTouser(dto);
            userService.save(user);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DuplicatedTupleException e){
            //enviando a mensagem de exceção pro front usando body
            Map<String, String> jsonResultado = Map.of("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResultado);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity authenticate(@RequestBody CredentialsDTO credentialsDTO){
        var token = userService.authenticate(credentialsDTO.getEmail(), credentialsDTO.getPassword());
        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(token);
    }
}
