package com.bluesoft.OdooGateWay.purchaseRequest;

import com.bluesoft.OdooGateWay.adapter.Adapter;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestHeader;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestItemLine;
import com.bluesoft.OdooGateWay.purchaseRequest.entities.PurchaseRequestRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class PurchaseRequestAdapter implements Adapter<PurchaseRequestRequest> {
    protected final Log logger = LogFactory.getLog(this.getClass());

    public HashMap<String, Object> adapt(PurchaseRequestRequest request) {
        logger.info("START ADAPT " + request.getClass().getSimpleName() + "[" + request + "]");
        HashMap<String, Object> odooEntity = adaptHeader(request.getPurchaseRequestHeader());
        odooEntity.put("request_line_ids", adaptItemLines(request.getItemLines()));
        logger.info("FINISH ADAPT " + request.getClass().getSimpleName() + "[" + odooEntity + "]");
        return odooEntity;
    }

    private HashMap<String, Object> adaptHeader(PurchaseRequestHeader header) {
        logger.info("START ADAPT " + header.getClass().getSimpleName() + "[" + header + "]");
        HashMap<String, Object> odooEntityHeader = new HashMap<>();
        if (header.getRequestDate() != null) {
            odooEntityHeader.put("request_date", header.getRequestDate().toString());
        }
        if (header.getScheduleDate() != null) {
            odooEntityHeader.put("schedule_date", header.getScheduleDate().toString());
        }
        odooEntityHeader.put("company_id", header.getCompanyId());
        if (header.getScheduleDate() != null) {
            odooEntityHeader.put("requester_id", header.getRequesterId());
        }
        if (header.getPartnerId() != null) {
            odooEntityHeader.put("partner_id", header.getPartnerId());
        }
        logger.info("FINISH ADAPT " + header.getClass().getSimpleName() + "[" + odooEntityHeader + "]");
        return odooEntityHeader;
    }

    private List<Object> adaptItemLines(List<PurchaseRequestItemLine> itemLines) {
        logger.info("START ADAPT item lines" + PurchaseRequestItemLine.class.getSimpleName() + "[" + itemLines + "]");
        ArrayList<Object> odooItemLines = new ArrayList<>();
        for (PurchaseRequestItemLine itemLine : itemLines) {
            HashMap<String, Object> odooItemLine = new HashMap<>();
            odooItemLine.put("product_id", itemLine.getProductId());
            odooItemLine.put("product_qty", itemLine.getProductQuantity());
            odooItemLine.put("product_uom_id", itemLine.getProductUomId());
            odooItemLines.add(Arrays.asList(0, 0, odooItemLine));
        }
        logger.info("FINISH ADAPT item lines" + PurchaseRequestItemLine.class.getSimpleName() + "[" + odooItemLines + "]");
        return odooItemLines;
    }
}
