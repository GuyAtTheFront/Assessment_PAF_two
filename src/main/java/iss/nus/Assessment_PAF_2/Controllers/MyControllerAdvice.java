package iss.nus.Assessment_PAF_2.Controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import iss.nus.Assessment_PAF_2.Exceptions.TransferFailedException;
import iss.nus.Assessment_PAF_2.Repositories.AcccountsRepository;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class MyControllerAdvice {
    
    @Autowired
    AcccountsRepository accRepo;

    @ExceptionHandler({TransferFailedException.class})
    public String transferFailed(HttpSession session, TransferFailedException ex, Model model) {
        List<String> errors = new LinkedList<>();
        errors.add(ex.getMessage());
        session.setAttribute("errors", errors);

        List<String> names = accRepo.getNamesForForm();
        model.addAttribute("names", names);

        return "index";
    }

}
