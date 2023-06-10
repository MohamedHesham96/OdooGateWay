package com.bluesoft.OdooGateWay.purchaseRequest.entities;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseRequestItemLine {
   @NotNull
   @Min(value = 1)
   private Integer productId;
    @NotNull
    private Double productQuantity;
    @NotNull
    @Min(value = 1)
    private Integer productUomId;

    @Override
    public String toString() {
        return "{" +
                "productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", productUomId=" + productUomId +
                '}';
    }
}
