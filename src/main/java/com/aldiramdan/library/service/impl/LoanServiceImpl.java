package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.LoanRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.dto.response.ResponseLoan;
import com.aldiramdan.library.model.entity.Book;
import com.aldiramdan.library.model.entity.Loan;
import com.aldiramdan.library.model.entity.User;
import com.aldiramdan.library.repository.BookRepository;
import com.aldiramdan.library.repository.LoanRepository;
import com.aldiramdan.library.repository.UserRepository;
import com.aldiramdan.library.service.LoanService;
import com.aldiramdan.library.validator.BookValidator;
import com.aldiramdan.library.validator.LoanValidator;
import com.aldiramdan.library.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanValidator loanValidator;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Override
    public ResponseData getAll() {
        List<Loan> loanList = loanRepository.findAll();

        List<ResponseLoan> listResult = new ArrayList<>();
        for (Loan l : loanList) {
            ResponseLoan temp = new ResponseLoan(l);
            listResult.add(temp);
        }

        return new ResponseData(200, "Success", listResult);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        return new ResponseData(200, "Success", new ResponseLoan(findLoan.get()));
    }

    @Override
    public ResponseData add(LoanRequest request) throws Exception {
        Optional<User> findUser = userRepository.findById(request.getUserId());
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser.get());

        Optional<Book> findBook = bookRepository.findById(request.getBook());
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsBorrowed(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook);

        Loan loan = new Loan();
        loan.setUser(findUser.get());
        loan.setBook(findBook.get());
        loan.setBorrowDate(request.getBorrowDate());
        loan.setDueDate(request.getDueDate());
        loanRepository.save(loan);

        findBook.get().setIsBorrowed(true);
        bookRepository.save(findBook.get());

        return new ResponseData(201, "Success", new ResponseLoan(loan));
    }

    @Override
    public ResponseData update(Long id, LoanRequest request) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        Optional<User> findUser = userRepository.findById(request.getUserId());
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser.get());

        Optional<Book> findBook = bookRepository.findById(request.getBook());
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsBorrowed(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook);

        findLoan.get().setUser(findUser.get());
        findLoan.get().setBook(findBook.get());
        findLoan.get().setBorrowDate(request.getBorrowDate());
        findLoan.get().setDueDate(request.getDueDate());
        loanRepository.save(findLoan.get());

        return new ResponseData(200, "Success", new ResponseLoan(findLoan.get()));
    }

    @Override
    public ResponseData updateStatus(Long id, LoanRequest request) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        Optional<User> findUser = userRepository.findById(request.getUserId());
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser.get());

        Optional<Book> findBook = bookRepository.findById(request.getBook());
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsBorrowed(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook);

        Loan loan = findLoan.get();
        loan.setReturnDate(request.getReturnDate());
        loan.setStatus(true);
        loanRepository.save(loan);

        findBook.get().setIsBorrowed(false);
        bookRepository.save(findBook.get());

        return new ResponseData(200, "Success", new ResponseLoan(findLoan.get()));
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);
        loanValidator.validateLoanIsAlreadyDeleted(findLoan);

        findLoan.get().setIsDeleted(true);
        loanRepository.save(findLoan.get());

        findLoan.get().getBook().setIsBorrowed(false);
        bookRepository.save(findLoan.get().getBook());

        return new ResponseData(200, "Successfully deleted loan book", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);
        loanValidator.validateLoanIsAlreadyRecovery(findLoan);

        findLoan.get().setIsDeleted(false);
        loanRepository.save(findLoan.get());

        findLoan.get().getBook().setIsBorrowed(false);
        bookRepository.save(findLoan.get().getBook());

        return new ResponseData(200, "Successfully recovery loan book", null);
    }
}
