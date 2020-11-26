package com.formice.mars.web.controller;


import com.formice.mars.web.common.Response;
import com.formice.mars.web.model.entity.Dic;
import com.formice.mars.web.model.entity.PortalContactUs;
import com.formice.mars.web.service.DicService;
import com.formice.mars.web.service.PortalContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("/portal")
public class PortalContactUsController {
    @Autowired
    private PortalContactUsService portalContactUsService;

    @RequestMapping(method = RequestMethod.POST,value = "/contactus")
    public Response addContactUs(PortalContactUs entity){
        portalContactUsService.addContactUs(entity);
        return Response.createBySuccess();
    }


}
