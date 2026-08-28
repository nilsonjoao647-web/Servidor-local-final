package com.labanta.servidorlocal.repository;
import com.labanta.servidorlocal.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface    UtilizadorRepository extends JpaRepository<Utilizador, Long> {
    Optional<Utilizador> findByUsername(String username);


}
