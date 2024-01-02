package com.project.pageflow.repository;

import com.project.pageflow.models.Student;
import com.project.pageflow.models.Transaction;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // TODO - verify with List<Transaction>
    Transaction findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);

}
