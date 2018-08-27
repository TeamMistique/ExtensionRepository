package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
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
    public List<Extension> listPopularExtensions() {
        return extensionService.listPopularExtensions();
    }

    @GetMapping("/new")
    public List<Extension> listNewExtensions() {
        return extensionService.listNewExtensions();
    }

    @GetMapping("/featured")
    public List<Extension> listFeaturedExtensions() {
        return extensionService.listFeaturedExtensions(true);
    }

    @GetMapping("/published")
    public List<Extension> listPublishedExtensions() {
        return extensionService.listPublishedExtensions(true);
    }

    @GetMapping("/{id}")
    public Extension getExtensionByID(@PathVariable int id) {
        return extensionService.getExtensionById(id);
    }

    @PostMapping("/add")
    public Extension addExtension(@RequestBody ExtensionDTO dto, HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        String authToken = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        dto.setUsername(username);

        return extensionService.createExtension(dto);
    }

    @PostMapping("/edit")
    public Extension editExtension(@RequestBody ExtensionDTO dto, HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        String authToken = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        dto.setUsername(username);

        return extensionService.updateExtension(dto);
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('USER')")
    public List<Extension> listUserExtensions(HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        String authToken = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        return userService.findOne(username).getExtensions();
    }

    @PostMapping("/delete")
    public void deleteExtension(@RequestParam int id, HttpServletRequest request){
        String header = request.getHeader(HEADER_STRING);
        String authToken = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        Extension extension = extensionService.getExtensionById(id);
        if(extension.getOwnerUsername().equals(username)) extensionService.deleteExtension(extension);
    }
}
