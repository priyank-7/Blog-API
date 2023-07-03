package com.blog.Services.Impl;

import com.blog.Services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File Name
        String name = file.getOriginalFilename();

        // Random name generate file
        String fileName1 = UUID.randomUUID().toString().concat(name.substring(name.lastIndexOf(".")));

        // Full Path
        String fullPath = path + File.separator + fileName1;

        // Create folder If not created
        File f = new File(path);
        if (!f.exists()){
            f.mkdir();
        }

        // File Copy
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);
        // DB logic to return inputstream
        return is;
    }
}
