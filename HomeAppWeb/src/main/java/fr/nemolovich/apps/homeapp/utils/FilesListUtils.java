/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.utils;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author nemo
 */
public class FilesListUtils implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {

        int result = 0;
        if (f1 == null || f2 == null) {
            result = f1 == null ? f2 == null ? 0 : 1 : -1;
        } else if (f1.isDirectory()) {
            result = f2.isDirectory() ? f1.getName().compareTo(f2.getName())
                : -1;
        } else {
            result = f2.isDirectory() ? 1 : f1.getName().compareTo(f2.getName());
        }
        return result;
    }

}
