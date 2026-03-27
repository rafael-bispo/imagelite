package br.com.estudo.imageliteapi.application.images;

import br.com.estudo.imageliteapi.domain.entity.Image;
import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
import br.com.estudo.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Nested;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.text.Collator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor //cria os construtores dos argumentos requeridos
//@CrossOrigin("*") foi solução paliativa para liberar acesso geral para o momento do curso. Nunca usar na prática/produção
public class ImagesController {

    private final ImageService service;
    private final ImageMapper mapper;
    //comentário apenas para subir o arquivo no github e ver se está tudo ok
    //postmapping é a rota via post para realizar o salvamento da imagem
    @PostMapping
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("tags") List<String> tags
            ) throws IOException {
      /*  log.info("Dados da imagem recebida: \nName: {}\nNome original da imagem: {}\nSize: {}\nTags: {}", name, file.getOriginalFilename(), file.getSize(), tags);*/

        Image image = mapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);
        URI imageUri = buildImageURL(savedImage);

        return ResponseEntity.created(imageUri).build();
    }

    /**
     * No getmapping eu defino o que acontecerá quando for realizada
     * uma requisição get para o controller. Abaixo "{id}" indica
     * o caminho após a raiz, que nesse caso será o id da imagem.
     * */
    @GetMapping("{id}")
    //Path variable indica que a variável receberá em si um trecho do caminho, se ela tiver o mesmo nome do caminho do getmapping não precisa usar ("id"), deixei neste caso apenas pra ilustrar.
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){

        var possibleImage = service.getById(id);
        if(possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        //pega o que tem dento do possibleImage
        var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();

        //define no header o tipo de conteudo (tipo de imagem) que será a resposta da requisição
        headers.setContentType(image.getExtension().getMediaType());

        //pega o tmanho a imagem do banco e seta no cabeçalho
        headers.setContentLength(image.getSize());

        //pega o nome do arquivo e seta no cabeçalho
        //aqui estou seguindo o curso, mas o correto seria chamar só contentDisposition, colocar o nome uma vez só e resolver o inline ou formdata via metodo
        headers.setContentDispositionFormData("inline;filename=\"" + image.getFileName() + "\"", image.getFileName());

        return new ResponseEntity<byte []>(image.getFile(), headers, HttpStatus.OK);
    }

    /**
     * O metodo fará o seguinte:
     *  - requisição get será feita direto na raiz: localhost:8080/v1/images
     *  - e os parâmetros que serão passados na requisição é: extension=PNG&query=Nature
     *  - logo, caminho completo da requisição da pesquisa será: localhost:8080/v1/images?extension=PNG&query=Nature
     * */
    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
            @RequestParam(value = "query", required = false) String query){

        var result = service.search(ImageExtension.ofName(extension), query);

        var images = result.stream().map(image -> {
            var url = buildImageURL(image);
            return mapper.imageToDTO(image, url.toString());
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images);
    }

    //mtodo para construir a url da imagem
    private URI buildImageURL(Image image){

        //Cria a URI com o ID da Imagem
        String imagePath = "/" +image.getId();

        //Reponde a requisição atual enviando o caminho da imagem apra acessá-la
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
    }
}
