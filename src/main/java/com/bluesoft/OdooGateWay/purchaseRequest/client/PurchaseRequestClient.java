package com.bluesoft.OdooGateWay.purchaseRequest.client;

import com.bluesoft.OdooGateWay.Global.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PurchaseRequestClient extends BaseClient {

    @Autowired
    public PurchaseRequestClient(Environment environment) {
        super(environment);
    }

    @Override
    public String getModel() {
        return "purchase.request";
    }
}
