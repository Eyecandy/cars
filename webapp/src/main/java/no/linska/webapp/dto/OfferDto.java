package no.linska.webapp.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OfferDto {
    @NotNull
    Long priceRequestOrderId;

    @NotNull
    Long amountTotal;

    @NotNull
    Integer monthsToDelivery;
}
