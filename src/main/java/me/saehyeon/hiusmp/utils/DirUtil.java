package me.saehyeon.hiusmp.utils;

import java.io.File;

public class DirUtil {
    public static void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // 하위 디렉토리 재귀 호출
                } else {
                    file.delete(); // 파일 삭제
                }
            }
        }
        dir.delete(); // 비워진 디렉토리 삭제
    }
}
