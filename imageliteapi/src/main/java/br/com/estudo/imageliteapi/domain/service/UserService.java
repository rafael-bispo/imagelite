package br.com.estudo.imageliteapi.domain.service;

import br.com.estudo.imageliteapi.domain.AccessToken;
import br.com.estudo.imageliteapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    User save(User user);
    AccessToken authenticate(String email, String password);
}
