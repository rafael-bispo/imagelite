package br.com.estudo.imageliteapi.domain.service;

import br.com.estudo.imageliteapi.domain.entity.Image;
import br.com.estudo.imageliteapi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image save(Image image);

    //O optional serve paa dizer que o banco pode retornar ou não uma imagem para o id usado na requisição
    Optional<Image> getById(String id);

    List<Image> search(ImageExtension extension, String query);
}
