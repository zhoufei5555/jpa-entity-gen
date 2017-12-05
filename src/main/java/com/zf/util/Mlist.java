package com.zf.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhoufei on 2017/11/28.
 */
public class Mlist{
    private List<String> list = new ArrayList<>();

    public void addWithLine(String s){
        this.list.add(s);
        this.list.add(" ");
    }

    public void add(String s){
        this.list.add(s);
    }

    public void addAll(Collection c){
        this.list.addAll(c);
    }

    public void addAllWithLine(Collection c){
        this.list.addAll(c);
        this.list.add(" ");

    }

    public void addWithTab(String s){
        this.list.add("    "+s);
    }
    public void addWithDbTab(String s){
        this.list.add("        "+s);
    }
    public void addWithTab(String s,int count){
        this.list.add(s);
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
