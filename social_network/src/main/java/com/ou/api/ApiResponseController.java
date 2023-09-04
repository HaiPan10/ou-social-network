package com.ou.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Response;
import com.ou.service.interfaces.ResponseService;

@RestController
@RequestMapping("api/responses")
public class ApiResponseController {
    @Autowired
    private ResponseService responseService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Response response) throws Exception {
        try {
            return ResponseEntity.ok(response);
            // return ResponseEntity.status(HttpStatus.CREATED).body(responseService.create(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 
}
