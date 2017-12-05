package com.zf.classfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/11/27.
 */
public class JPAFormGen {

    public static List<String> gen( String entityName, String entityPackageName,String formPackageName , String author) {
        List<String> lineList = new ArrayList<>();
        //文件头部
        List<String> headerList = genHeader(entityName, entityPackageName,formPackageName,author);
        // 文件体

        lineList.addAll(headerList);
        lineList.add("}");
        return lineList;
    }

    /**
     * 生成文件头部
     * @param entityName
     * @param entityPackageName
     * @param author
     * @return
     */
    private static List<String> genHeader(String entityName, String entityPackageName,String formPackageName, String author) {
        List<String> headerList = new ArrayList<String>();
        headerList.add("package " + formPackageName + ";");
        headerList.add(" ");
        headerList.add("import "+entityPackageName+"."+entityName+";");
        headerList.add(" ");
        headerList.add("/**");
        headerList.add("* Created by " + author + " on " + new SimpleDateFormat("YYYY/MM/dd").format(new Date()) + ".");
        headerList.add("*/");
        headerList.add("public class "+entityName+"Form extends "+entityName+" {");
        return headerList;
    }
}
