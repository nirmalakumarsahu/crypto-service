package com.sahu.cryptoservice.helper;

import com.sahu.cryptoservice.constant.FileConstant;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Component
public class FileWrapperHelper {

    public File wrapFilesAsGzip(List<File> files, String wrapperFileName) {
        Assert.notNull(files, "Files list must not be null");
        Assert.notEmpty(files, "Files list must not be empty");
        Assert.notNull(wrapperFileName, "Wrapper file name must not be null");

        try {
            File wrappedFile = new File(wrapperFileName + FileConstant.GZIP_FILE_SUFFIX.toString());

            try (FileOutputStream fos = new FileOutputStream(wrappedFile)) {
                fos.write(getByteArrayOutputStream(files));
            }

            return wrappedFile;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error wrapping files into GZIP: " + e.getMessage(), e);
        }
    }

    private byte[] getByteArrayOutputStream(List<File> files) throws IOException {
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream();
              ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }

            zos.finish();
            return baos.toByteArray();
        }
    }

}
