package no.linska.webapp.service;

import no.linska.webapp.entity.CarBrand;
import no.linska.webapp.repository.CarBrandRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarBrandServiceImpl  implements CarBrandService{

    @Autowired
    CarBrandRepository carBrandRepository;

    List<CarBrand> allCarBrands;


    @Override
    public List<CarBrand> getAllCarBrands() {
        if (allCarBrands == null) {
            allCarBrands = IterableUtils.toList(carBrandRepository.findAll());
        }
        return allCarBrands;
    }
}
