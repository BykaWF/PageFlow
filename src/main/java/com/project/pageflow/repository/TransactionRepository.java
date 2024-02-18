package com.project.pageflow.repository;

import com.project.pageflow.models.Transaction;
import com.project.pageflow.models.Student;
import com.project.pageflow.models.Book;
import com.project.pageflow.models.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findTransactionByStudentAndBookAndOrderTypeOrderByIdDesc(Student student, Book book, OrderType orderType);

}
