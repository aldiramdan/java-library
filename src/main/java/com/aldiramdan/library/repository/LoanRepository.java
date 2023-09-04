package com.aldiramdan.library.repository;

import com.aldiramdan.library.model.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("""
            select l from Loan l inner join User u\s
            on l.user.id = u.id\s
            where u.id = :id\s
            """)
    List<Loan> findUserById(Long id);
}
