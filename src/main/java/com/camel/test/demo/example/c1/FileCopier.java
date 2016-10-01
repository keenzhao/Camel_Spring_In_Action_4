package com.camel.test.demo.example.c1;

import java.io.*;

/**
 * 文件拷贝的类例子
 * <p>
 * Created by keen.zhao on 2016/10/1.
 */
public class FileCopier {

    public static void main(String[] args) throws IOException {
        File inBoxDirectory = new File("data/inbox");
        File outBoxDirectory = new File("data/outbox");
        outBoxDirectory.mkdir();

        File[] files = inBoxDirectory.listFiles();
        for (File source : files) {
            if (source.isFile()) {
                File dest = new File(outBoxDirectory.getPath()
                        + File.separator + source.getName());
                copyFile(source, dest);
            }
        }
    }

    private static void copyFile(File source, File dest) throws IOException {

        OutputStream out = new FileOutputStream(dest);
        byte[] buffer = new byte[((int) source.length())];
        InputStream in = new FileInputStream(source);

        in.read(buffer); //从输入文件数据读入buffer

        try {
            out.write(buffer); //把buffer写入到输出文件
        } finally {
            out.close();
            in.close();
        }
    }
}
