package no.linska.webapp.controller;


import no.linska.webapp.dto.PriceRequestDto;
import no.linska.webapp.entity.*;
import no.linska.webapp.repository.TireOptionRepository;
import no.linska.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @GetMapping("/list_price_request")
    public ResponseEntity<?> getPriceRequest() {
        List<PriceRequestDto> priceRequestDtos = new ArrayList<>();
        for (PriceRequest priceRequest: priceRequestService.getUserPriceRequest()) {
            priceRequestDtos.add(convertPriceRequest(priceRequest));
        }
        return ResponseEntity.ok().body(priceRequestDtos);
    }

    @GetMapping("/dropdownvalues")
    public ResponseEntity<?> getAvailableCarBrands() {

        return ResponseEntity.ok(List.of(carBrandService.getAllCarBrands(),
                tireOptionRepository.findAll(),
                configMethodService.getAllConfigMethods(),
                countyService.getAllCounties()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPriceRequest(@RequestPart PriceRequestDto priceRequestDTO,
                                                @RequestPart(value="file", required = false) MultipartFile multipartFile,
                                                BindingResult  bindingResult){

        if (bindingResult.hasErrors()) {
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

        return ResponseEntity.ok("my custom created");

    }

    private PriceRequestDto convertPriceRequest(PriceRequest priceRequest) {
        PriceRequestDto priceRequestDto = new PriceRequestDto();
        priceRequestDto.setCounty(priceRequest.getCounty());
        priceRequestDto.setDeadline(priceRequest.getDeadline());
        priceRequestDto.setTireOption(priceRequest.getTireOption());
        priceRequestDto.setCarBrand(priceRequest.getCarBrand());
        priceRequestDto.setConfigMethod(priceRequest.getConfigMethod());
        priceRequestDto.setLink(priceRequest.getConfiguration());
        priceRequestDto.setId(priceRequest.getId());

        return priceRequestDto;
    }

}
