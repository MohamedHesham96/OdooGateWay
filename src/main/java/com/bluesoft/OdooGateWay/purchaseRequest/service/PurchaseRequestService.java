package com.bluesoft.OdooGateWay.purchaseRequest.service;

import com.bluesoft.OdooGateWay.Global.exceptions.CommandException;
import com.bluesoft.OdooGateWay.Global.adapter.Adapter;
import com.bluesoft.OdooGateWay.Global.client.BaseClient;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestRequest;
import com.bluesoft.OdooGateWay.Global.reposnes.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PurchaseRequestService {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final Adapter<PurchaseRequestRequest> purchaseRequestAdapter;
    private final BaseClient purchaseRequestClient;

    @Autowired
    public PurchaseRequestService(Adapter<PurchaseRequestRequest> purchaseRequestAdapter, BaseClient purchaseRequestClient) {
        this.purchaseRequestAdapter = purchaseRequestAdapter;
        this.purchaseRequestClient = purchaseRequestClient;
    }

    public Response create(PurchaseRequestRequest purchaseRequestRequest) throws CommandException {
        logger.info("START CREATE " + PurchaseRequestRequest.class.getSimpleName());
        HashMap<String, Object> odooEntity = purchaseRequestAdapter.adapt(purchaseRequestRequest);
        Integer odooEntityId;
        try {
            odooEntityId = createCommand(odooEntity);
            confirmCommand(odooEntityId);
            logger.info("FINISH CREATE " + PurchaseRequestRequest.class.getSimpleName());
            return new Response(odooEntityId, HttpStatus.CREATED);
        } catch (CommandException exception) {
            logger.error("FINISH CREATE " + PurchaseRequestRequest.class.getSimpleName());
            throw exception;
        }
    }

    public Integer createCommand(HashMap<String, Object> odooPurchaseRequest) throws CommandException {
        logger.info("START CREATE" + " [" + odooPurchaseRequest + "]");
        Integer odooEntityId;
        final String commandName = "create";
        try {
            odooEntityId = purchaseRequestClient.executeCommand(purchaseRequestClient.getModel(), commandName, odooPurchaseRequest);
        } catch (Exception exception) {
            throw new CommandException(commandName.toUpperCase() + " process has been failed", exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return odooEntityId;
    }

    public void confirmCommand(Integer odooEntityId) throws CommandException {
        final String commandName = "action_confirm_request";
        executeCommand(odooEntityId, commandName);
    }

    public void createPO(Integer purchaseRequestId) throws CommandException {
        final String commandName = "action_create_purchase_order";
        executeCommand(purchaseRequestId, commandName);
    }

    private void executeCommand(Integer purchaseRequestId, String commandName) throws CommandException {
        try {
            logger.info("START " + commandName.toUpperCase() + " [" + purchaseRequestId + "]");
            purchaseRequestClient.executeCommand(purchaseRequestClient.getModel(), "action_confirm_request", purchaseRequestId);
            logger.info("FINISH " + commandName.toUpperCase() + " [" + purchaseRequestId + "]");
        } catch (RuntimeException exception) {
            logger.error("(odoo side) FINISH " + commandName.toUpperCase() + " [" + purchaseRequestId + "]");
        } catch (Exception exception) {
            logger.error("FINISH " + commandName.toUpperCase() + " [" + purchaseRequestId + "]");
            throw new CommandException(commandName.toUpperCase() + " process has been failed", HttpStatus.BAD_REQUEST);
        }
    }

    public void createPA(Integer purchaseRequestId) throws CommandException {
        final String commandName = "action_create_purchase_requisition";
        executeCommand(purchaseRequestId, commandName);
    }
}
