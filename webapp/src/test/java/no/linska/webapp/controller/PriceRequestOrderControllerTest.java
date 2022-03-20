package no.linska.webapp.controller;


import no.linska.webapp.entity.PriceRequestOrder;
import no.linska.webapp.prebuilt.SellerBuilder;
import no.linska.webapp.service.PriceRequestOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceRequestOrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PriceRequestOrderServiceImpl priceRequestOrderService;


    @Test

    @WithMockUser(value = "seller@mailcom")
    public void shouldGetOrderList() throws Exception {

        List<PriceRequestOrder> priceRequestOrders = new ArrayList<>();

        PriceRequestOrder priceRequestOrder = new PriceRequestOrder();
        priceRequestOrder.setSeller(SellerBuilder.createSeller());
        priceRequestOrders.add(priceRequestOrder);

        given(this.priceRequestOrderService.getOrdersBelongingToSellerUser())
                .willReturn(priceRequestOrders);

        MockHttpServletRequestBuilder getListRequest = get("/price_request_order");

        this.mvc.perform(getListRequest.with(csrf()))
                .andDo(print())
                .andExpect(model().attributeExists("priceRequestOrders"))
                .andExpect(model().attribute("priceRequestOrders", priceRequestOrders))
        ;

    }


}
