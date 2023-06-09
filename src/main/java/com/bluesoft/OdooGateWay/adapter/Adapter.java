package com.bluesoft.OdooGateWay.adapter;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface Adapter<O extends RequestEntity> {
    HashMap<String, Object> adapt(O entity);
}
