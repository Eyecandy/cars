package no.linska.webapp.controller;

import no.linska.webapp.entity.User;
import no.linska.webapp.entity.UserRole;
import no.linska.webapp.repository.UserRepository;
import no.linska.webapp.service.PriceRequestService;
import no.linska.webapp.service.UserRoleService;
import no.linska.webapp.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PriceRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PriceRequestService priceRequestService;



    private String testUserName = "spring@mail.com";

    @BeforeEach
    void tearDown() {
        userRepository.deleteAll();
    }







    @Test
    @WithMockUser(value = "spring@mail.com")
    public void shouldCreatePriceRequest() throws Exception {
        //TODO: CREATE spring@gmail.com user in DB before test
        //ADD USER IN POST PRICE REQUEST AND SAVE PRICEREQUEST

        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setId(1);
        UserRole userRole1 = userRoleService.findRoleById(userRole);
        user.setUserRole(userRole1);
        user.setEmail(testUserName);
        user.setPassword("mypassword");
        userService.register(user);

       MockHttpServletRequestBuilder request = post("/pricerequest")
                .param("carBrandId","1")
                .param("countyId","1")
                .param("user.id","1")
                .param("configMethodId", "1")
                .param("config", "1")
                .param("configuration","1")

                .with(csrf());


        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(value = "spring@mail.com")
    public void shouldGetPriceList() throws Exception {
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setId(1);
        UserRole userRole1 = userRoleService.findRoleById(userRole);
        user.setUserRole(userRole1);
        user.setEmail(testUserName);
        user.setPassword("mypassword");
        userService.register(user);

        MockHttpServletRequestBuilder createPriceRequest = post("/pricerequest")
                .param("carBrandId","1")
                .param("countyId","1")
                .param("user.id","1")
                .param("configMethodId", "1")
                .param("config", "1")
                .param("configuration","1")

                .with(csrf());


        this.mvc.perform(createPriceRequest);

        MockHttpServletRequestBuilder getListRequest = get("/list_price_request");

        this.mvc.perform(getListRequest.with(csrf()))
                .andDo(print())
        ;


    }


    @Test
    @WithMockUser(value = "spring@mail.com")
    public void shouldThrowErrorMissingCarBrandId() throws Exception {


        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setId(1);
        UserRole userRole1 = userRoleService.findRoleById(userRole);
        user.setUserRole(userRole1);
        user.setEmail(testUserName);
        user.setPassword("mypassword");
        userService.register(user);

        MockHttpServletRequestBuilder request = post("/pricerequest")
                .param("countyId","1")
                .param("user.id","1")
                .param("configMethodId", "1")
                .param("config", "1")
                .param("configuration","1")

                .with(csrf());


        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
