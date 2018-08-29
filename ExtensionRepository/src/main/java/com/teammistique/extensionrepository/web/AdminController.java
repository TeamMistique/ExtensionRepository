package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.services.base.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private ExtensionService extensionService;

    @Autowired
    public AdminController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @GetMapping("/extensions/all")
    public List<Extension> listAllExtensions(){
        return extensionService.listAllExtensions();
    }

    @GetMapping("/extensions/unpublished")
    public List<Extension> listUnpublishedExtensions(){
        return extensionService.listPublishedExtensions(false);
    }

    @PostMapping("/publish")
    public Extension publishExtension(@RequestParam int id){
        return extensionService.publishExtension(id);
    }

    @PostMapping("/feature")
    public Extension changeFeaturedStatus(@RequestParam int id){
        Extension extension = null;
        try {
            extension = extensionService.changeFeatureStatus(id);
        } catch (FullFeaturedListException e) {
            e.printStackTrace();
        }
        return extension;
    }

    @PostMapping("/feature/size")
    public void changeFeaturedListSize(@RequestParam int size){
        extensionService.setMaxListSize(size);
    }
}
