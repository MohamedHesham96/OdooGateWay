package com.bluesoft.OdooGateWay.purchaseRequest.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PurchaseRequestItemLine {
    private Integer productId;
    private Double productQuantity;
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
