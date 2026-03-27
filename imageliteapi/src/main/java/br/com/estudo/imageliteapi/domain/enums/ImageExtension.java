package br.com.estudo.imageliteapi.domain.enums;

import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.Arrays;

public enum ImageExtension {

    //O Spring possui uma Enum própria onde define os tipos de mídia, pertencente ao org.spring.http
    //Nele são definidos tipo e subtipo, como "image" e "jpeg", ou ainda multpart e form-data

    //MediaType usa o construtor  que está aí embaixo para pegar o vvalor de MediaType.IMAGE_PNG e etc, para que ao ser acionada a enum, o valor que sej aregistrado é aquele padronizado pelo MediaTYpe
    PNG(MediaType.IMAGE_PNG),
    JPEG(MediaType.IMAGE_JPEG),
    JPG(MediaType.IMAGE_JPEG),
    GIF(MediaType.IMAGE_GIF);

    @Getter //gera um getter pra essa variável
    private MediaType mediaType;

    ImageExtension(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    //Metodo que irá ver o valor da extensão da imagem pa rapoder registrar no BD

    public static ImageExtension valueOf(MediaType mediaType){
        // o código abaixo transforma as enums (values() representa os valores das enums) em um array
        //com a expressão lâmbda itera sobre o array filtrando todos que sejam iguais ao mediaType passado como parâmetro, e após a filtragem, pega o primeiro valor, se e nanhum ocrrespondência for encontrada e o resultado for vazio, retorna null
        return Arrays.stream(values())
                .filter(ie -> ie.mediaType.equals(mediaType))
                .findFirst()
                .orElse(null);
    }
    public static ImageExtension ofName(String name){
        return Arrays.stream(values())
                .filter(ie -> ie.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
