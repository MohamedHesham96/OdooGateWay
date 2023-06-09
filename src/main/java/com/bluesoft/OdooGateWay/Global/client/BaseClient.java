package com.bluesoft.OdooGateWay.Global.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;

@Component
public abstract class BaseClient {
    protected final Log logger = LogFactory.getLog(this.getClass());

    public static final String DATABASE_NAME = "database.name";
    public static final String DATABASE_USERNAME = "database.username";
    public static final String DATABASE_PASSWORD = "database.password";
    public static final String Odoo_COMMON_URL = "odoo.common.url";
    public static final String Odoo_OBJECT_URL = "odoo.object.url";
    public static final String AUTHENTICATE = "authenticate";
    public static final String API_URL = "api.url";
    public static final int FIVE_MINUTES = 300000;
    public static final String FIELDS = "fields";
    private Integer uid;
    public static final String SEARCH_READ = "search_read";

    public static final String SEARCH = "search";
    public static final String CREATE = "create";
    public static final String EXECUTE_KW = "execute_kw";
    protected final Environment environment;
    private String apiUrl;
    private String username;
    private String password;
    private String databaseName;
    private String odooCommonUrl;
    private String odooObjectUrl;

    @Autowired
    public BaseClient(Environment environment) {
        this.environment = environment;
        this.getProperties();
    }

    private void getProperties() {
        apiUrl = this.environment.getProperty(API_URL);
        username = this.environment.getProperty(DATABASE_USERNAME);
        password = this.environment.getProperty(DATABASE_PASSWORD);
        databaseName = this.environment.getProperty(DATABASE_NAME);
        odooCommonUrl = this.environment.getProperty(Odoo_COMMON_URL);
        odooObjectUrl = this.environment.getProperty(Odoo_OBJECT_URL);
    }


    public abstract String getModel();

    private void login() {
        logger.info("Login to odoo: Database: [" + databaseName + "] Username: [" + username + "] Password: [" + password + "]");
        XmlRpcClient client = new XmlRpcClient();
        final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
        try {
            common_config.setServerURL(new URL(String.format(odooCommonUrl, apiUrl)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            this.uid = (int) client.execute(common_config, AUTHENTICATE, asList(databaseName, username, password, emptyMap()));
        } catch (XmlRpcException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer executeCommand(String modelName, String commandName, Object entity) {
        try {
            if (this.uid == null) {
                login();
            }
            logger.info("START " + commandName.toUpperCase() + " " + modelName + " [" + entity + "]");
            XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
            common_config.setServerURL(new URL(String.format(odooObjectUrl, apiUrl)));
            Integer createdEntityId = (Integer) client.execute(common_config, EXECUTE_KW, asList(databaseName, uid, password, modelName, commandName,
                    Collections.singletonList(entity)));
            logger.info("FINISH " + commandName.toUpperCase() + " " + modelName + " [" + entity + "]");
            return createdEntityId;
        } catch (XmlRpcException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
