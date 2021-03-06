package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * Abstract class with utilities more specific to this project.
 */
public abstract class AbstractRetailTest extends AbstractTest {

    protected final static String API_VERSION = RestConstants.VERSION_ONE;
    protected static final long   INVALID_ID  = 9999999;

    /*
     *   The URLs for all APIs in the project.
     */

    protected static String STORE_REQUEST_URI   = "%s" + API_VERSION + "/stores/";
    protected static String PRODUCT_REQUEST_URI = "%s" + API_VERSION + "/products/";
    protected static String STOCK_REQUEST_URI   = "%s" + API_VERSION + "/stock/";
    protected static String ORDERS_REQUEST_URI  = "%s" + API_VERSION + "/orders/";

    /*
     *   The payloads of all successful creation of entities which are the ones that can be reused in all tests.
     */

    @Value("classpath:store/success/create.json")
    protected File createStoreSuccess;
    @Value("classpath:product/success/create.json")
    protected File createProductSuccess;

    /*
     *   The following methods are in this abstract class to make them available for all tests since they all rely on creating and fetching
     *   Stores and Products
     */

    // Store Helpers

    protected Long createStore() {
        Long  storeId = createStore(createStoreSuccess, HttpStatus.OK);
        Store store   = getStore(storeId, HttpStatus.OK);
        assertNotNull("Store cannot be null", store);
        assertEquals("Store ids mismatch", store.getStoreId(), storeId);
        return storeId;
    }

    protected Long createStore(File data, HttpStatus expectedStatus) {
        ResourceCreated<Long> response = request(String.format(STORE_REQUEST_URI, basePath()), data, HttpMethod.POST, expectedStatus, new TypeReference<ResourceCreated<Long>>() {});
        return response != null ? response.getId() : null;
    }

    protected Store getStore(long storeId, HttpStatus expectedStatus) {
        return request(String.format(STORE_REQUEST_URI, basePath()) + storeId, (String) null, HttpMethod.GET, expectedStatus, new TypeReference<Store>() {});
    }

    protected List<Store> getAllStores() {
        return request(String.format(STORE_REQUEST_URI, basePath()), (String) null, HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Store>>() {});
    }


    // Product Helpers

    protected Long createProduct(long storeId) {
        Long    productId = createProduct(storeId, createProductSuccess, HttpStatus.OK);
        Product product   = getProduct(storeId, productId, HttpStatus.OK);
        assertNotNull("Product cannot be null", product);
        assertEquals("Product ids mismatch", product.getProductId(), productId);
        return productId;
    }

    protected Long createProduct(long storeId, File data, HttpStatus expectedStatus) {
        ResourceCreated<Long> response = request(String.format(PRODUCT_REQUEST_URI, basePath()) + storeId, data, HttpMethod.POST, expectedStatus, new TypeReference<ResourceCreated<Long>>() {});
        return response != null ? response.getId() : null;
    }

    protected Product getProduct(long storeId, long productId, HttpStatus expectedStatus) {
        return request(String.format(PRODUCT_REQUEST_URI, basePath()) + storeId + "/" + productId, (String) null, HttpMethod.GET, expectedStatus, new TypeReference<Product>() {});
    }

    protected List<Product> getAllProducts(long storeId) {
        return request(String.format(PRODUCT_REQUEST_URI, basePath()) + storeId, (String) null, HttpMethod.GET, HttpStatus.OK, new TypeReference<List<Product>>() {});
    }
}
