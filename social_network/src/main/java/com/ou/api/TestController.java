package com.ou.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Post;
import com.ou.service.interfaces.CloudinaryService;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired CloudinaryService uploadFileService;

    @GetMapping("beans")
    public ResponseEntity<String> retrives(){
        StringBuilder sb = new StringBuilder();
        System.out.println(applicationContext.getBeanDefinitionCount());
        for(String bean : applicationContext.getBeanDefinitionNames()){
            sb.append(bean + "\n");
        }
        return ResponseEntity.ok().body(sb.toString());
    }

    // @GetMapping("/cloudinary/delete")
    // public ResponseEntity<Object> deleteImage() throws IOException {
    //     return ResponseEntity.ok().body(uploadFileService.deleteImage());
    // }

    @PostMapping("posts")
    public ResponseEntity<?> uploadSurvey(@RequestBody Post post) {
        return ResponseEntity.ok().body(post);
    }
}
