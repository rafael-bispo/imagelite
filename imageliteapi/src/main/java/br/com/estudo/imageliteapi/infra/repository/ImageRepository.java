package br.com.estudo.imageliteapi.infra.repository;

import br.com.estudo.imageliteapi.domain.entity.Image;
import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
import br.com.estudo.imageliteapi.infra.repository.specs.GenericSpecs;
import br.com.estudo.imageliteapi.infra.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static br.com.estudo.imageliteapi.infra.repository.specs.GenericSpecs.conjunction;
import static br.com.estudo.imageliteapi.infra.repository.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {

        //select * from image where 1=1 (esse 1=1 é chamado conjunção/conjunction
        //root representa a entidade image, se quiser acessar um dado ou metodo dele é com root
        //q representa a query que está sendo montada, neste caso select * from
        //criteriaBuilder são os critérios que estão sendo colocados com o where, and e similares
        //q é mais usado para joins e outros recursos

        //criando a specification do tipo image
        Specification<Image> spec = where(conjunction());

        if(extension != null){
            //add a query
            spec = spec.and(extensionEqual(extension));
        }

        //o metodo hasText verifica se não é nulo, se a string é vazio ou somente contém espaçod
        if(StringUtils.hasText(query)){


            //Specification.allOf é semelhante ao AND, ou seja, precisa que todos os critérios sejam atendidos
            //Specification.anyOf é semelhante ao OU, ou seja, se qualquer um dos critérios for atendido, ele é válido
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        //aqui ele será executado o findAll do JPA para encontrar todos que atendam aos critérios definidos na specification, por isso ela é passado como parãmetro
        return findAll(spec);
    }


}



//cÓDIGO ANTES DA REFATORAÇÃO
/**
 * package br.com.estudo.imageliteapi.infra.repository;
 *
 * import br.com.estudo.imageliteapi.domain.entity.Image;
 * import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
 * import org.springframework.data.jpa.domain.Specification;
 * import org.springframework.data.jpa.repository.JpaRepository;
 * import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 * import org.springframework.util.StringUtils;
 *
 * import java.util.List;
 *
 * public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
 *     default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
 *
 *         //select * from image where 1=1 (esse 1=1 é chamado conjunção/conjunction
 *         //root representa a entidade image, se quiser acessar um dado ou metodo dele é com root
 *         //q representa a query que está sendo montada, neste caso select * from
 *         //criteriaBuilder são os critérios que estão sendo colocados com o where, and e similares
 *         //q é mais usado para joins e outros recursos
 *         Specification<Image> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
 *
 *         //criando a specification do tipo image
 *         Specification<Image> spec = Specification.where(conjunction);
 *
 *         if(extension != null){
 *             //add a query
 *             Specification<Image> extensionEqual = (root, q, cb) -> cb.equal(root.get("extension"), extension);
 *             spec = spec.and(extensionEqual);
 *         }
 *
 *         //o metodo hasText verifica se não é nulo, se a string é vazio ou somente contém espaçod
 *         if(StringUtils.hasText(query)){
 *             Specification<Image> nameLike = (root, query1, cb) -> cb.like(cb.upper(root.get("name")), query.toUpperCase()) ;
 *             Specification<Image> tagsLike = (root, query1, cb) -> cb.like(cb.upper(root.get("tags")), query.toUpperCase());
 *
 *             //Specification.allOf é semelhante ao AND, ou seja, precisa que todos os critérios sejam atendidos
 *             //Specification.anyOf é semelhante ao OU, ou seja, se qualquer um dos critérios for atendido, ele é válido
 *             Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);
 *             spec = spec.and(nameOrTagsLike);
 *         }
 *
 *         //aqui ele será executado o findAll do JPA para encontrar todos que atendam aos critérios definidos na specification, por isso ela é passado como parãmetro
 *         return findAll(spec);
 *     }
 *
 *
 * }
 * */