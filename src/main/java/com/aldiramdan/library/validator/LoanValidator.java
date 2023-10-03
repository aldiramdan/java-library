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

    public void validateLoanIsAlreadyDeleted(Optional<Loan> findLoan) throws Exception {
        if (Objects.nonNull(findLoan.get().getIsDeleted()) && findLoan.get().getIsDeleted()) {
            throw new NotProcessException("Book Loan is already deleted!");
        }
    }

    public void validateLoanIsAlreadyRecovery(Optional<Loan> findLoan) throws Exception {
        if (!findLoan.get().getIsDeleted()) {
            throw new NotProcessException("Book Loan is already recovered!");
        }
    }
}
