package com.gox.basic.controller;

import com.gox.common.utils.file.FileTypeUtils;
import com.gox.basic.domain.ElectronicAttributes;
import com.gox.basic.service.IElectronicAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    private IElectronicAttributesService service;

    @RequestMapping("/ele/{id}")
    public void getFile(@PathVariable String id, HttpServletResponse resp) throws IOException {
        ElectronicAttributes ea = service.selectElectronicAttributesById(Long.valueOf(id));
        String p = ea.getCurrentLocation();
        FileTypeUtils.preview(p, resp);
    }
}
