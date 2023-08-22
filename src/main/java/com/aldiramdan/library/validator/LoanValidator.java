package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.Loan;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoanValidator {
    public void validateLoanNotFound(Optional<Loan> findLoan) throws Exception {
        if (findLoan.isEmpty()) {
            throw new NotFoundException("Book Loan is not found!");
        }
    }

    public void validateLoanIsAlreadyDeleted(Loan loan) throws Exception {
        if (Objects.nonNull(loan.getIsDeleted()) && loan.getIsDeleted()) {
            throw new NotProcessException("Book Loan is already deleted!");
        }
    }
}
