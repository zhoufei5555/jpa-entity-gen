package com.zf.classfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/11/27.
 */
public class JPARepositoryGen {


    public static List<String> gen(String entityName, String packageName, String entityPackageName, String author) {
        List<String> lineList = new ArrayList<>();
        lineList.add("package " + packageName + ";");
        lineList.add("import "+entityPackageName+"."+entityName+";");
        lineList.add("import org.springframework.data.jpa.repository.JpaRepository;");
        lineList.add("import org.springframework.data.jpa.repository.JpaRepository;");
        lineList.add("import org.springframework.data.jpa.repository.JpaSpecificationExecutor;");
        lineList.add(" ");
        lineList.add("/**");
        lineList.add("* Created by "+author+" on "+new SimpleDateFormat("YYYY/MM/dd").format(new Date())+".");
        lineList.add("*/");
        lineList.add("public interface "+entityName+"Repository extends JpaRepository<"+entityName+", Integer>, JpaSpecificationExecutor<"+entityName+"> {");
        lineList.add("}");

        return lineList;
    }
}
