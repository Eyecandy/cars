package no.linska.webapp.service;

import no.linska.webapp.entity.County;
import no.linska.webapp.repository.CountyRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountyServiceImpl implements CountyService {


    @Autowired
    CountyRepository countyRepository;

    List<County> allCounties;

    @Override
    public List<County> getAllCounties() {
        if (allCounties == null) {
            allCounties = IterableUtils.toList(countyRepository.findAll());
        }
        return allCounties;

    }
}
