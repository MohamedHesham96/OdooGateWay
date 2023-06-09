package com.bluesoft.OdooGateWay.purchaseRequest;

import com.bluesoft.OdooGateWay.Exceptions.CommandException;
import com.bluesoft.OdooGateWay.adapter.Adapter;
import com.bluesoft.OdooGateWay.client.BaseClient;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestRequest;
import com.bluesoft.OdooGateWay.requestAndReposne.Response;
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
        try {
            logger.info("START " + commandName.toUpperCase() + " [" + odooEntityId + "]");
            purchaseRequestClient.executeCommand(purchaseRequestClient.getModel(), "action_confirm_request", odooEntityId);
            logger.info("FINISH " + commandName.toUpperCase() + " [" + odooEntityId + "]");
        } catch (RuntimeException exception) {
            logger.error("(odoo side) FINISH " + commandName.toUpperCase() + " [" + odooEntityId + "]");
        } catch (Exception exception) {
            logger.error("FINISH " + commandName.toUpperCase() + " [" + odooEntityId + "]");
            throw new CommandException(commandName.toUpperCase() + " process has been failed", HttpStatus.BAD_REQUEST);
        }
    }
}
