package com.ou.api;

import java.net.URI;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ou.pojo.Account;
import com.ou.pojo.AuthRequest;
import com.ou.pojo.AuthResponse;
import com.ou.pojo.User;
import com.ou.pojo.UserStudent;
import com.ou.service.interfaces.AccountService;
import com.ou.validator.MapValidator;
import com.ou.validator.WebAppValidator;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/accounts")
public class ApiAccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MapValidator mapValidator;
    
    @Autowired
    private WebAppValidator webAppValidator;

    @InitBinder("params")
    public void initBinderMap(WebDataBinder binder) {
        binder.setValidator(mapValidator);
    }

    @InitBinder("requestBody")
    public void initBinderWeb(WebDataBinder binder) {
        binder.setValidator(webAppValidator);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> createPendingAccount(@RequestBody Map<String, Object> params,
            BindingResult bindingResult) throws Exception {
        try {
            System.out.println("[DEBUG] - Register");
            mapValidator.validate(params, bindingResult);
            if (bindingResult.hasErrors()) {
                // Print log
                System.out.println("============================================");
                bindingResult.getAllErrors().forEach(error -> {
                    System.out.println("[ERROR CODE]: " + error.getCode());
                    System.out.println("[MESSAGE]: " + error.getDefaultMessage());
                });

                String invalidMessage = bindingResult
                        .getAllErrors()
                        .get(0)
                        .getDefaultMessage();


                return ResponseEntity.badRequest().body(invalidMessage);
            }

            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.convertValue(params.get("account"), Account.class);
            User user = mapper.convertValue(params.get("user"), User.class);
            UserStudent userStudent = mapper.convertValue(params.get("userStudent"), UserStudent.class);

            return ResponseEntity.ok(accountService.create(account, user, userStudent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping(path = "/verify/{accountId}/{verificationCode}")
    public ResponseEntity<Object> verifyAccount(@PathVariable Integer accountId, 
    @PathVariable String verificationCode, HttpServletResponse response) throws Exception {
        try {            
            if (accountService.verifyEmail(accountId, verificationCode)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create("http://localhost:3000/"));
                return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
            } else {
                return null;
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path="/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest requestBody,
        BindingResult bindingResult) throws AccountNotFoundException {
        try {
            if (bindingResult.hasErrors()) {
                String invalidMessage = bindingResult
                        .getAllErrors()
                        .get(0)
                        .getDefaultMessage();
                return ResponseEntity.badRequest().body(invalidMessage);
            }
            AuthResponse response = accountService.login(requestBody);
            if(response != null){
                return ResponseEntity.ok().body(response);
            }
            else {
                throw new Exception("Get Null Pointer");
            }
        } catch (Exception e) {
            // return ResponseEntity
            //     .status(HttpStatus.UNAUTHORIZED)
            //     .body(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path="/status/{accountId}")
    public ResponseEntity<Object> getStatus(@PathVariable Integer accountId) {
        try {
            return ResponseEntity.ok().body(accountService.getStatus(accountId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
