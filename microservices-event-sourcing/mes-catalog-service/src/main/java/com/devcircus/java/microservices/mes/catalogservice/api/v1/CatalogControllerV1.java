package com.devcircus.java.microservices.mes.catalogservice.api.v1;

import com.devcircus.java.microservices.mes.catalogservice.catalog.Catalog;
import com.devcircus.java.microservices.mes.catalogservice.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CatalogControllerV1 {

    private final CatalogServiceV1 catalogService;

    /**
     * 
     * @param catalogService 
     */
    @Autowired
    public CatalogControllerV1(CatalogServiceV1 catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * 
     * @return 
     */
    @RequestMapping(path = "/catalog", method = RequestMethod.GET, name = "getCatalog")
    public ResponseEntity<Catalog> getCatalog() {
        return Optional.ofNullable(catalogService.getCatalog())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 
     * @param productId
     * @return 
     */
    @RequestMapping(path = "/products/{productId}", method = RequestMethod.GET, name = "getProduct")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        return Optional.ofNullable(catalogService.getProduct(productId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
