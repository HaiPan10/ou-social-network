package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.CloudinaryService;
import com.ou.service.interfaces.PostService;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    CloudinaryService uploadFileService;
    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;

    @GetMapping("beans")
    public ResponseEntity<String> retrives() {
        StringBuilder sb = new StringBuilder();
        System.out.println(applicationContext.getBeanDefinitionCount());
        for (String bean : applicationContext.getBeanDefinitionNames()) {
            sb.append(bean + "\n");
        }
        return ResponseEntity.ok().body(sb.toString());
    }

    // @GetMapping("/cloudinary/delete")
    // public ResponseEntity<Object> deleteImage() throws IOException {
    // return ResponseEntity.ok().body(uploadFileService.deleteImage());
    // }

    // @PostMapping(path = "posts")
    // public ResponseEntity<?> uploadSurvey(@RequestBody Post post, BindingResult bindingResult) {
    //     try{
    //         System.out.println("[DEBUG] - " + post);
    //         Post p = postService.uploadPostSurvey(post, 1);
    //         return ResponseEntity.ok().body(p);
    //     }
    //     catch(Exception e){
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @GetMapping(path = "accounts")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok().body(accountService.list());
    }
}
