package com.teammistique.extensionrepository.web;

import com.teammistique.extensionrepository.config.security.JwtTokenUtil;
import com.teammistique.extensionrepository.models.DTO.ExtensionDTO;
import com.teammistique.extensionrepository.models.Extension;
import com.teammistique.extensionrepository.models.Tag;
import com.teammistique.extensionrepository.models.User;
import com.teammistique.extensionrepository.services.base.ExtensionService;
import com.teammistique.extensionrepository.services.base.TagService;
import com.teammistique.extensionrepository.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.teammistique.extensionrepository.models.security.Constants.HEADER_STRING;
import static com.teammistique.extensionrepository.models.security.Constants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionController {

    private TagService tagService;
    private ExtensionService extensionService;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ExtensionController(ExtensionService extensionService, TagService tagService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.extensionService = extensionService;
        this.tagService = tagService;
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

    @PostMapping("/add")
    public Extension addExtension(@RequestBody ExtensionDTO extensionDTO, HttpServletRequest request){
        String header = request.getHeader(HEADER_STRING);
        String authToken = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        User user = userService.findOne(username);

        List<Tag> tags = new ArrayList<>();
        for(String tagName:extensionDTO.getTagNames()){
            Tag tag = new Tag(tagName);
            tags.add(tagService.createTag(tag));
        }

        Extension extension = new Extension();
        extension.setOwner(user);
        extension.setName(extensionDTO.getName());
        extension.setDescription(extensionDTO.getDescription());
        extension.setLink(extensionDTO.getLink());
        extension.setFile(extensionDTO.getFile());
        extension.setImage(extensionDTO.getImage());
        extension.setTags(tags);

        return extensionService.createExtension(extension);
    }

    @PostMapping("/edit")
    public Extension editExtension(@RequestBody ExtensionDTO dto){
        Extension extension = new Extension();
        extension.setName(dto.getName());
        extension.setDescription(dto.getDescription());
        extension.setLink(dto.getLink());
        extension.setFile(dto.getFile());
        extension.setImage(dto.getImage());

        return extensionService.updateExtension(extension);
    }
}
