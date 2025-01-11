package com.r23.ams.file;


import com.r23.ams.exception.BadRequestException;
import com.r23.ams.exception.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.*;


@Service
public class FilesStorageService {

    private static String os = System.getProperty("os.name").toLowerCase();

    public String getRootPath() {
        if (isWindows() || isUnix()) {
            return "./home/static";
        } else {
            throw new BadRequestException("Cannot set root path because of Unknown OS");
        }
    }

    private Path convertRelativeToAbsolutePath(String relativePath) {
        return Paths.get(getRootPath() + relativePath);
    }

    public void saveAs(MultipartFile file, String relativePath) {
        try {
            File directory = new File(convertRelativeToAbsolutePath(relativePath).getParent().toString()); //check folder cha đã có chưa

            if (!directory.exists()) {
                directory.mkdirs();
            }

            InputStream stream = file.getInputStream();
            Files.copy(stream, convertRelativeToAbsolutePath(relativePath)); //save file
            stream.close();//Cannot delete Tomcat temporary directories issues
        } catch (Exception e) {
            throw new BadRequestException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = convertRelativeToAbsolutePath(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BadRequestException("Could not read the file !");
            }
        } catch (Exception e) {
            throw new BadRequestException("Error: " + e.getMessage());
        }

    }
    public Resource loadFileFromResource(String filename) {
        try {
            File filePath = new File(this.getClass().getClassLoader().getResource(filename).getFile());
            Resource resource = new UrlResource(filePath.toURI());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BadRequestException("Could not read the file !");
            }
        } catch (Exception e) {
            throw new BadRequestException("Error: " + e.getMessage());
        }

    }

    public boolean deleteFile(String relativePath) throws NotFoundException {

        try {
            File directory = new File(convertRelativeToAbsolutePath(relativePath).toString());

            return directory.delete();
        } catch (Exception e) {
            throw new NotFoundException("File is not found");
        }

    }

    private boolean isWindows() {
        return os.contains("win");
    }

    private Boolean isUnix() {
        return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
    }

}

