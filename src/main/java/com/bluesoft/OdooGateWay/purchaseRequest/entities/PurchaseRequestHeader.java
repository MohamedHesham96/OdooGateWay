package com.bluesoft.OdooGateWay.purchaseRequest.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PurchaseRequestHeader {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;
    @NotNull(message = "Company id can't be null")
    @Min(value = 1)
    private Integer companyId;
    private Integer partnerId;
    private Boolean active;

    @Override
    public String toString() {
        return "{" +
                "requestDate=" + requestDate +
                ", scheduleDate=" + scheduleDate +
                ", companyId=" + companyId +
                ", partnerId=" + partnerId +
                ", active=" + active +
                '}';
    }
}
