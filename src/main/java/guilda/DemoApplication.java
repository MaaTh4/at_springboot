package guilda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import guilda.seguranca.dominio.Organizacao;
import guilda.seguranca.dominio.Usuario;
import guilda.seguranca.repositorio.OrganizacaoRepository;
import guilda.seguranca.repositorio.UsuarioRepository;
import java.time.OffsetDateTime;

import org.springframework.cache.annotation.EnableCaching;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("rankingMissoes");
    }

    @Bean
    public CommandLineRunner dataSeeder(OrganizacaoRepository orgRepo, UsuarioRepository userRepo) {
        return args -> {
            Organizacao org;
            if (orgRepo.count() == 0) {
                org = new Organizacao();
                org.setNome("Guilda Central");
                org.setAtivo(true);
                orgRepo.save(org);
            } else {
                org = orgRepo.findAll().get(0);
            }

            if (userRepo.count() == 0) {
                Usuario u = new Usuario();
                u.setNome("Sistema");
                u.setEmail("sys@guilda.com");
                u.setStatus("ATIVO");
                u.setOrganizacao(org);
                u.setCreatedAt(OffsetDateTime.now());
                userRepo.save(u);
            }
        };
    }
}
