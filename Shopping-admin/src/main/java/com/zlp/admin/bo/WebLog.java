package com.zlp.admin.bo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class WebLog implements Serializable {

    private static final long serialVersionUID = -8902900245630136558L;
    
    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "操作用户")
    private String userName;

    @ApiModelProperty(value = "操作时间")
    private Long startTime;

    @ApiModelProperty(value = "消耗时间")
    private Integer spendTime;

    @ApiModelProperty(value = "根路径")
    private String basePath;

    @ApiModelProperty(value = "uri")
    private String uri;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "请求类型")
    private String method;

    @ApiModelProperty(value = "ip")
    private String ip;

    private Object parameter;

    private Object result;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
