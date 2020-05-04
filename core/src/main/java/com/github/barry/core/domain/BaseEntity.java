package com.github.barry.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共实体类
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -8139929879209660401L;
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 核心企业ID
     */
    private Long companyId;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 创建人ID
     */
    private String createId;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 更新时间
     */
    private Date modified;
    /**
     * 修改人ID
     */
    private String updateId;
    /**
     * 修改人名称
     */
    private String updateName;
    /**
     * 是否有效 0：数据无效/逻辑删除， 1：数据有效 ，2：数据无效可归档， 3：数据无效可归档， 4：标记可物理删除
     */
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
