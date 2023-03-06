package iss.nus.Assessment_PAF_2.Services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.Assessment_PAF_2.Models.Form;
import iss.nus.Assessment_PAF_2.Repositories.LogAuditRepository;
import jakarta.json.Json;

@Service
public class LogAuditService {

    @Autowired
    LogAuditRepository logRepo;
    
    public void saveLog(String id, Form form) {

        String json = Json.createObjectBuilder()
                        .add("transactionId", id)
                        .add("date", LocalDate.now().toString())
                        .add("from_account", form.getFromId())
                        .add("to_account", form.getToId())
                        .add("amount", form.getAmount())
                        .build().toString();

        logRepo.insertRecord(id, json);

    }

}
