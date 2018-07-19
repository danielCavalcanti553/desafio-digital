package com.b2w.project.resource;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.b2w.project.ProjectApplication;
import com.b2w.project.domain.Planeta;
import com.b2w.project.repository.PlanetaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectApplication.class)
@WebAppConfiguration
public class PlanetaResourceTest {
	
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
	@Autowired
	private PlanetaRepository planetaRepository;
	
	private Planeta insertPlaneta;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
    
    //INÍCIO TESTES
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
		planetaRepository.deleteAll();
		Planeta p1 = new Planeta(null,"Terra","Temperate","Forests, mountains, lakes",0);
		Planeta p2 = new Planeta(null,"Marte","Arid","rock, desert, mountain",0);	
		planetaRepository.saveAll(Arrays.asList(p1,p2));
		
		Planeta p3 = new Planeta(null,"Mercurio","Arid","rock, desert, mountain",0);
		insertPlaneta = planetaRepository.save(p3);
    }
	
	@Test
    public void testCreatePlaneta() throws Exception {
        String newPlaneta = json(new Planeta(null,"Naboo","Temperate","Forests, mountains, lakes"));

        this.mockMvc.perform(post("/planetas")
                .contentType(contentType)
                .content(newPlaneta))
                .andExpect(status().isCreated());
    }

	
	@Test
    public void testCreatePlanetaNull() throws Exception {
        String newPlaneta = json(new Planeta(null,"","",""));

        this.mockMvc.perform(post("/planetas")
                .contentType(contentType)
                .content(newPlaneta))
        		.andDo(print())
        		.andExpect(status().isUnprocessableEntity())
        		.andExpect(jsonPath("$.error", is("Erro de validação")))
        		.andExpect(jsonPath("$.errors[0].message", anyOf(is("Clima é obrigatório"),is("Terreno é obrigatório"),is("Nome é obrigatório"))))
        		.andExpect(jsonPath("$.errors[1].message", anyOf(is("Clima é obrigatório"),is("Terreno é obrigatório"),is("Nome é obrigatório"))))
        		.andExpect(jsonPath("$.errors[2].message", anyOf(is("Clima é obrigatório"),is("Terreno é obrigatório"),is("Nome é obrigatório"))));
                
        
    }
	
	@Test
    public void testListPlanetas() throws Exception {
        mockMvc.perform(get("/planetas?page=0&linesPerPage=1&direction=ASC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome", is("Marte")));
	}
	
	@Test
    public void testSinglePlanetas() throws Exception {
        mockMvc.perform(get("/planetas/"+insertPlaneta.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Mercurio")));
	}
	
	@Test
    public void testSinglePlanetaNotFound() throws Exception {
        mockMvc.perform(get("/planetas/"+"dnaosidn123"))
                .andDo(print())
                .andExpect(status().isNotFound());
	}	
	
	@Test
    public void testListPlanetaByName() throws Exception {
        mockMvc.perform(get("/planetas/search?nome=M&page=0&linesPerPage=1&direction=ASC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome", is("Marte")));
	}

	@Test
    public void testListPlanetasByNameNotFound() throws Exception {
        mockMvc.perform(get("/planetas/search?nome=xyz&page=0&linesPerPage=1&direction=ASC"))
                .andDo(print())
                .andExpect(status().isOk());
	}
	
	@Test
    public void testDeletePlaneta() throws Exception {
        this.mockMvc.perform(delete("/planetas/"+insertPlaneta.getId())
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }
	
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
