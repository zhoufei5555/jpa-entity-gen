package com.zf.dict;

/**
 * Created by DELL on 2017/11/27.
 */
public enum SqlTypeEnum {

    VARCHAR("VARCHAR","String", "java.lang.String"),
    CHAR("CHAR","String" ,"java.lang.String"),
    BLOB("BLOB","byte[]","java.lang.byte"),
    TEXT("TEXT","String","java.lang.String"),
    INT("INT","Integer","java.lang.Integer"),
    INTEGER("INTEGER","Integer","java.lang.Integer"),
    TINYINT("TINYINT","Integer","java.lang.Integer"),
    SMALLINT("SMALLINT","Integer","java.lang.Integer"),
    MEDIUMINT("MEDIUMINT","Integer","java.lang.Integer"),
    BIT("BIT","Boolean","java.lang.Boolean"),
    BIGINT("BIGINT","BigInteger","java.math.BigInteger"),
    FLOAT("FLOAT","Float","java.lang.Float"),
    DOUBLE("DOUBLE","Double","java.lang.Double"),
    DECIMAL("DECIMAL","BigDecimal","java.math.BigDecimal"),
    DATE("DATE","Timestamp","java.lang.Long"),
    DATETIME("DATETIME","Timestamp","java.sql.Timestamp"),
    TIMESTAMP("TIMESTAMP","Timestamp","java.sql.Timestamp"),
    TIME("TIME","Timestamp","java.sql.Timestamp"),
    YEAR("YEAR","Timestamp","java.sql.Timestamp");


    String sqlType;

    String javaType;

    String fullJavaType;


    SqlTypeEnum(String sqlType, String javaType, String fullJavaName) {
        this.sqlType = sqlType;
        this.javaType = javaType;
        this.fullJavaType = fullJavaName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getFullJavaType() {
        return fullJavaType;
    }

    public static String getJavaTypeBySqlType(String sqlType){
        for(SqlTypeEnum sqlTypeEnum:values()){
            if(sqlTypeEnum.getSqlType().equals(sqlType)){
                return sqlTypeEnum.getJavaType();
            }
        }
        return "";
    }

    public static String getFullJavaTypeBySqlType(String sqlType){
        for(SqlTypeEnum sqlTypeEnum:values()){
            if(sqlTypeEnum.getSqlType().equals(sqlType)){
                return sqlTypeEnum.getFullJavaType();
            }
        }
        return "";
    }
}
