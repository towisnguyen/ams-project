package com.r23.ams.utils;

import com.r23.ams.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageFileValidation {
    private ImageFileValidation() {
        throw new IllegalStateException("Utility class");
    }
    private static final int MAX_FILE_SIZE = 8000000;
    public static void validate(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(file.getSize() > MAX_FILE_SIZE){
            throw new BadRequestException("File is not more than 8MB");
        }
        if (fileName == null || "".equals(fileName)) {
            throw new BadRequestException("You need to select file");
        }
        Pattern pattern = Pattern.compile("^(.+(doc|dot|xls|xlt|xla|pdf|ppt|pps|pot|ppa|sld)+(|x|m))$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new BadRequestException("Type file is not valid");
        }
    }

    public static void validateImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(file.getSize() > MAX_FILE_SIZE){
            throw new BadRequestException("File not more than 8MB");
        }
        if (fileName == null || "".equals(fileName)) {
            throw new BadRequestException("You need to select file");
        }
        Pattern pattern = Pattern.compile("^(.+\\.(svg|gif|jpe?g|tiff?|png|webp|bmp))$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new BadRequestException("Type image is not valid");
        }
    }
}
