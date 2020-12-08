package com.devcircus.java.microservices.mes.catalogservice.api.v1;

import com.devcircus.java.microservices.mes.catalogservice.catalog.Catalog;
import com.devcircus.java.microservices.mes.catalogservice.catalog.CatalogInfo;
import com.devcircus.java.microservices.mes.catalogservice.catalog.CatalogInfoRepository;
import com.devcircus.java.microservices.mes.catalogservice.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
public class CatalogServiceV1 {

    private final CatalogInfoRepository catalogInfoRepository;
    private final RestTemplate restTemplate;

    /**
     *
     * @param catalogInfoRepository
     * @param restTemplate
     */
    @Autowired
    public CatalogServiceV1(CatalogInfoRepository catalogInfoRepository,
            @LoadBalanced RestTemplate restTemplate) {
        this.catalogInfoRepository = catalogInfoRepository;
        this.restTemplate = restTemplate;
    }

    /**
     *
     * @return
     */
    public Catalog getCatalog() {
        Catalog catalog;
        CatalogInfo activeCatalog = catalogInfoRepository.findCatalogByActive(true);
        catalog = restTemplate.getForObject(String.format("http://inventory-service/api/catalogs/search/findCatalogByCatalogNumber?catalogNumber=%s",
                activeCatalog.getCatalogId()), Catalog.class);
        ProductsResource products;
        products = restTemplate.getForObject(String.format("http://inventory-service/api/catalogs/%s/products",
                catalog.getId()), ProductsResource.class);
        catalog.setProducts(products.getContent().stream().collect(Collectors.toSet()));
        return catalog;
    }

    /**
     *
     * @param productId
     * @return
     */
    public Product getProduct(String productId) {
        return restTemplate.getForObject(String.format("http://inventory-service/v1/products/%s",
                productId), Product.class);
    }
}
