package br.com.estudo.imageliteapi.application.images;

import br.com.estudo.imageliteapi.domain.entity.Image;
import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ImageMapper {

    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {

        //implementação do tratamento da imagem e seus dados para salvar no banco
        Image image = Image.builder()
                .name(name)
                .tags(String.join(",", tags)) //o join faz com que o array de strings recebido seja salvo como uma única string, porém com um delimitador específico, como , ou . por exemplo
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes()) //transformar em bytes é o caminho para guardar os dados, visto que não tem um tipo de arquivo específico para salvar arquivos como o varcher e o interger no postgres, então o melhor caminho é transformar em bytes. Isso descarta todos os metadados, por isso os salvamos a parte (NOME, TAMANHO, TAGS)
                .build();

        return image;
    }

    //metodo que transforma imagem em DTO
    public ImageDTO imageToDTO(Image image, String url){
        return ImageDTO.builder()
                .url(url)
                .extension(image.getExtension().name())
                .name(image.getName())
                .size(image.getSize())
                .uploadDate(image.getUploadDate().toLocalDate())
                .build();
    }
}
