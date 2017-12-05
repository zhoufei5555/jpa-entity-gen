package com.zf.util;

import java.io.*;
import java.util.List;

/**
 * Created by zhoufei on 2017/11/28.
 */
public class FileUtil {

    public static void writerFile(String fileName ,List<String> data) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String s : data) {
            pw.println(s);
        }
        pw.flush();
        pw.close();
    }
}
