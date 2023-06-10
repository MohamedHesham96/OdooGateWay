package com.bluesoft.OdooGateWay.purchaseRequest.entities;

import com.bluesoft.OdooGateWay.Global.enitities.RequestEntity;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PurchaseRequestRequest extends RequestEntity {
    @Valid
    private PurchaseRequestHeader purchaseRequestHeader;
    @Valid
    private List<PurchaseRequestItemLine> itemLines;

    @Override
    public String toString() {
        return "PurchaseRequestRequest{" + "header=" + purchaseRequestHeader + ", itemLines=" + itemLines + "}";
    }
}
