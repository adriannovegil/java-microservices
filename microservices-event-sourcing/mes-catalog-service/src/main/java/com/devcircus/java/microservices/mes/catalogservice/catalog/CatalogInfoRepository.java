package com.devcircus.java.microservices.mes.catalogservice.catalog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CatalogInfoRepository extends JpaRepository<CatalogInfo, String> {

    /**
     * 
     * @param active
     * @return 
     */
    CatalogInfo findCatalogByActive(@Param("active") Boolean active);
}
