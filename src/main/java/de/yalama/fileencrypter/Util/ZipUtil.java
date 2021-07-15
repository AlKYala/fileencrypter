package de.yalama.fileencrypter.Util;

import de.yalama.fileencrypter.Exceptions.FileNameException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static ZipOutputStream getZipOutputStreamForMultipleFiles(String[][] fileNames, String targetZipFilename) throws IOException, FileNameException {
        FileOutputStream fos = new FileOutputStream(String.format("%s.zip", targetZipFilename));
        ZipOutputStream zos = new ZipOutputStream(fos);
        for(int i = 0; i < fileNames.length; i++) {
            if(!ZipUtil.checkIsFileNameIntact(fileNames[i])) {
                throw new FileNameException("Filename not complete - check data for missing information");
            }
            zos.putNextEntry(ZipUtil.fileToZipEntry(fileNames[i][0], fileNames[i][1]));
        }
        zos.closeEntry();
        return zos;
    }

    private static boolean checkIsFileNameIntact(String[] fileNameWithExtension) {
        return fileNameWithExtension != null && fileNameWithExtension.length == 2;
    }

    public static ZipEntry fileToZipEntry(String fileName, String fileExtension) {
        return new ZipEntry(FileUtil.fileToFSR(fileName, fileExtension).getFilename());
    }

    public static ZipEntry fileToZipEntry(String fileName) {
        return new ZipEntry(FileUtil.fileToFSR(fileName).getFilename());
    }
}
