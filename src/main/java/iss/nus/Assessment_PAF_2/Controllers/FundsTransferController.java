package iss.nus.Assessment_PAF_2.Controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import iss.nus.Assessment_PAF_2.Models.Form;
import iss.nus.Assessment_PAF_2.Repositories.AcccountsRepository;
import iss.nus.Assessment_PAF_2.Repositories.MongoAccountsRepository;
import iss.nus.Assessment_PAF_2.Services.FundsTransferService;
import iss.nus.Assessment_PAF_2.Services.LogAuditService;
import iss.nus.Assessment_PAF_2.Services.MongoLogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class FundsTransferController {
    
    @Autowired
    AcccountsRepository accRepo;

    @Autowired
    FundsTransferService transferService;

    @Autowired
    LogAuditService logService;

    @Autowired
    MongoAccountsRepository mongoRepo;

    @Autowired
    MongoLogService mongoLog;

    @GetMapping("/")
    public String fundTransfer(Model model, HttpSession session) {
        
        String status = (String) session.getAttribute("status");
        if (null != status) {
            session.invalidate();
        }

        // List<String> names = accRepo.getNamesForForm();
        List<String> names = mongoRepo.getNameForForm();

        System.out.println("controller --> " + names);
        model.addAttribute("names", names);

        return "index";
    }

    @PostMapping(path = "/transfer", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String transfer(@ModelAttribute Form form, HttpSession session) {

        // @RequestBody MultiValueMap<String, String> fromUser
        // Form form = new Form();
        // form.setFromAccount(fromUser.getFirst("fromAccount"));
        // form.setToAccount(fromUser.getFirst("toAccount"));
        // form.setAmount(Double.parseDouble(fromUser.getFirst("amount")));
        // form.setComments(fromUser.getFirst("comments"));

        session.setAttribute("form", form);

        List<String> errors = new LinkedList<>();

        // c0
        // List<String> names = accRepo.getNamesForForm();
        List<String> names = mongoRepo.getNameForForm();
        
        if(!names.contains(form.getFromAccount())) {
            errors.add("From Account does not exist");
        }

        if(!names.contains(form.getToAccount())) {
            errors.add("To Account does not exist");
        }

        // c1
        if (form.getFromId().length() != 10 ) {
            errors.add("From Account Id not 10 characters");
        }

        // c1
        if (form.getToId().length() != 10) {
            errors.add("To Account Id not 10 characters");
        }

        // c2
        if (form.getFromAccount().equalsIgnoreCase(form.getToAccount())) {
            errors.add("From Account and To Account cannot be the same");
        }

        // c3
        if (form.getAmount() <= 0) {
            errors.add("Amount cannot be zero or negative");
        }

        // c4
        if (form.getAmount() < 10) {
            errors.add("Amount cannot be less than $10");
        }

        // c5
        // Optional<Double> balanceOpt = accRepo.getBalanceByID(form.getFromId());
        Optional<Double> balanceOpt = mongoRepo.getBalanceByID(form.getFromId());
        
        if (balanceOpt.isPresent()) {
            if(balanceOpt.get() - form.getAmount() < 0) {
                errors.add("From Account insufficient funds");
            }
        }

        // !errors.isEmpty()
        if (errors.size() > 0) {
            session.setAttribute("errors", errors);
            return "redirect:/";
        }

        // no error
        
        // generate id
        String transactionId = UUID.randomUUID().toString().substring(0, 8);

        // perform transfer
        transferService.fundTransfer(form.getFromId(), form.getToId(), form.getAmount());

        // auditlog
        // logService.saveLog(transactionId, form);
        mongoLog.saveLog(transactionId, form);

        session.setAttribute("transactionId", transactionId);
        session.setAttribute("status", "complete");
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String result(HttpSession session) {
        System.out.println(">>>" + session.toString());
        return "result";
    }
}
