package com.bluesoft.OdooGateWay.purchaseRequest.entities;

import com.bluesoft.OdooGateWay.adapter.RequestEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PurchaseRequestRequest extends RequestEntity {
    private PurchaseRequestHeader purchaseRequestHeader;
    private List<PurchaseRequestItemLine> itemLines;

    @Override
    public String toString() {
        return "PurchaseRequestRequest{" + "header=" + purchaseRequestHeader + ", itemLines=" + itemLines + "}";
    }
}
