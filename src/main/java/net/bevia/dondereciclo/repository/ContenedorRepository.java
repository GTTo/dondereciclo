package net.bevia.dondereciclo.repository;

import net.bevia.dondereciclo.domain.Contenedor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contenedor entity.
 */
public interface ContenedorRepository extends JpaRepository<Contenedor,Long> {

    @Query("select contenedor from Contenedor contenedor where contenedor.user.login = ?#{principal.username}")
    List<Contenedor> findByUserIsCurrentUser();

}
