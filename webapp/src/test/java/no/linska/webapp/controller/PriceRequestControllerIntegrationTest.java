package no.linska.webapp.controller;

import no.linska.webapp.entity.*;
import no.linska.webapp.prebuilt.CarBrandBuilder;
import no.linska.webapp.prebuilt.RetailersBuilder;
import no.linska.webapp.prebuilt.SellerBuilder;
import no.linska.webapp.repository.*;
import no.linska.webapp.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
    UserService userService;

    @Autowired
    CarBrandRepository carBrandRepository;

    @Autowired
    RetailerRepository retailerRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PriceRequestRepository priceRequestRepository;

    @Autowired
    PriceRequestOrderRepository priceRequestOrderRepository;






    CarBrand toyota;
    CarBrand honda;


    private String customerUserName = "spring@mail.com";

    private String sellerUserName = "seller@mail.com";


    @BeforeAll
    void addDataToTables() {
        UserRole sellerRole = new UserRole();
        sellerRole.setType("Seller");
        sellerRole.setId(2);
        UserRole customerRole = new UserRole();
        customerRole.setId(1);
        customerRole.setType("Customer");
        userRoleRepository.save(customerRole);
        userRoleRepository.save(sellerRole);
        // DB setup
        CarBrand toyota = CarBrandBuilder.createValidCarBrandToyota();
        CarBrand honda = CarBrandBuilder.createValidCarBrandHonda();
        Set<CarBrand> carBrands = new HashSet<>();
        carBrands.add(toyota);
        carBrands.add(honda);
        this.toyota = toyota;
        this.honda = honda;
        carBrandRepository.saveAll(carBrands);
        Retailer retailer = RetailersBuilder.createRetailer();
        retailer.setCarBrands(carBrands);
        retailerRepository.save(retailer);

        User sellerUser = new User();

        sellerUser.setUserRole(sellerRole);
        sellerUser.setEmail(sellerUserName);
        sellerUser.setPassword("mypassword");

        userService.register(sellerUser);
        Seller seller = SellerBuilder.createSeller();
        seller.setUser(sellerUser);
        seller.setRetailer(retailer);
        sellerRepository.save(seller);

        User customerUser = new User();
        customerUser.setUserRole(customerRole);
        customerUser.setEmail(customerUserName);
        customerUser.setPassword("mypassword");
        userService.register(customerUser);

        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setUser(customerUser);
        priceRequest.setCarBrandId(toyota.getId());
        priceRequest.setConfiguration("/path/file.pdf");
        priceRequest.setConfigMethodId(1);
        priceRequest.setCountyId(1);

    }



    @BeforeEach
    public void removePriceRequests() {
        priceRequestOrderRepository.deleteAll();
        priceRequestRepository.deleteAll();

    }





    @Test
    @WithMockUser(value = "spring@mail.com")
    public void shouldCreatePriceRequestOrder() throws Exception {


        // request
       MockHttpServletRequestBuilder request = post("/pricerequest")
                .param("carBrandId",toyota.getId().toString())
                .param("countyId",toyota.getId().toString())
                .param("configMethodId", "1")
                .param("config", "1")
                .param("configuration","1")
                .with(csrf());

        // test request
        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
        Iterable<PriceRequestOrder> priceRequestOrders = priceRequestOrderRepository.findAll();
        Iterable<PriceRequest> priceRequests =  priceRequestRepository.findAll();
        Assertions.assertEquals(1, priceRequests.spliterator().estimateSize());
        Assertions.assertEquals(1, priceRequestOrders.spliterator().estimateSize());
        PriceRequest priceRequest = priceRequests.iterator().next();
        PriceRequestOrder priceRequestOrder = priceRequestOrders.iterator().next();

        Assertions.assertEquals(priceRequest.getId(), priceRequestOrder.getPriceRequest().getId());

    }

    @Test
    @WithMockUser(value = "spring@mail.com")
    public void shouldGetPriceList() throws Exception {


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
