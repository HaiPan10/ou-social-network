package com.ou.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ou.pojo.Account;
import com.ou.service.interfaces.AccountService;

@RestController
@RequestMapping("admin")
public class ApiAdminController {
    @Autowired
    private AccountService accountService;

    @PatchMapping("accounts/update_status")
    public ResponseEntity<?> update(@RequestBody Map<String, String> request){
        try {
            Account account = accountService.retrieve(Integer.parseInt(request.get("id")));
            if(!account.getStatus().equals(request.get("status"))){
                accountService.verifyAccount(account, request.get("status"));
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
