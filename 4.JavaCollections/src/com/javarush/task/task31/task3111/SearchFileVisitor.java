package com.javarush.task.task31.task3111;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName = null;
    private String partOfContent = null;
    private int minSize = Integer.MIN_VALUE;
    private int maxSize = Integer.MAX_VALUE;
    private List<Path> foundFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length

        boolean hasPartOfName = true;
        if (partOfName != null && !file.getFileName().toString().contains(partOfName)) {
            hasPartOfName = false;
        }

        boolean hasPartOfContent = true;
        String contentStr = new String(content);
        if (partOfContent != null && !contentStr.contains(partOfContent)) {
            hasPartOfContent = false;
        }

        boolean moreMinSize = true;
        if (content.length <= minSize) {
            moreMinSize = false;
        }

        boolean lessMaxSize = true;
        if (content.length >= maxSize) {
            lessMaxSize = false;
        }

        if (hasPartOfName && hasPartOfContent && moreMinSize && lessMaxSize) {
            foundFiles.add(file);
        }

        return super.visitFile(file, attrs);
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }
}
