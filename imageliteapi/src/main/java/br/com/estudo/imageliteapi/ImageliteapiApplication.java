package br.com.estudo.imageliteapi;

import br.com.estudo.imageliteapi.domain.entity.Image;
import br.com.estudo.imageliteapi.domain.enums.ImageExtension;
import br.com.estudo.imageliteapi.infra.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //precisa disso para autorizar o jpaAuditing , e assim ele pegar a adata no Image.java
public class ImageliteapiApplication {

	//CommandLineRunner é u roteador da linha de comandos ele coloca pra rodar comandos ao iniciar o aplicativo
	/*@Bean
	public CommandLineRunner commandLineRunner(@Autowired ImageRepository repository){
		return args -> {
			Image image = Image.builder()
					.extension(ImageExtension.PNG)
					.name("Minha Imagem")
					.tags("teste")
					.size(1234L)
					.build();

			repository.save(image);
		};
	}*/

	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
