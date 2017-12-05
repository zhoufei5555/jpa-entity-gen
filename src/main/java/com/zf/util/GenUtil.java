package com.zf.util;

/**
 * Created by zhoufei on 2017/11/28.
 */
public class GenUtil {

    public static final String UP_FIRST ="UP_FIRST";
    public static final String UN_UP_FIRST ="UN_UP_FIRST";

    public static String  columnToJava(String columnName){
        return columnToJava(columnName,UN_UP_FIRST);
    }
    /**
     * 数据库列名转成java名
     * @param columnName
     * @return
     */
    public static String  columnToJava(String columnName,String type ){
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

    /**
     * 以空格补齐字符串长度到 指定的长度
     *
     * @param s
     * @param len 指定的长度
     * @return
     */
    public static String addSpace(String s, int len) {
        if(s==null){
            return null;
        }
        StringBuilder sb=new StringBuilder(s);
        int addCount=len-getWordCount(s);
        while(addCount>0){
            sb.append(" ");
            addCount--;
        }
        return sb.toString();
    }


    public static int getWordCount(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;

        }
        return length;
    }
}
