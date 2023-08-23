package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseLoan;
import com.aldiramdan.library.model.dto.response.ResponseUser;
import com.aldiramdan.library.model.entity.Loan;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.repository.LoanRepository;
import com.aldiramdan.library.service.HistoryService;
import com.aldiramdan.library.validator.LoanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanValidator loanValidator;

    private ResponseData responseData;

    @Override
    public ResponseData getByUsername(String username) {
        List<Loan> loanList = loanRepository.findUserByUsername(username);

        List<ResponseLoan> listResult = new ArrayList<>();
        for (Loan l : loanList) {
            ResponseLoan temp = new ResponseLoan(l);
            listResult.add(temp);
        }

        return responseData = new ResponseData(200, "Success", listResult);
    }
}
