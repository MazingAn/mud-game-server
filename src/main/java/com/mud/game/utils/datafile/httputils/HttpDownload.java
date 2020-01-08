package com.mud.game.utils.datafile.httputils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpDownload {

    public static void download(String filePath, HttpServletResponse response) throws UnsupportedEncodingException {
        if (filePath != null){
            File downloadFile = new File(filePath);
            String fileName = downloadFile.getName();
            if (downloadFile.exists()){
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso8859-1"));

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try{
                    fis = new FileInputStream(downloadFile);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1){
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
