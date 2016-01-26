package net.bevia.dondereciclo.web.rest;

import net.bevia.dondereciclo.Application;
import net.bevia.dondereciclo.domain.Contenedor;
import net.bevia.dondereciclo.repository.ContenedorRepository;
import net.bevia.dondereciclo.service.ContenedorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContenedorResource REST controller.
 *
 * @see ContenedorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ContenedorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final BigDecimal DEFAULT_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LON = new BigDecimal(1);
    private static final BigDecimal UPDATED_LON = new BigDecimal(2);

    @Inject
    private ContenedorRepository contenedorRepository;

    @Inject
    private ContenedorService contenedorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContenedorMockMvc;

    private Contenedor contenedor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContenedorResource contenedorResource = new ContenedorResource();
        ReflectionTestUtils.setField(contenedorResource, "contenedorService", contenedorService);
        this.restContenedorMockMvc = MockMvcBuilders.standaloneSetup(contenedorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contenedor = new Contenedor();
        contenedor.setName(DEFAULT_NAME);
        contenedor.setLat(DEFAULT_LAT);
        contenedor.setLon(DEFAULT_LON);
    }

    @Test
    @Transactional
    public void createContenedor() throws Exception {
        int databaseSizeBeforeCreate = contenedorRepository.findAll().size();

        // Create the Contenedor

        restContenedorMockMvc.perform(post("/api/contenedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contenedor)))
                .andExpect(status().isCreated());

        // Validate the Contenedor in the database
        List<Contenedor> contenedors = contenedorRepository.findAll();
        assertThat(contenedors).hasSize(databaseSizeBeforeCreate + 1);
        Contenedor testContenedor = contenedors.get(contenedors.size() - 1);
        assertThat(testContenedor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContenedor.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testContenedor.getLon()).isEqualTo(DEFAULT_LON);
    }

    @Test
    @Transactional
    public void getAllContenedors() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get all the contenedors
        restContenedorMockMvc.perform(get("/api/contenedors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contenedor.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
                .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.intValue())));
    }

    @Test
    @Transactional
    public void getContenedor() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

        // Get the contenedor
        restContenedorMockMvc.perform(get("/api/contenedors/{id}", contenedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contenedor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContenedor() throws Exception {
        // Get the contenedor
        restContenedorMockMvc.perform(get("/api/contenedors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContenedor() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

		int databaseSizeBeforeUpdate = contenedorRepository.findAll().size();

        // Update the contenedor
        contenedor.setName(UPDATED_NAME);
        contenedor.setLat(UPDATED_LAT);
        contenedor.setLon(UPDATED_LON);

        restContenedorMockMvc.perform(put("/api/contenedors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contenedor)))
                .andExpect(status().isOk());

        // Validate the Contenedor in the database
        List<Contenedor> contenedors = contenedorRepository.findAll();
        assertThat(contenedors).hasSize(databaseSizeBeforeUpdate);
        Contenedor testContenedor = contenedors.get(contenedors.size() - 1);
        assertThat(testContenedor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContenedor.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testContenedor.getLon()).isEqualTo(UPDATED_LON);
    }

    @Test
    @Transactional
    public void deleteContenedor() throws Exception {
        // Initialize the database
        contenedorRepository.saveAndFlush(contenedor);

		int databaseSizeBeforeDelete = contenedorRepository.findAll().size();

        // Get the contenedor
        restContenedorMockMvc.perform(delete("/api/contenedors/{id}", contenedor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contenedor> contenedors = contenedorRepository.findAll();
        assertThat(contenedors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
