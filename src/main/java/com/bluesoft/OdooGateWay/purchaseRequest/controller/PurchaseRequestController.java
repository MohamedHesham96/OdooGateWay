package com.bluesoft.OdooGateWay.purchaseRequest.controller;

import com.bluesoft.OdooGateWay.Global.exceptions.CommandException;
import com.bluesoft.OdooGateWay.purchaseRequest.service.PurchaseRequestService;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestRequest;
import com.bluesoft.OdooGateWay.Global.reposnes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping(value = "/PO/{purchaseRequestId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> createPO(@PathVariable Integer purchaseRequestId) throws CommandException {
        purchaseRequestService.createPO(purchaseRequestId);
        Response response = new Response(purchaseRequestId, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/PA/{purchaseRequestId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Response> createPA(@PathVariable Integer purchaseRequestId) throws CommandException {
        purchaseRequestService.createPA(purchaseRequestId);
        Response response = new Response(purchaseRequestId, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
