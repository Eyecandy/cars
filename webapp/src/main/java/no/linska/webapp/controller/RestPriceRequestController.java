package no.linska.webapp.controller;


import no.linska.webapp.dto.PriceRequestDto;
import no.linska.webapp.dto.PriceRequestStatsDto;
import no.linska.webapp.entity.*;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.repository.TireOptionRepository;
import no.linska.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/buyer/pricerequest")
public class RestPriceRequestController {

    @Autowired
    CarBrandService carBrandService;

    @Autowired
    TireOptionRepository tireOptionRepository;

    @Autowired
    ConfigMethodService configMethodService;

    @Autowired
    CountyService countyService;

    @Autowired
    StorageService storageService;

    @Autowired
    PriceRequestService priceRequestService;

    @Autowired
    PriceRequestOrderService priceRequestOrderService;







    @GetMapping("/list_price_request")
    public ResponseEntity<?> getPriceRequest() {
        List<PriceRequestDto> priceRequestDtos = new ArrayList<>();
        for (PriceRequest priceRequest: priceRequestService.getUserPriceRequest()) {
            priceRequestDtos.add(priceRequestService.convertPriceRequest(priceRequest));
        }
        return ResponseEntity.ok().body(priceRequestDtos);
    }

    @GetMapping("/dropdownvalues")
    public ResponseEntity<?> getAvailableCarBrands() {

        return ResponseEntity.ok(List.of(carBrandService.getAllCarBrandDtos(),
                tireOptionRepository.findAll(),
                configMethodService.getAllConfigMethods(),
                countyService.getAllCounties()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPriceRequest(@RequestPart PriceRequestDto priceRequestDTO,
                                                @RequestPart(value="file", required = false) MultipartFile multipartFile,
                                                BindingResult  bindingResult) throws InterruptedException {


        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        PriceRequest priceRequest = new PriceRequest();


        if (priceRequestDTO.getConfigMethod().getName().equals("Link")  && !priceRequestDTO.getLink().isBlank()) {
            String link = priceRequestDTO.getLink();
            priceRequest.setConfiguration(link);

        }

        else if (priceRequestDTO.getConfigMethod().getName().equals("PDF") && multipartFile != null) {
            Path storedPath = storageService.store(multipartFile);
            priceRequest.setConfiguration(storedPath.toString());
        }
        else {
            return ResponseEntity.badRequest().body("smth wrong with file, config method or link");
        }

        CarBrand carBrand = priceRequestDTO.getCarBrand();

        County county = priceRequestDTO.getCounty();
        TireOption tireOption = priceRequestDTO.getTireOption();
        ConfigMethod configMethod = priceRequestDTO.getConfigMethod();
        priceRequest.setConfigMethod(configMethod);
        priceRequest.setCarBrand(carBrand);
        priceRequest.setCounty(county);
        priceRequest.setTireOption(tireOption);

        priceRequestService.save(priceRequest);
        priceRequestOrderService.createPriceRequestOrders(priceRequest);



        return ResponseEntity.ok("my custom created");

    }

    @GetMapping("/getfile/{priceRequestId}")
    public ResponseEntity<?> getFileFromPriceRequest(@PathVariable  Long priceRequestId) throws IOException {



        PriceRequest priceRequest = priceRequestService.getPriceRequest(priceRequestId);
        priceRequestService.priceBelongToUserCheck(priceRequest);

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


    @GetMapping("/lowest-offer-file/{priceRequestId}")
    public ResponseEntity<?> getBestOfferFile(@PathVariable  Long priceRequestId) throws IOException {



        PriceRequest priceRequest = priceRequestService.getPriceRequest(priceRequestId);
        priceRequestService.priceBelongToUserCheck(priceRequest);

        var filePathOfLowestOffer = priceRequestOrderService.getFilePathOfBestOffer(priceRequest);


        File file =  storageService.readFile(filePathOfLowestOffer);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return ResponseEntity.ok()

                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }




    @GetMapping("/price-request-stats/{priceRequestId}")
    public ResponseEntity<?> getBestPriceAndStats(@PathVariable  Long priceRequestId) {
        ;
        PriceRequest priceRequest = priceRequestService.getPriceRequest(priceRequestId);
        priceRequestService.priceBelongToUserCheck(priceRequest);

        var priceRequestOrderList = priceRequestOrderService.getPriceRequestOrdersBelongingTo(priceRequestId);

        var highestAndLowestOffer = priceRequestOrderService.getHighestAndLowest(priceRequestOrderList);
        if (highestAndLowestOffer.isEmpty()) {
            throw new ProcessingException(Reason.NO_ANSWER_PRICE_REQUEST_ORDERS_ON_REQUEST,priceRequestId.toString());
        }
        PriceRequestStatsDto priceRequestStatsDto = new PriceRequestStatsDto();
        priceRequestStatsDto.setPriceRequestOrderId(highestAndLowestOffer.get(0).getId());
        priceRequestStatsDto.setHighestOffer(highestAndLowestOffer.get(0).getTotalPrice());
        priceRequestStatsDto.setLowestOffer(highestAndLowestOffer.get(highestAndLowestOffer.size()-1).getTotalPrice());


        return ResponseEntity.ok(priceRequestStatsDto);

    }



}
