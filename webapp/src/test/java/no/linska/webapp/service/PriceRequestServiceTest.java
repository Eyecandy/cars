package no.linska.webapp.service;

import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.exception.reason.ProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriceRequestServiceTest {

    @Autowired
    PriceRequestService priceRequestService;

    @Test
    void deadLineIsReached() {
        PriceRequest priceRequest = new PriceRequest();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -48);  // advances day by 2
        priceRequest.setDeadline(calendar.getTime());
        Assertions.assertTrue(priceRequestService.isDeadlineReached(priceRequest));

    }

    @Test
    void deadLineNotReached() {
        PriceRequest priceRequest = new PriceRequest();
        Calendar calendar = Calendar.getInstance();
        int arbitraryStartOrderNumber = 2437;
        calendar.add(Calendar.HOUR, 48);  // advances day by 2
        priceRequest.setDeadline(calendar.getTime());


        Assertions.assertFalse(priceRequestService.isDeadlineReached(priceRequest));

    }

    @Test
    void deadLineFieldMissingFromPriceRequest() {
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setId(1L);

        ProcessingException exception = Assertions.assertThrows(ProcessingException.class,
                () -> {priceRequestService.isDeadlineReached(priceRequest);}  ) ;
        Assertions.assertEquals("s20", exception.getReason().getCode());


    }
}