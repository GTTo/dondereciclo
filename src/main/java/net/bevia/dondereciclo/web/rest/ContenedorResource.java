package net.bevia.dondereciclo.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.bevia.dondereciclo.domain.Contenedor;
import net.bevia.dondereciclo.service.ContenedorService;
import net.bevia.dondereciclo.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Contenedor.
 */
@RestController
@RequestMapping("/api")
public class ContenedorResource {

    private final Logger log = LoggerFactory.getLogger(ContenedorResource.class);
        
    @Inject
    private ContenedorService contenedorService;
    
    /**
     * POST  /contenedors -> Create a new contenedor.
     */
    @RequestMapping(value = "/contenedors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contenedor> createContenedor(@RequestBody Contenedor contenedor) throws URISyntaxException {
        log.debug("REST request to save Contenedor : {}", contenedor);
        if (contenedor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contenedor", "idexists", "A new contenedor cannot already have an ID")).body(null);
        }
        Contenedor result = contenedorService.save(contenedor);
        return ResponseEntity.created(new URI("/api/contenedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contenedor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contenedors -> Updates an existing contenedor.
     */
    @RequestMapping(value = "/contenedors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contenedor> updateContenedor(@RequestBody Contenedor contenedor) throws URISyntaxException {
        log.debug("REST request to update Contenedor : {}", contenedor);
        if (contenedor.getId() == null) {
            return createContenedor(contenedor);
        }
        Contenedor result = contenedorService.save(contenedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contenedor", contenedor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contenedors -> get all the contenedors.
     */
    @RequestMapping(value = "/contenedors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contenedor> getAllContenedors() {
        log.debug("REST request to get all Contenedors");
        return contenedorService.findAll();
            }

    /**
     * GET  /contenedors/:id -> get the "id" contenedor.
     */
    @RequestMapping(value = "/contenedors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contenedor> getContenedor(@PathVariable Long id) {
        log.debug("REST request to get Contenedor : {}", id);
        Contenedor contenedor = contenedorService.findOne(id);
        return Optional.ofNullable(contenedor)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contenedors/:id -> delete the "id" contenedor.
     */
    @RequestMapping(value = "/contenedors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContenedor(@PathVariable Long id) {
        log.debug("REST request to delete Contenedor : {}", id);
        contenedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contenedor", id.toString())).build();
    }
}
