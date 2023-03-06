package iss.nus.Assessment_PAF_2.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import iss.nus.Assessment_PAF_2.Exceptions.TransferFailedException;
import iss.nus.Assessment_PAF_2.Repositories.AcccountsRepository;

@Service
public class FundsTransferService {
    
    @Autowired
    AcccountsRepository accRepo;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void fundTransfer(String fromId, String toId, Double amount) {
        
        Boolean transferForm = accRepo.updateBalanceDeltaById(fromId, amount * -1);
        Boolean transferTo = accRepo.updateBalanceDeltaById(toId, amount);

        if (transferForm == false || transferTo == false) {
            throw new TransferFailedException("transfer failed");
        }

        // if (true) throw new TransferFailedException("Transfer Failed Exception!!");

        return;
    }

}
