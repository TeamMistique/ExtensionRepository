package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.exceptions.FullFeaturedListException;
import com.teammistique.extensionrepository.exceptions.UnpublishedExtensionException;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.services.base.AdminExtensionService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private AdminExtensionService extensionService;
    private UserService userService;

    @Autowired
    public AdminController(AdminExtensionService extensionService, UserService userService) {
        this.extensionService = extensionService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<Extension> listAllExtensions(){
        return extensionService.listAllExtensions();
    }

    @GetMapping("/unpublished")
    public List<Extension> listUnpublishedExtensions(){
        return extensionService.listPublishedExtensions(false);
    }

    @PostMapping("/publish")
    public Extension changePublishedStatus(@RequestParam int id){
        return extensionService.changePublishedStatus(id);
    }

    @PostMapping("/feature")
    public Extension changeFeaturedStatus(@RequestParam int id){
        Extension extension = null;
        try {
            extension = extensionService.changeFeatureStatus(id);
        } catch (FullFeaturedListException | UnpublishedExtensionException e) {
            e.printStackTrace();
        }
        return extension;
    }

    @PostMapping("/featured/size")
    public void changeFeaturedListSize(@RequestParam int size){
        extensionService.setMaxListSize(size);
    }

    @PostMapping("/sync")
    public void changeSyncTimer(@RequestParam long delay){
        extensionService.setUpdateGitHubPeriod(delay);
    }

    @PostMapping("/changeUserEnabled")
    public void changeUsersEnabledStatus(@RequestParam String username){
        userService.changeEnabled(userService.findOne(username));
    }

    @GetMapping("/userExtensions")
    public List<Extension> listUsersExtensions(@RequestParam String username) {
        return userService.findOne(username).getExtensions();
    }
}
