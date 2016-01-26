package net.bevia.dondereciclo.service;

import net.bevia.dondereciclo.domain.Contenedor;

import java.util.List;

/**
 * Service Interface for managing Contenedor.
 */
public interface ContenedorService {

    /**
     * Save a contenedor.
     * @return the persisted entity
     */
    public Contenedor save(Contenedor contenedor);

    /**
     *  get all the contenedors.
     *  @return the list of entities
     */
    public List<Contenedor> findAll();

    /**
     *  get the "id" contenedor.
     *  @return the entity
     */
    public Contenedor findOne(Long id);

    /**
     *  delete the "id" contenedor.
     */
    public void delete(Long id);
}
