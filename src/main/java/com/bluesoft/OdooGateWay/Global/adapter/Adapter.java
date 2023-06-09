package com.bluesoft.OdooGateWay.Global.adapter;

import com.bluesoft.OdooGateWay.Global.enitities.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface Adapter<O extends RequestEntity> {
    HashMap<String, Object> adapt(O entity);
}
