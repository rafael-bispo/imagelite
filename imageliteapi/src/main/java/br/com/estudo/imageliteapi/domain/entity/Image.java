package br.com.estudo.imageliteapi.domain.entity;

import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    //o uuid é universal unic id, cria um hash para poder identificar unicamente
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; //por ser hash observe que é uma string e não int

    @Column
    private Long size;
    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)//Para que ele gaurde as opções da enum como string e não apenass a posição (como acontece com o Enum.Type.ORDINAL
    private ImageExtension extension;
    @Column
    @CreatedDate
    private LocalDateTime uploadDate;
    @Column
    private String tags;
    @Column
    @Lob // é utilizado para poder salvar um arquivo no banco de dados
    private  byte[] file;

    public String getFileName(){

        return getName().concat(".").concat(getExtension().name());
    }

}
