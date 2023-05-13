package no.linska.webapp.dto;

import lombok.Data;

@Data
public class PriceRequestStatsDto {
    private Long PriceRequestOrderId;
    private Long lowestOffer;
    private Long highestOffer;
}
