package com.zf.classfile;

/**
 * Created by DELL on 2017/11/27.
 */
public class Column {
    private String name;

    private String dataType;

    private String columnComment;

    private int columnCommentLen;

    private int columnNameLen;

    public Column() {
    }

    public Column(String name, String dataType, String columnComment) {
        this.name = name;
        this.dataType = dataType;
        this.columnComment = columnComment;
    }

    public Column(String name, String dataType, String columnComment,int columnNameLen,int columnCommentLen) {
        this.name = name;
        this.dataType = dataType;
        this.columnComment = columnComment;
        this.columnNameLen = columnNameLen;
        this.columnCommentLen = columnCommentLen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", columnComment='" + columnComment + '\'' +
                '}';
    }
}
