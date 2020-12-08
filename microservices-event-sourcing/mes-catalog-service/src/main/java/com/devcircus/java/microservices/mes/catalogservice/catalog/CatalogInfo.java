package com.devcircus.java.microservices.mes.catalogservice.catalog;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class CatalogInfo implements Serializable {

    @Id
    private String id;
    private Long catalogId;
    private Boolean active;

    /**
     * 
     */
    public CatalogInfo() {
        id = UUID.randomUUID().toString();
        active = false;
    }

    /**
     * 
     * @param catalogId 
     */
    public CatalogInfo(Long catalogId) {
        this();
        this.catalogId = catalogId;
    }

    /**
     * 
     * @param catalogId
     * @param active 
     */
    public CatalogInfo(Long catalogId, Boolean active) {
        this(catalogId);
        this.active = active;
    }

    /**
     * 
     * @return 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public Long getCatalogId() {
        return catalogId;
    }

    /**
     * 
     * @param catalogId 
     */
    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * 
     * @return 
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     * @param active 
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "CatalogInfo{"
                + "id='" + id + '\''
                + ", catalogId=" + catalogId
                + ", active=" + active
                + '}';
    }

    /**
     * 
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CatalogInfo that = (CatalogInfo) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (catalogId != null ? !catalogId.equals(that.catalogId) : that.catalogId != null) {
            return false;
        }
        return active != null ? active.equals(that.active) : that.active == null;

    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (catalogId != null ? catalogId.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }
}
