package iss.nus.Assessment_PAF_2.Services;

import java.time.LocalDate;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.Assessment_PAF_2.Models.Form;
import iss.nus.Assessment_PAF_2.Repositories.MongoAccountsRepository;

@Service
public class MongoLogService {
    
    @Autowired
    MongoAccountsRepository mongoRepo;

    public void saveLog(String id, Form form) {

        Document doc = new Document()
                        .append("transactionId", id)
                        .append("date", LocalDate.now().toString())
                        .append("from_account", form.getFromId())
                        .append("to_account", form.getToId())
                        .append("amount", form.getAmount());
        
        mongoRepo.insertLog(doc);
    }
}
