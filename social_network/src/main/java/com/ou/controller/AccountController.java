package com.ou.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.ou.pojo.Account;
import com.ou.pojo.Status;
import com.ou.pojo.User;
import com.ou.service.interfaces.AccountService;
import com.ou.service.interfaces.UserService;
import com.ou.validator.GrantAccountValidator;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private Environment env;
    @Autowired
    private GrantAccountValidator adminValidator;
    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
        // true));
        binder.setValidator(adminValidator);
    }

    @GetMapping("/verification")
    public String accountsVerification(Model model, @RequestParam Map<String, String> params) {
        List<Account> pendingAccounts = accountService.getPendingAccounts(params);
        model.addAttribute("pendingAccounts", pendingAccounts);
        Integer pageSize = Integer.parseInt(env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(accountService.countPendingAccounts() * 1.0 / pageSize));
        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);
            } else {
                page = 1;
            }
        } else {
            page = 1;
        }
        model.addAttribute("currentPage", page);
        return "accountsVerification";
    }

    @GetMapping()
    public String accounts(Model model, @RequestParam Map<String, String> params) {
        List<Account> accounts = accountService.search(params);
        model.addAttribute("accounts", accounts);
        Integer pageSize = Integer.parseInt(env.getProperty("PENDING_ACCOUNT_PAGE_SIZE"));
        model.addAttribute("counter", Math.ceil(accountService.countAccounts(params) * 1.0 / pageSize));
        int page;
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                page = Integer.parseInt(p);
            } else {
                page = 1;
            }
            String kw = params.get("kw");
            if(kw != null){
                model.addAttribute("kw", kw);
            }

            String filterStatus = params.get("status");
            if(filterStatus != null){
                model.addAttribute("filterStatus", filterStatus);
            }

        } else {
            page = 1;
        }
        model.addAttribute("status", Status.values());
        model.addAttribute("currentPage", page);
        return "accounts";
    }

    @GetMapping("/verification/{accountId}")
    public String verify(@PathVariable Integer accountId, @RequestParam String status) throws Exception {
        accountService.verifyAccount(accountService.retrieve(accountId), status);
        return "redirect:/admin/accounts/verification/";
    }

    @GetMapping("/provider")
    public String provideAccounts(Model model, @RequestParam(name = "status", required = false) String status) {
        Account account = new Account();
        account.setUser(new User());
        model.addAttribute("account", account);
        String defaultPassword = env.getProperty("DEFAULT_PASSWORD");
        model.addAttribute("defaultPassword", defaultPassword);
        if (status != null) {
            model.addAttribute("status", status);
        }
        return "provider";
    }

    @PostMapping(path = "/provider")
    public String add(@ModelAttribute("account") Account account,
            @RequestPart(value = "fileInput", required = false) MultipartFile avatar,
            BindingResult bindingResult) throws Exception {
        try {
            User user = account.getUser();
            account.setUser(null);
            adminValidator.validate(account, bindingResult);
            adminValidator.validate(user, bindingResult);
            if (bindingResult.hasErrors()) {
                return "provider";
            }
            System.out.printf("[INFO] - Provider email: %s\n", account);

            if (avatar.isEmpty()) {
                String defaultAvatar = this.env.getProperty("DEFAULT_AVATAR").toString();
                user.setAvatar(defaultAvatar);
            }
            String defaultCover = this.env.getProperty("DEFAULT_COVER").toString();
            user.setCoverAvatar(defaultCover);
            Account createdAccount = accountService.create(account, user);
            System.out.printf("[INFO] - Provided email: %s\n", createdAccount);
            if (!avatar.isEmpty()) {
                userService.uploadAvatar(avatar, createdAccount.getId());
            }
            return "redirect:/admin/accounts/provider/?status=success";
        } catch (Exception e) {
            bindingResult.addError(new ObjectError("exceptionError", e.getMessage()));
            return "provider";
        }
    }

    @GetMapping("{id}")
    public String retrieve(@PathVariable(value = "id") Integer accountId, Model model) {
        try {
            Account targetAccount = accountService.retrieve(accountId);
            model.addAttribute("account", targetAccount);
        } catch (Exception e) {

        }
        return "accountDetail";
    }
}
