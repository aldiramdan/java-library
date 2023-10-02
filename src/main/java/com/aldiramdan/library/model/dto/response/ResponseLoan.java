package com.aldiramdan.library.model.dto.response;

import com.aldiramdan.library.model.entity.Loan;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseLoan {
    private Long id;
    private ResponseUser user;
    private ResponseBook book;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date borrowDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date returnDate;
    private Boolean status;

    public ResponseLoan(Loan loan) {
        this.id = loan.getId();
        this.user = new ResponseUser(loan.getUser());
        this.book = new ResponseBook(loan.getBook());
        this.borrowDate = loan.getBorrowDate();
        this.dueDate = loan.getDueDate();
        this.returnDate = loan.getReturnDate();
        this.status = loan.getStatus();
    }
}
