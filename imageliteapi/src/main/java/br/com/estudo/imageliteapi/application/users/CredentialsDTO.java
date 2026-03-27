package br.com.estudo.imageliteapi.application.users;

import lombok.Data;

@Data
public class CredentialsDTO {
    private String email;
    private String password;
}
