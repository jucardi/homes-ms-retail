package com.tenx.ms.retail.product.repositories;

import com.tenx.ms.retail.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByStoreIdAndProductId(long storeId, long productId);

    List<ProductEntity> findAllByStoreId(long productId);
}
