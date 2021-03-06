package com.tenx.ms.retail.stock.domain;

import java.io.Serializable;
import java.util.Objects;


public class StockEntityPK implements Serializable {
    private Long productId;
    private Long storeId;

    public Long getProductId() { return this.productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getStoreId() { return this.storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
}
