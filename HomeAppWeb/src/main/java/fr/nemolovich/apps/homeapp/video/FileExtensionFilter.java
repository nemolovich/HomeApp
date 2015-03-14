/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.video;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author nemo
 */
public class FileExtensionFilter implements FileFilter {

    private final ConcurrentLinkedQueue<String> extensions;
    private boolean folderAllowed;

    public FileExtensionFilter() {
        this(new String[0]);
    }

    public FileExtensionFilter(boolean allowFodler) {
        this(new String[0], allowFodler);
    }

    public FileExtensionFilter(String... extensions) {
        this(Arrays.asList(extensions));
    }

    public FileExtensionFilter(String[] extensions, boolean allowFolder) {
        this(Arrays.asList(extensions), allowFolder);
    }

    public FileExtensionFilter(List<String> extensions) {
        this(extensions, false);
    }

    public FileExtensionFilter(List<String> extensions, boolean allowFolder) {
        this.extensions = new ConcurrentLinkedQueue<>(extensions);
        this.folderAllowed = false;
    }

    public List<String> getExtensions() {
        return new ArrayList<>(this.extensions);
    }

    public void allowFolder(boolean allowFolder) {
        this.folderAllowed = allowFolder;
    }

    public void addExtensions(String... extensions) {
        for (String ext : extensions) {
            this.addExtension(ext);
        }
    }

    public void addExtension(String extension) {
        this.extensions.add(extension);
    }

    @Override
    public boolean accept(File file) {
        boolean result = false;
        if (file.isDirectory()) {
            result = this.folderAllowed;
        } else if (file.isFile()) {
            for (String ext : this.extensions) {
                String extension = ext;
                if (extension.startsWith(".")) {
                    extension = extension.substring(1, extension.length());
                }
                if (file.getName().toLowerCase().endsWith(extension)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
