package com.bluesoft.OdooGateWay.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;

/**
 * <p>
 * Title: MilanoOdOOBaseDAO.java
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright(c) EME International, 2021
 * </p>
 *
 * <p>
 * Company: EME International
 * </p>
 *
 * @author <a href="mailto:mohamed.hesham@emeint.net">Mohamed Hesham</a>
 * @version 1.0
 * @date 23/01/2023
 */

@Component
public abstract class MilanoOdooBaseClient {

    public static final String DATABASE_NAME = "database.name";
    public static final String DATABASE_USERNAME = "database.username";
    public static final String DATABASE_PASSWORD = "database.password";
    public static final String Odoo_COMMON_URL = "odoo.common.url";
    public static final String Odoo_OBJECT_URL = "odoo.object.url";
    public static final String AUTHENTICATE = "authenticate";
    public static final String API_URL = "api.url";
    public static final String MILANO_COMPANY_ID = "milano.company.id";
    public static final String ASEEL_COMPANY_ID = "aseel.company.id";
    public static final String WADY_COMPANY_ID = "wadi.company.id";
    public static final String SYADEEN_COMPANY_ID = "syadeen.company.id";
    public static final int FIVE_MINUTES = 300000;
    public static final String FIELDS = "fields";
    private Integer uid;
    public static final String SEARCH_READ = "search_read";

    public static final String SEARCH = "search";
    public static final String CREATE = "create";
    public static final String EXECUTE_KW = "execute_kw";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String apiUrl;
    private String username;
    private String password;
    private String databaseName;
    private String odooCommonUrl;
    private String odooObjectUrl;
    private int milanoCompanyId;
    private int aseelCompanyId;
    private int wadiCompanyId;
    private int syadeenCompanyId;

    private void login() {

    }

    @SuppressWarnings({"rawtypes"})
    public Object[] findAll() {
        try {
            logger.info("Odoo: Find all records");
            String modelName = getModelName();
            if (this.uid == null) {
                login();
            }
            List criteria = asList(getCriteria(), getCustomCriteria());
            logRequest(criteria);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setReplyTimeout(FIVE_MINUTES);
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));

            return (Object[]) client.execute(common_config, EXECUTE_KW, asList(databaseName, uid, password, modelName, SEARCH_READ,
                    Collections.singletonList(criteria), new HashMap<String, Object>() {

                        {
                            put(FIELDS, getFields());
                        }
                    }));
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public Object[] findByCodes(List codes) {
        try {
            logger.info("Odoo: Find by codes");
            String modelName = getModelName();
            if (this.uid == null) {
                login();
            }
            List criteria = asList(getFindByCodeCriteria(codes), getCustomCriteria());
            logRequest(criteria);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setReplyTimeout(FIVE_MINUTES);
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            return (Object[]) client.execute(common_config, EXECUTE_KW, asList(databaseName, uid, password, modelName, SEARCH_READ,
                    Collections.singletonList(criteria), new HashMap<String, Object>() {

                        {
                            put(FIELDS, getFields());
                        }
                    }));
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public Boolean isPosted(String modelName, Integer entityId) {
        try {
            logger.info("Odoo: Is posted");
            if (this.uid == null) {
                login();
            }
            List criteria = asList(getIdCriteria(entityId), getStateCriteria());
            logRequest(criteria);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setReplyTimeout(FIVE_MINUTES);
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            Object[] result = (Object[]) client.execute(common_config, EXECUTE_KW,
                    asList(databaseName, uid, password, modelName, SEARCH, Collections.singletonList(criteria)));
            return result.length > 0;
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public Object[] getSpecificFields(String modelName, List codes, List<String> requiredFields) {
        try {
            logger.info("Odoo: Get specific fields " + requiredFields);
            if (this.uid == null) {
                login();
            }
            List criteria = asList(getFindByCodeCriteria(codes), getCustomCriteria());
            logRequest(criteria);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setReplyTimeout(FIVE_MINUTES);
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            return (Object[]) client.execute(common_config, EXECUTE_KW, asList(databaseName, uid, password, modelName, SEARCH_READ,
                    Collections.singletonList(criteria), new HashMap<String, Object>() {

                        {
                            put(FIELDS, requiredFields);
                        }
                    }));
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public Object[] getSpecificFields(String modelName, String fieldName, Integer fieldValue, List<String> requiredFields) {
        try {
            logger.info("Odoo: Get specific fields " + requiredFields);
            if (this.uid == null) {
                login();
            }
            List criteria = Collections.singletonList(buildCustomCriteria(fieldName, fieldValue));
            logRequest(criteria);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setReplyTimeout(FIVE_MINUTES);
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            return (Object[]) client.execute(common_config, EXECUTE_KW, asList(databaseName, uid, password, modelName, SEARCH_READ,
                    Collections.singletonList(criteria), new HashMap<String, Object>() {

                        {
                            put(FIELDS, requiredFields);
                        }
                    }));
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<? extends Serializable> buildCustomCriteria(String parentIdFieldName, Integer parentId) {
        return asList(parentIdFieldName, "=", parentId);
    }

    @SuppressWarnings({"rawtypes"})
    public Object[] findByDate(Date fromDate) {
        try {
            logger.info("Odoo: find by date");
            String modelName = getModelName();
            if (this.uid == null) {
                login();
            }
            List criteria = new ArrayList();
            logRequest(criteria);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            common_config.setReplyTimeout(FIVE_MINUTES);

            return (Object[]) client.execute(common_config, EXECUTE_KW, asList(databaseName, uid, password, modelName, SEARCH_READ,
                    Collections.singletonList(criteria), new HashMap<String, Object>() {

                        {
                            put(FIELDS, getFields());
                        }
                    }));
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }



    public Object executeCommand(String modelName, String commandName, int entityId) {
        try {
            if (this.uid == null) {
                login();
            }
            logger.info("Start " + commandName.toUpperCase() + " " + modelName + " [" + entityId + "]");
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            common_config.setReplyTimeout(FIVE_MINUTES);
            Object result = client.execute(common_config, EXECUTE_KW,
                    asList(databaseName, uid, password, modelName, commandName, Collections.singletonList(entityId)));
            logger.info("Finish " + commandName.toUpperCase() + " " + modelName + " [" + entityId + "]");
            return result;
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object executeCommand(String modelName, String commandName, HashMap<String, Object> entity) {
        try {
            if (this.uid == null) {
                login();
            }
            logger.info("Start create " + modelName + "  " + entity);
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            Object createdEntityId = client.execute(common_config, EXECUTE_KW,
                    asList(databaseName, uid, password, modelName, commandName, Collections.singletonList(entity)));
            logger.info("Finish create " + modelName + " [" + createdEntityId + "]");
            return createdEntityId;
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getModelName();

    protected String getCodeFieldName() {
        return "id";
    }

    @SuppressWarnings({"rawtypes"})
    protected List getCriteria() {
        return asList(getCodeFieldName(), "!=", false);
    }

    @SuppressWarnings({"rawtypes"})
    protected List getCustomCriteria() {
        return getCriteria();
    }

    @SuppressWarnings({"rawtypes"})
    protected List getFindByCodeCriteria(List codes) {
        return asList(getCodeFieldName(), "in", codes);
    }

    @SuppressWarnings({"rawtypes"})
    protected List getStateCriteria() {
        return EMPTY_LIST;
    }

    @SuppressWarnings({"rawtypes"})
    protected List getIdCriteria(Integer id) {
        return asList("id", "=", id);
    }

    public List<String> getFields() {
        return Collections.singletonList(getCodeFieldName());
    }

    public String getCompanyCode(String entityCode) {
        return entityCode.substring(0, 1);
    }

    public int getCompanyId(String entityCode) {
        switch (getCompanyCode(entityCode)) {
            case "M":
                return milanoCompanyId;
            case "A":
                return aseelCompanyId;
            case "W":
                return wadiCompanyId;
            case "S":
                return syadeenCompanyId;
            default:
                return 0;
        }
    }


    @SuppressWarnings({"rawtypes"})
    private void logRequest(List criteria) {
        logger.info("API URL: [" + apiUrl + "] Model name: [" + getModelName() + "] " + "Criteria: " + criteria);
    }

}
