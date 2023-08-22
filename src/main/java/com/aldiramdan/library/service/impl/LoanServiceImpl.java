package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.LoanRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanValidator loanValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookValidator bookValidator;

    private ResponseData responseData;

    @Override
    public ResponseData getAll() {
        List<Loan> loanList = loanRepository.findAll();

        responseData = new ResponseData(200, "Success", loanList);
        return responseData;
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        responseData = new ResponseData(200, "Success", findLoan);
        return responseData;
    }

    @Override
    public ResponseData add(LoanRequest request) throws Exception {
        Optional<User> findUser = userRepository.findById(request.getUser());
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser.get());

        Optional<Book> findBook = bookRepository.findById(request.getBook());
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsBorrowed(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook.get());

        Loan loan = new Loan();
        loan.setUser(findUser.get());
        loan.setBook(findBook.get());
        loan.setBorrowDate(request.getBorrowDate());
        loan.setDueDate(request.getDueDate());

        loanRepository.save(loan);

        findBook.get().setIsBorrowed(true);

        responseData = new ResponseData(201, "Success", loan);
        return responseData;
    }

    @Override
    public ResponseData update(Long id, LoanRequest request) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        Optional<User> findUser = userRepository.findById(request.getUser());
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser.get());

        Optional<Book> findBook = bookRepository.findById(request.getBook());
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsBorrowed(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook.get());

        Loan loan = findLoan.get();
        loan.setUser(findUser.get());
        loan.setBook(findBook.get());
        loan.setBorrowDate(request.getBorrowDate());
        loan.setDueDate(request.getDueDate());

        loanRepository.save(loan);

        responseData = new ResponseData(200, "Success", loan);
        return responseData;
    }

    @Override
    public ResponseData updateStatus(Long id, LoanRequest request) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        Optional<User> findUser = userRepository.findById(request.getUser());
        userValidator.validateUserNotFound(findUser);
        userValidator.validateUserIsAlreadyDeleted(findUser.get());

        Optional<Book> findBook = bookRepository.findById(request.getBook());
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsBorrowed(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook.get());

        Loan loan = findLoan.get();
        loan.setReturnDate(request.getReturnDate());
        loan.setStatus(true);

        loanRepository.save(loan);

        findBook.get().setIsBorrowed(false);

        responseData = new ResponseData(200, "Success", loan);
        return responseData;
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        Loan loan = findLoan.get();
        loanValidator.validateLoanIsAlreadyDeleted(loan);

        loan.setIsDeleted(true);

        loanRepository.save(loan);

        findLoan.get().getBook().setIsBorrowed(false);

        responseData = new ResponseData(200, "Success", null);
        return responseData;
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Loan> findLoan = loanRepository.findById(id);
        loanValidator.validateLoanNotFound(findLoan);

        Loan loan = findLoan.get();

        loan.setIsDeleted(false);

        loanRepository.save(loan);

        findLoan.get().getBook().setIsBorrowed(false);

        responseData = new ResponseData(200, "Success", null);
        return responseData;
    }
}
