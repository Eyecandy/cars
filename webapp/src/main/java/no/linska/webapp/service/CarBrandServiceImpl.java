package no.linska.webapp.service;

import no.linska.webapp.dto.CarBrandDto;
import no.linska.webapp.entity.CarBrand;
import no.linska.webapp.repository.CarBrandRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarBrandServiceImpl  implements CarBrandService{

    @Autowired
    CarBrandRepository carBrandRepository;

    List<CarBrand> allCarBrands;

    List<CarBrandDto> carBrandDtos;


    @Override
    public List<CarBrand> getAllCarBrands() {
        if (allCarBrands == null) {
            allCarBrands = IterableUtils.toList(carBrandRepository.findAll());
        }
        return allCarBrands;
    }

    public List<CarBrandDto> getAllCarBrandDtos() {
        if (allCarBrands == null) {
            allCarBrands = IterableUtils.toList(carBrandRepository.findAll());
        }
        return convertAllToDto(allCarBrands);
    }

    private List<CarBrandDto> convertAllToDto(List<CarBrand> carBrands) {
        if (carBrandDtos ==  null) {
            List<CarBrandDto> carBrandDtosTemp = new ArrayList<>();
            for (CarBrand carBrand: carBrands) {
                CarBrandDto carBrandDto = convertToDto(carBrand);
                carBrandDtosTemp.add(carBrandDto);
            }
            carBrandDtos = carBrandDtosTemp;
        }
        return  carBrandDtos;
    }


    private CarBrandDto convertToDto(CarBrand carBrand) {
        CarBrandDto carBrandDto = new CarBrandDto();
        carBrandDto.setId(carBrand.getId());
        carBrandDto.setName(carBrand.getName());
        return carBrandDto;
    }
}
