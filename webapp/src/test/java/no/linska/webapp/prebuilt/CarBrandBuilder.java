package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.CarBrand;

public class CarBrandBuilder {

    CarBrand toyota;

    CarBrand honda;

    public CarBrand getOrCreateValidCarBrandToyota() {
        if (toyota == null) {
            toyota = createValidCarBrandToyota();
        }
        return toyota;
    }

    public CarBrand getOrCreateValidCarBrandHonda() {
        if (honda == null) {
            honda  = createValidCarBrandHonda();
        }
        return honda;
    }


    public static CarBrand createValidCarBrandToyota() {
        CarBrand carBrand = new CarBrand();
        carBrand.setId(1);
        carBrand.setName("Toyota");
        return carBrand;

    }

    public static CarBrand createValidCarBrandHonda() {
        CarBrand carBrand = new CarBrand();
        carBrand.setId(2);
        carBrand.setName("Honda");
        return carBrand;
    }

}
