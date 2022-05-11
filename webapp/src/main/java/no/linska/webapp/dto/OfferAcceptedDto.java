package no.linska.webapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OfferAcceptedDto {
    @NotNull
    Long priceRequestId;
}
