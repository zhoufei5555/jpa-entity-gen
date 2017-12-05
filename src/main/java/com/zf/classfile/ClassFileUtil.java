package com.zf.classfile;

/**
 * Created by DELL on 2017/11/27.
 */
public class ClassFileUtil {

    public static final String UP_FIRST ="UP_FIRST";
    public static final String UN_UP_FIRST ="UN_UP_FIRST";




    /**
     * 数据库列名转成java名
     * @param columnName
     * @return
     */
    public static String  sqlNameToJavaName(String columnName,String type ){
        String retString = "";
        int count = 0;
        for(String s :columnName.split("_")){
            if(count++ == 0 && UN_UP_FIRST.equals(type)){
                retString += s;
                continue;
            }
            retString += toUpCaseFirst(s);
        }

        return retString;
    }



    public static String  toUpCaseFirst(String s){
        String retString = "";
        String []  arr = s.split("");
        arr[0] = arr[0].toUpperCase();
        for(String s1:arr){
            retString += s1;
        }
        return retString;
    }

    public static String  toLowCaseFirst(String s){
        String retString = "";
        String []  arr = s.split("");
        arr[0] = arr[0].toLowerCase();
        for(String s1:arr){
            retString += s1;
        }
        return retString;
    }
}
