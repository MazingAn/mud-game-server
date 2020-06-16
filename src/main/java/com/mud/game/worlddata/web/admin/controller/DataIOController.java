package com.mud.game.worlddata.web.admin.controller;

import com.mud.game.server.ServerManager;
import com.mud.game.utils.datafile.dateutils.AmzCalendar;
import com.mud.game.utils.datafile.exportor.Exportor;
import com.mud.game.utils.datafile.exportor.ExportorFatory;
import com.mud.game.utils.datafile.httputils.HttpDownload;
import com.mud.game.utils.datafile.importor.Importor;
import com.mud.game.utils.datafile.importor.ImportorFactor;
import com.mud.game.utils.datafile.pathutils.PathUtils;
import com.mud.game.utils.datafile.ziputils.ZipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("file")
@Api(tags = "B:文件管理和系统维护接口")
public class DataIOController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.upload.url}")
    private String uploadPath;
    @Value("${file.download.url}")
    private String outputPath;
    @Value("${file.icon.url}")
    private String iconPath;


    /*
     * 上传文件并写入数据库
     * @Param：files 前端文件集合
     */
    @PostMapping("/upload")
    @ApiOperation("上传游戏数据文件")
    @ApiImplicitParam(name = "files", value = "要上传的文件")
    public ResponseEntity httpUpload(@RequestParam("files") MultipartFile[] files) throws JSONException {
        JSONObject object = new JSONObject();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String filePath = uploadPath + '/' + fileName;
            try {
                //写入文件到数据库
                assert fileName != null;
                Importor importor = ImportorFactor.createImportor(file.getInputStream(), fileName);
                importor.ToDataBase();
                //保存一份文件到服务器备份
                File dest = new File(filePath);
                if (!dest.exists()) dest.mkdirs();
                file.transferTo(dest);
            } catch (Exception e) {
                System.out.println(fileName + "在上传的时候发生错误！");
                e.printStackTrace();
                return ResponseEntity.ok(-1);
            }
        }
        return ResponseEntity.ok(0);
    }


    /*
     * 下载指定游戏世界数据库文件
     * @Param：type 下载的文件类型
     * @Param：tableNmae 下载的表名
     */
    @GetMapping("/download")
    @ApiOperation("下载文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableName", value = "要下载的数据名称,参考模型名称"),
            @ApiImplicitParam(name = "type", value = "下载的文件格式，可选csv|xls|xlsx")
    })
    public void httpDownload(@RequestParam("tableName") String tableName,
                               @RequestParam("type") String type,
                               HttpServletResponse response) throws UnsupportedEncodingException {
        //生成文件
        Exportor exportor = ExportorFatory.createExportor(outputPath+'/'+type, tableName, type);
        if(exportor==null){
            logger.warn("在下载"+tableName+"的时候出现了例外情况:没有找到对应的表");
        }
        assert exportor!=null;
        String filePath = exportor.toFile();
        HttpDownload.download(filePath, response);
    }


    /*
     * 下载所有游戏世界数据库文件
     * @Param：type 下载的文件类型
     */
    @GetMapping("/download/all")
    @ApiOperation("下载所有文件，返回zip压缩包")
    @ApiImplicitParam(name = "type", value = "下载的文件格式，可选csv|xls|xlsx")
    public void httpDownloadAll(@RequestParam("type") String type, HttpServletResponse response) throws UnsupportedEncodingException {
        //获得所有的模型名称（要下载数据库所有表)
        List<String> tableNames = PathUtils.getAllEntitiesName("com.mud.game.worlddata.db.models");
        for (String tableName: tableNames) {
            // 生成所有文件
            Exportor exportor = ExportorFatory.createExportor(outputPath+'/'+type, tableName, type);
            if(exportor == null) {
                System.out.println("在下载所有数据库文件的时候出现了例外情况：" + tableName +"没有找到对应的表");
                logger.warn("在下载所有数据库文件的时候出现了例外情况：" + tableName +"没有找到对应的表");
                continue;
            }
            exportor.toFile();
        }
        //文件压缩
        String zipPath = outputPath + '/' + type + AmzCalendar.dateNow("yyyyMMddHms") + ".zip";
        ZipUtils.compress(outputPath+'/'+type, zipPath );
        //下载zip文件
        HttpDownload.download(zipPath, response);
    }

    @PostMapping("/icon/upload")
    @ApiOperation("给某一条记录上传图标文件")
    @ApiImplicitParam(name = "file", value = "上传的文件")
    public String iconUpload(@RequestParam("file") MultipartFile file) throws JSONException {
            String fileName = file.getOriginalFilename();
            String filePath = iconPath + '/' + fileName;
            try {
                //保存一份文件到服务器备份
                File dest = new File(filePath);
                if (!dest.exists()) dest.mkdirs();
                file.transferTo(dest);
                return fileName;
            } catch (Exception e) {
                System.out.println(fileName + "在上传的时候发生错误！");
                e.printStackTrace();
                return "error";
            }
    }

    @GetMapping("/apply")
    @ApiOperation("应用服务器变更")
    public String httpApply(){
        ServerManager.apply();
        return "应用完成";
    }

    @GetMapping("/reSet")
    @ApiOperation("更新服务器缓存")
    public String httpStart(){
        ServerManager.start();
        return "启动完成";
    }

}
