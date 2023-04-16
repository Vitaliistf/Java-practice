package com.efimchick.ifmo.io.filetree;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class FileTreeImpl implements FileTree {

    @Override
    public Optional<String> tree(Path path) {
        if(path == null || !Files.exists(path)) {
            return Optional.empty();
        } else {
            StringBuilder result = tree(path.toFile(), new StringBuilder(), new StringBuilder());
            return Optional.of(result.toString());
        }
    }

    private StringBuilder tree(File currentFile, StringBuilder result, StringBuilder intend) {

        result.append(getFileDescription(currentFile));

        File[] files = currentFile.listFiles();
        if (files == null) {
            return result;
        }
        Arrays.sort(files, this::compareFiles);

        for (File file : files) {
            result.append(intend);
            if(file.isDirectory()) {
                if(file.equals( files[files.length-1] )) {
                    intend.append("   ");
                } else {
                    intend.append("│  ");
                }
            }

            if(file.equals( files[files.length-1] )){
                result.append("└─ ");
            } else {
                result.append("├─ ");
            }

            tree(file, result, intend);
        }
        if(intend.length()-3 >= 0) {
            intend.delete(intend.length() - 3, intend.length());
        }
        return result;
    }

    private int compareFiles(File o1, File o2) {
        int o1Dir = o1.isDirectory() ? 1 : 0;
        int o2Dir = o2.isDirectory() ? 1 : 0;
        String o1Name = o1.getName().toLowerCase();
        String o2Name = o2.getName().toLowerCase();

        if(o2Dir - o1Dir != 0) {
            return o2Dir - o1Dir;
        } else {
            return o1Name.compareTo(o2Name);
        }
    }

    private String getFileDescription(File file) {
        return file.getName() + " " + getFileSize(file) + " bytes\n";
    }

    private long getFileSize(File file) {
        long length = 0;
        File[] files = file.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile())
                    length += f.length();
                else
                    length += getFileSize(f);
            }
        } else {
            length = file.length();
        }

        return length;
    }
}
