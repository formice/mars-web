package com.formice.mars.web.model.vo;

public class InputAndOutputVo {
    private Long id;
    private String name;

    private Long toolId;
    private String cate;
    private String type;
    private Integer minNum;
    private Integer maxNum;
    private String fileFormat;
    private String fileSplitSymbol;
    private String desc;
    private String prefix;
    private String prefixSplitSymbol;
    private String isMust;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileSplitSymbol() {
        return fileSplitSymbol;
    }

    public void setFileSplitSymbol(String fileSplitSymbol) {
        this.fileSplitSymbol = fileSplitSymbol;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixSplitSymbol() {
        return prefixSplitSymbol;
    }

    public void setPrefixSplitSymbol(String prefixSplitSymbol) {
        this.prefixSplitSymbol = prefixSplitSymbol;
    }

    public String getIsMust() {
        return isMust;
    }

    public void setIsMust(String isMust) {
        this.isMust = isMust;
    }
}
