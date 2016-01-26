package net.bevia.dondereciclo.service.impl;

import net.bevia.dondereciclo.service.ContenedorService;
import net.bevia.dondereciclo.domain.Contenedor;
import net.bevia.dondereciclo.repository.ContenedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Contenedor.
 */
@Service
@Transactional
public class ContenedorServiceImpl implements ContenedorService{

    private final Logger log = LoggerFactory.getLogger(ContenedorServiceImpl.class);
    
    @Inject
    private ContenedorRepository contenedorRepository;
    
    /**
     * Save a contenedor.
     * @return the persisted entity
     */
    public Contenedor save(Contenedor contenedor) {
        log.debug("Request to save Contenedor : {}", contenedor);
        Contenedor result = contenedorRepository.save(contenedor);
        return result;
    }

    /**
     *  get all the contenedors.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Contenedor> findAll() {
        log.debug("Request to get all Contenedors");
        List<Contenedor> result = contenedorRepository.findAll();
        return result;
    }

    /**
     *  get one contenedor by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Contenedor findOne(Long id) {
        log.debug("Request to get Contenedor : {}", id);
        Contenedor contenedor = contenedorRepository.findOne(id);
        return contenedor;
    }

    /**
     *  delete the  contenedor by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contenedor : {}", id);
        contenedorRepository.delete(id);
    }
}
