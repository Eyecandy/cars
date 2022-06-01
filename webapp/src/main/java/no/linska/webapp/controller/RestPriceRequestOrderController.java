package no.linska.webapp.controller;


import no.linska.webapp.dto.BuyerUserDto;
import no.linska.webapp.dto.OfferDto;
import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.PriceRequestOrder;
import no.linska.webapp.entity.User;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.repository.PriceRequestOrderRepository;
import no.linska.webapp.repository.PriceRequestRepository;
import no.linska.webapp.service.BuyerService;
import no.linska.webapp.service.PriceRequestOrderService;
import no.linska.webapp.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;

@RestController()
@RequestMapping("/api/seller/pricerequestorder")
public class RestPriceRequestOrderController {
    @Autowired
    PriceRequestOrderService priceRequestOrderService;

    @Autowired
    PriceRequestRepository priceRequestRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    PriceRequestOrderRepository priceRequestOrderRepository;

    @Autowired
    BuyerService buyerService;



    @GetMapping("/listpricerequestorders")
    public ResponseEntity<?> getSellerPriceRequestOrder() {
        List<PriceRequestOrder> priceRequestOrderList = priceRequestOrderService.getOrdersBelongingToSellerUser();
        return ResponseEntity.ok().body(priceRequestOrderList);
    }

    @GetMapping("/getbuyerinfo/{priceRequestOrderId}")
    public ResponseEntity<?> getBuyerInfo(@PathVariable Long priceRequestOrderId) {
        PriceRequestOrder priceRequestOrder = priceRequestOrderService.getPriceRequestOrder(priceRequestOrderId);
        PriceRequest priceRequest = priceRequestOrder.getPriceRequest();
        User user = priceRequest.getUser();
        var buyer = buyerService.getBuerByUserId(user.getId());
        var buyerUserDto = new BuyerUserDto();
        buyerUserDto.setEmail(user.getEmail());
        buyerUserDto.setFirstName(user.getFirstName());
        buyerUserDto.setLastName(user.getLastName());
        buyerUserDto.setPhoneNumber(user.getPhoneNumber());
        buyerUserDto.setCity(buyer.getCity());
        buyerUserDto.setPostBox(buyer.getPostBox());
        buyerUserDto.setStreetName(buyer.getStreetName());
        buyerUserDto.setStreetNumber(buyer.getStreetNumber());
        return ResponseEntity.ok().body(buyerUserDto);
    }

    @GetMapping("/getfile/{priceRequestOrderId}")
    public ResponseEntity<?> getFileFromPriceRequest(@PathVariable Long priceRequestOrderId) throws IOException {

            PriceRequestOrder priceRequestOrder = priceRequestOrderService.getPriceRequestOrder(priceRequestOrderId);
            PriceRequest priceRequest = priceRequestOrder.getPriceRequest();

            if (!priceRequest.getConfigMethod().getName().equals("PDF")) {
                throw new ProcessingException(Reason.PDF_REQUEST_ON_WRONG_CONFIG_METHOD);
            }

        File file =  storageService.readFile(priceRequest.getConfiguration());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return ResponseEntity.ok()

                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);



    }
    @PostMapping("/offer")
    public ResponseEntity<?> createOffer(@Valid @RequestPart OfferDto offerDto,
                            @RequestPart(value="file", required = false) MultipartFile multipartFile,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body("Problemer med noen av feltene");
        }

        PriceRequestOrder priceRequestOrder =  priceRequestOrderService.getPriceRequestOrder(offerDto.getPriceRequestOrderId());
        PriceRequest priceRequest = priceRequestOrder.getPriceRequest();
        Calendar c = Calendar.getInstance();

        if (priceRequest.getDeadline().before(c.getTime())) {
            throw new ProcessingException(Reason.DEADLINE_LINE_PASSED,"deadline passed for :" + priceRequest.getId());
        }
        if (!priceRequestOrder.isAnswered()) {
            addThisOfferToTotal(priceRequest, priceRequestOrder);
        }
        priceRequestOrder.setAnswered(true);
        priceRequestOrder.setTotalPrice(offerDto.getAmountTotal());

        Path storedPath = storageService.store(multipartFile);
        priceRequestOrder.setOfferFilePath(storedPath.toString());

        priceRequestOrderRepository.save(priceRequestOrder);


        return ResponseEntity.status(201).body("Vi har lastet inn ditt tilbud");


    }

    private void addThisOfferToTotal(PriceRequest priceRequest, PriceRequestOrder priceRequestOrder) {
        Integer offersOnRequest = priceRequestOrder.getPriceRequest().getNumRetailersAnswered();
        priceRequest.setNumRetailersAnswered(offersOnRequest + 1);
        priceRequestRepository.save(priceRequest);

    }


}
