package com.example.springbootdemo.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.*;
import java.util.*;

@Controller
public class FileUploadTest {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @PostMapping("/upload")
    public List<String> upload(MultipartFile[] uploadFiles, HttpServletRequest req) {
        List<String> fileUrls = new ArrayList<>();
        //遍历文件
        for (MultipartFile file : uploadFiles) {
            String realPath =
                    req.getSession().getServletContext().getRealPath("/uploadFile/");
            String format = sdf.format(new Date());
            File folder = new File(realPath + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
            String oldName = file.getOriginalFilename();
            String newName = UUID.randomUUID().toString() +
                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                file.transferTo(new File(folder, newName));
                String filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                        req.getServerPort() + "/uploadFile/" + format + newName;
                fileUrls.add(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileUrls;
    }
}