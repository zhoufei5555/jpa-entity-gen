package com.zf.classfile;

import com.zf.util.FileUtil;
import com.zf.util.JdbcUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhoufei on 2017/11/27.
 * 生成数据库的代码
 */
public class ClassFileGen {

    static {
        System.out.println("调用ClassFileGen静态块");
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"\\config\\genClassFile.properties"),"UTF-8"));
        } catch (IOException e) {
            System.err.println("读取配置文件失败，fileName:genClassFile.properties");
            e.printStackTrace();
        }
        ClassFileGen.BASE_DIR = properties.getProperty("BASE_DIR");
        ClassFileGen.AUTHOR = properties.getProperty("AUTHOR");
        ClassFileGen.DATABASE_NAME = properties.getProperty("DATABASE_NAME");
        ClassFileGen.TABLE_NAME = properties.getProperty("TABLE_NAME");
        ClassFileGen.ENTITY_NAME = properties.getProperty("ENTITY_NAME");
        ClassFileGen.ENTITY_PACKAGE_NAME = properties.getProperty("ENTITY_PACKAGE_NAME");
        ClassFileGen.REPOSITORY_PACKAGE_NAME = properties.getProperty("REPOSITORY_PACKAGE_NAME");
        ClassFileGen.FORM_PACKAGE_NAME = properties.getProperty("FORM_PACKAGE_NAME");
        ClassFileGen.PRIMARY_KEY = properties.getProperty("PRIMARY_KEY");
    }

    // 生成文件所在路径
    public static String BASE_DIR;
    // 创建类的作者
    public static String AUTHOR;
    // 数据库名
    public static String DATABASE_NAME;
    // 表名
    public static String TABLE_NAME;
    // 实体类名  （不填自动替换为表名的驼峰）
    public static String ENTITY_NAME;
    // 实体类包名
    public static String ENTITY_PACKAGE_NAME;
    // JPA数据库接口所在包名
    public static String REPOSITORY_PACKAGE_NAME;
    // form所在包名
    public static String FORM_PACKAGE_NAME;

    // 主键ID
    public static String PRIMARY_KEY;

    public static final String ENTITY = "ENTITY";

    public static final String REPOSITORY = "REPOSITORY";

    public static final String FORM = "FORM";


    public static void gen() throws SQLException {
        if (null == ClassFileGen.ENTITY_NAME || "".equals(ClassFileGen.ENTITY_NAME)) {
            ENTITY_NAME = ClassFileUtil.sqlNameToJavaName(ClassFileGen.TABLE_NAME, ClassFileUtil.UP_FIRST);
        }

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = JdbcUtils.getConn("information_schema");
            statement = conn.createStatement();
            String columnSql = "SELECT  UPPER(DATA_TYPE) AS dataType,COLUMN_COMMENT AS columnComment,COLUMN_NAME AS columnName  " +
                    "FROM  information_schema.COLUMNS WHERE table_schema='" + DATABASE_NAME + "' and table_name ='" + TABLE_NAME + "'";
            resultSet = statement.executeQuery(columnSql);
            // 所有列的集合
            List<Column> columnList = new ArrayList<>();
            while (resultSet.next()) {
                columnList.add(new Column(resultSet.getString("columnName"), resultSet.getString("dataType"), resultSet.getString("columnComment")));
            }
            // 实体类的行
            List<String> entityLineList = JPAEntityGen.gen(columnList, TABLE_NAME, ENTITY_NAME, ENTITY_PACKAGE_NAME, PRIMARY_KEY, AUTHOR);
            // Repository类的行
            List<String> repositoryLineList = JPARepositoryGen.gen(ENTITY_NAME, REPOSITORY_PACKAGE_NAME, ENTITY_PACKAGE_NAME, AUTHOR);
            // form 的行
            List<String> formLineList = JPAFormGen.gen(ENTITY_NAME, ENTITY_PACKAGE_NAME, FORM_PACKAGE_NAME, AUTHOR);


            createFile(ENTITY_PACKAGE_NAME, entityLineList, ENTITY);
            createFile(REPOSITORY_PACKAGE_NAME, repositoryLineList, REPOSITORY);
            createFile(FORM_PACKAGE_NAME, formLineList, FORM);
            System.out.println("------------类文件生成完成-------------");
            System.out.println("------------              -------------");
            System.out.println("------------开始生成接口文档-------------");
//            DocGen.gen();
            System.out.println("------------生成接口文档生成完成-------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
            statement.close();
            resultSet.close();
        }
    }

    /**
     * 生成java文件
     *
     * @param packageName
     * @param lineList
     * @param type
     * @throws IOException
     */
    private static void createFile(String packageName, List<String> lineList, String type) throws IOException {
        File baseFile = new File(BASE_DIR);
        // 如果生成的目录不存在  需要创建相应的目录
        if (!baseFile.exists() || !baseFile.isDirectory()) {
            baseFile.mkdir();
        }
        String[] arr = packageName.split("\\.");
        String filePackageName = "\\";
        for (String s : arr) {
            filePackageName += s + "\\";
        }
        String fileName = BASE_DIR + filePackageName + getFileName(type);
        System.out.println("类代码正在写入 ：" + fileName);
        FileUtil.writerFile(fileName,lineList);
    }

    // 根据响应的包名来获取对应的文件名
    private static String getFileName(String key) {
        String name = "";
        do {
            if (ENTITY == key) {
                name = ENTITY_NAME;
                break;
            }
            if (REPOSITORY == key) {
                name = ENTITY_NAME + "Repository";
                break;
            }
//            if (SERVICE == key) {
//                name = ENTITY_NAME + "Service";
//                break;
//            }
//            if (CONTROLLER == key) {
//                name = ENTITY_NAME + "Controller";
//                break;
//            }
            if (FORM == key) {
                name = ENTITY_NAME + "Form";
                break;
            }
        } while (false);

        return name + ".java";

    }


    public static void main(String[] args) {
        try {
            gen();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println(ANNONATION_REMARK);


    }


//    /**
//     * 生成文件所在地
//     */
//    public static  String BASE_DIR = "C:\\Users\\DELL\\Desktop\\testCreateFile";
//    /**
//     * 创建类的作者
//     */
//    public static  String AUTHOR = "zhoufei";
//    /**
//     * 数据库名
//     */
//    public static  String DATABASE_NAME = "water-tank2";
//    /**
//     * 表名
//     */
//    public static  String TABLE_NAME = "wt_disinfector";
//    /**
//     * 注解描述
//     */
//    public static  String ANNONATION_REMARK = "消毒剂";
//    /**
//     * 实体类名  （不填自动替换为表名的驼峰）
//     */
//    public static  String ENTITY_NAME = "";
//    /**
//     * 生成实体类所在包名
//     */
//    public static  String ENTITY_PACKAGE_NAME = "com.szdxit.sws.operation.jpa.orm";
//    /**
//     * 生成JPA所在包名
//     */
//    public static  String REPOSITORY_PACKAGE_NAME = "com.szdxit.sws.operation.jpa.repository";
//    /**
//     * 生成service所在包名
//     */
//    public static  String SERVICE_PACKAGE_NAME = "com.szdxit.sws.operation.jpa.service";
//    /**
//     * 生成Controller所在包名
//     */
//    public static  String CONTROLLER_PACKAGE_NAME = "com.szdxit.sws.operation.web.controller";
//    /**
//     * 生成Form所在的包名
//     */
//    public static  String FORM_PACKAGE_NAME = "com.szdxit.sws.operation.form";
//    /**
//     * 主键
//     */
//    public static String PRIMARY_KEY = "id";


}
