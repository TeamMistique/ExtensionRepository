package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.ExtensionService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.teammistique.extensionrepository.models.security.Constants.HEADER_STRING;
import static com.teammistique.extensionrepository.models.security.Constants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionController {

    private ExtensionService extensionService;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ExtensionController(ExtensionService extensionService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.extensionService = extensionService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/popular")
    public List<Extension> listPopularExtensions(){
        return extensionService.listPopularExtensions();
    }

    @GetMapping("/new")
    public List<Extension> listNewExtensions(){
        return extensionService.listNewExtensions();
    }

    @GetMapping("/featured")
    public List<Extension> listFeaturedExtensions(){
        return extensionService.listFeaturedExtensions(true);
    }

    @GetMapping("/{id}")
    public Extension getExtensionByID(@PathVariable int id){
        return extensionService.getExtensionById(id);
    }

    @PostMapping("/new")
    public Extension createNewExtension(@ModelAttribute Extension extension, HttpServletRequest request){
        String header = request.getHeader(HEADER_STRING);
        String authToken = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        User user = userService.findOne(username);
        extension.setOwner(user);
        System.out.println(extension);
        return extensionService.createExtension(extension);
    }
}
