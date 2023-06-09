package com.bluesoft.OdooGateWay.purchaseRequest;

import com.bluesoft.OdooGateWay.Exceptions.CommandException;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestRequest;
import com.bluesoft.OdooGateWay.requestAndReposne.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchaseRequests")
public class PurchaseRequestController {

    private final PurchaseRequestService purchaseRequestService;

    @Autowired
    public PurchaseRequestController(PurchaseRequestService purchaseRequestService) {
        this.purchaseRequestService = purchaseRequestService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> create(@RequestBody PurchaseRequestRequest purchaseRequestRequest) throws CommandException {
        Response response = purchaseRequestService.create(purchaseRequestRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
