package no.linska.webapp.service;

import no.linska.webapp.dto.CarBrandDto;
import no.linska.webapp.entity.CarBrand;

import java.util.List;

public interface CarBrandService  {

    List<CarBrand> getAllCarBrands();

    List<CarBrandDto> getAllCarBrandDtos();


}
