package no.linska.webapp.dto;

import lombok.Data;
import no.linska.webapp.entity.CarBrand;
import no.linska.webapp.entity.ConfigMethod;
import no.linska.webapp.entity.County;
import no.linska.webapp.entity.TireOption;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PriceRequestDto {
    private long id;

    @NotNull(message = "Må velge bilmerke")
    private CarBrand carBrand;

    @NotNull(message = "Må velge dekk")
    private TireOption tireOption;

    private String link;

    @NotNull
    private County county;

    @NotNull
    private ConfigMethod configMethod;

    private Date deadline;





}
