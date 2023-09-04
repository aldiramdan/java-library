package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseLoan;
import com.aldiramdan.library.model.entity.Loan;
import com.aldiramdan.library.repository.LoanRepository;
import com.aldiramdan.library.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final LoanRepository loanRepository;

    private ResponseData responseData;

    @Override
    public ResponseData getById(Long id) {
        List<Loan> loanList = loanRepository.findUserById(id);

        List<ResponseLoan> listResult = new ArrayList<>();
        for (Loan l : loanList) {
            ResponseLoan temp = new ResponseLoan(l);
            listResult.add(temp);
        }

        return responseData = new ResponseData(200, "Success", listResult);
    }
}
