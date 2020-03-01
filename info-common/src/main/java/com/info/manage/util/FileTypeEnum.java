package com.info.manage.util;

public enum  FileTypeEnum {
    PDF("pdf"),
    DOC("doc"),
    DOCX("docx"),
    XLS("xls"),
    XLSX("xlsx"),
    PPT("ppt"),
    PPTX("pptx");

    String value ;
    FileTypeEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
