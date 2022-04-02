package no.linska.webapp.service;

import no.linska.webapp.entity.ConfigMethod;
import no.linska.webapp.repository.ConfigMethodRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigMethodServiceImpl implements ConfigMethodService{

    @Autowired
    ConfigMethodRepository configMethodRepository;

    private List<ConfigMethod> configMethods;

    @Override
    public List<ConfigMethod> getAllConfigMethods() {
        if (configMethods == null ) {
            configMethods = IterableUtils.toList(configMethodRepository.findAll());
        }
        return configMethods;
    }
}
