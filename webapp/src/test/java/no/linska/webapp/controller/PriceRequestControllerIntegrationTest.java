package no.linska.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.linska.webapp.entity.PriceRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.json.JsonObject;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PriceRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(value = "spring@mail.com")
    public void createMockRequest() throws Exception {
        //TODO: CREATE spring@gmail.com user in DB before test
        //ADD USER IN POST PRICE REQUEST AND SAVE PRICEREQUEST

       MockHttpServletRequestBuilder request = post("/pricerequest")
                .param("carBrandId","1")
                .param("countyId","1")
                .param("user.id","1")
                .param("configMethodId", "1")
                .param("config", "1")
                .param("configuration","1")

                .with(csrf());


        this.mvc.perform(request)
                .andDo(print());


    }
}
