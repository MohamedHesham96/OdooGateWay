package com.bluesoft.OdooGateWay.purchaseRequest.entities;

import com.bluesoft.OdooGateWay.adapter.RequestEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PurchaseRequestHeader extends RequestEntity {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;
    private Integer requesterId;
    private Integer companyId;
    private Integer partnerId;
    private Boolean active;

    @Override
    public String toString() {
        return "{" +
                "requestDate=" + requestDate +
                ", scheduleDate=" + scheduleDate +
                ", requesterId=" + requesterId +
                ", companyId=" + companyId +
                ", partnerId=" + partnerId +
                ", active=" + active +
                '}';
    }
}
