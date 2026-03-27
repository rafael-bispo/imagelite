package br.com.estudo.imageliteapi.domain.service;

import br.com.estudo.imageliteapi.domain.entity.Image;
import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
import br.com.estudo.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository repository;

    @Override
    @Transactional //faz com que uma transação seja aberta com o BD, apenas para escrita
    public Image save(Image image) {
        return repository.save(image);
    }

    @Override
    public Optional<Image> getById(String id){
        return repository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return repository.findByExtensionAndNameOrTagsLike(extension, query);
    }
}
