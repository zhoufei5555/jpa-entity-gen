package com.zf.classfile;

import com.zf.dict.SqlTypeEnum;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/11/27.
 * 生成JPA实体类工具类
 */
public class JPAEntityGen {

    public static List<String> gen(List<Column> columnList, String tableName, String entityName, String packageName, String primaryKey, String author) throws SQLException {
        List<String> lineList = new ArrayList<>();
        //文件头部
        List<String> headerList = genHeader(tableName, entityName, packageName, columnList,author);
        // 文件体
        List<String> bodyList = genBody(columnList, primaryKey);

        lineList.addAll(headerList);
        lineList.addAll(bodyList);
        lineList.add("}");
        return lineList;

    }

    /**
     * 生成类文件的头部
     *
     * @param tableName
     */
    private static List<String> genHeader(String tableName, String entityName, String packageName, List<Column> columnList,String author) {
        List<String> headerList = new ArrayList<String>();
        headerList.add("package " + packageName + ";");
        headerList.add(" ");
        List<String> importList = new ArrayList<String>();
        headerList.add("import javax.persistence.*;");

        for (Column column : columnList) {
            String fullJaveType = SqlTypeEnum.getFullJavaTypeBySqlType(column.getDataType());
            if (!importList.contains(fullJaveType)) {
                importList.add(fullJaveType);
                headerList.add("import " + fullJaveType + ";");
            }
        }
        headerList.add("");
        headerList.add("/**");
        headerList.add("* Created by "+author+" on "+new SimpleDateFormat("YYYY/MM/dd").format(new Date())+".");
        headerList.add("*/");

        headerList.add("@Entity(name = \"" + tableName + "\")");
        headerList.add("public class " + entityName + " { ");
        headerList.add(" ");
        return headerList;
    }

    /**
     * 生成类体
     *
     * @param columnList
     * @return
     */
    private static List<String> genBody(List<Column> columnList, String primaryKey) {
        List<String> retList = new ArrayList<>();

        List<String> getSetMethod = new ArrayList<>();
        //注解及属性
        for (Column column : columnList) {
            String upFirstJavaName = ClassFileUtil.sqlNameToJavaName(column.getName(), ClassFileUtil.UP_FIRST);
            String unUpFirstJavaName = ClassFileUtil.sqlNameToJavaName(column.getName(), ClassFileUtil.UN_UP_FIRST);
            String dataType = SqlTypeEnum.getJavaTypeBySqlType(column.getDataType());

            // 注解
            if (primaryKey.equals(column.getName())) {
                retList.add("    @Id");
                retList.add("    @GeneratedValue(strategy = GenerationType.IDENTITY)");
            } else {
                retList.add("    /**");
                retList.add("    *"+column.getColumnComment());
                retList.add("    */");
                retList.add("    @Column(name = \"" + column.getName() + "\")");
            }

            //属性
            StringBuilder body = new StringBuilder();
            body.append("    private ");
            body.append(dataType);
            body.append(" ");
            body.append(unUpFirstJavaName + ";");
            retList.add(body.toString());
            // 留空行
            retList.add("");

            StringBuilder setMethodString = new StringBuilder();
            StringBuilder getMethodString = new StringBuilder();

            setMethodString.append("    public void");
            setMethodString.append(" set" + upFirstJavaName);
            setMethodString.append("(" + dataType + " ");
            setMethodString.append(unUpFirstJavaName + "){");
            setMethodString.append("this." + unUpFirstJavaName + " = " + unUpFirstJavaName + "; }");


            getMethodString.append("    public ");
            getMethodString.append(dataType);
            getMethodString.append(" get" + upFirstJavaName);
            getMethodString.append("(){");
            getMethodString.append("return " + unUpFirstJavaName + "; }");

            getSetMethod.add(setMethodString.toString());
            getSetMethod.add("  ");
            getSetMethod.add(getMethodString.toString());
            getSetMethod.add("  ");
        }
        //get set方法
        retList.addAll(getSetMethod);
        return retList;
    }



}
