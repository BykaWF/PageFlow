package com.project.pageflow.service;

import com.project.pageflow.dto.FormRequestDto;
import com.project.pageflow.dto.InitiateOrderRequest;
import com.project.pageflow.repository.TransactionRepository;

import com.project.pageflow.models.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    private final StudentService studentService;
    private final TransactionRepository transactionRepository;
    private final CartItemService cartItemService;
    private final ShoppingSessionService shoppingSessionService;
    private final ShippingAddressService shippingAddressService;
    private final PaymentMethodService paymentMethodService;


    public TransactionService(StudentService studentService,
                              TransactionRepository transactionRepository,
                              CartItemService cartItemService,
                              ShoppingSessionService shoppingSessionService,
                              ShippingAddressService shippingAddressService,
                              PaymentMethodService paymentMethodService) {

        this.studentService = studentService;
        this.transactionRepository = transactionRepository;
        this.cartItemService = cartItemService;
        this.shoppingSessionService = shoppingSessionService;
        this.shippingAddressService = shippingAddressService;
        this.paymentMethodService = paymentMethodService;
    }

    @Transactional
    public OrderStatus initiateTransaction(InitiateOrderRequest initiateOrderRequest, Authentication authentication) throws Exception {
        if (initiateOrderRequest.getOrderType() != OrderType.RETURN) {
            return checkoutBook(initiateOrderRequest, authentication);
        } else {
            return returnBook(initiateOrderRequest, authentication);
        }

    }

    private OrderStatus checkoutBook(InitiateOrderRequest initiateOrderRequest, Authentication authentication) throws Exception {

        Student student = studentService.getCurrentStudent(authentication);
        List<CartItem> cartItemList = initiateOrderRequest.getCartItemList();
        ShippingAddress shippingAddress = initiateOrderRequest.getShippingAddress();
        PaymentMethod paymentMethod = initiateOrderRequest.getPaymentMethod();
        BigDecimal totalOrder = initiateOrderRequest.getTotal();

        validateRequest(student, cartItemList, shippingAddress, paymentMethod, totalOrder);
        Transaction transaction = processCheckoutTransaction(student, cartItemList, shippingAddress, paymentMethod, totalOrder);

        return transaction.getOrderStatus();
    }


    private Transaction processCheckoutTransaction(Student student,
                                                  List<CartItem> cartItems,
                                                  ShippingAddress shippingAddress,
                                                  PaymentMethod paymentMethod,
                                                  BigDecimal totalOrder) {

        Transaction transaction = buildTransaction(student, cartItems, shippingAddress, paymentMethod, totalOrder);

        try {
            saveTransaction(transaction);
            transaction.setOrderStatus(OrderStatus.SUCCESS);
            handleShoppingCartSuccessAndCleanup(student.getShoppingSession());
        } catch (Exception e) {
            handleTransactionFailure(transaction);
        } finally {
            saveTransaction(transaction);
        }

        return transaction;
    }

    public void handleShoppingCartSuccessAndCleanup(ShoppingSession shoppingSession) {
        cartItemService.cleanup(shoppingSession.getId());
        shoppingSessionService.cleanup(shoppingSession);

    }

    private void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    private void handleTransactionFailure(Transaction transaction) {
        if (transaction != null) {
            transaction.setOrderStatus(OrderStatus.FAILURE);
            saveTransaction(transaction);
        }
    }

    private Transaction buildTransaction(Student student,
                                         List<CartItem> cartItems,
                                         ShippingAddress shippingAddress,
                                         PaymentMethod paymentMethod,
                                         BigDecimal totalOrder) {

        return Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .student(student)
                .cartItems(cartItems)
                .shippingAddress(shippingAddress)
                .paymentMethod(paymentMethod)
                .totalOrder(totalOrder)
                .orderStatus(OrderStatus.PENDING)
                .orderType(OrderType.CHECKOUT)
                .build();
    }


    private void validateRequest(Student student,
                                 List<CartItem> cartItemList,
                                 ShippingAddress shippingAddress,
                                 PaymentMethod paymentMethod,
                                 BigDecimal totalCheckout) throws Exception {

        if (student == null
                || cartItemList.isEmpty()
                || shippingAddress == null
                || paymentMethod == null
                || totalCheckout == null) {
            throw new Exception("Invalid Request");
        }
    }

    public Transaction getLastTransaction() {
        Optional<Transaction> lastTransaction = transactionRepository.findFirstByOrderByCreatedOnDesc();
        return lastTransaction.orElse(null);
    }

    /**
     * 1. Validate the book, student, admin and also validate if the book is issued to the same person
     * 2. Get the corresponding issuance transaction
     * 3. Entry in the transaction table
     * 4. Due date check, if due date - issue date > allowedDuration => fine calculation
     * 5. If there is no fine, de-allocate the book from student's name ===> book table
     */
    private OrderStatus returnBook(InitiateOrderRequest initiateOrderRequest, Authentication authentication) throws Exception {
//        Student student = getStudentFromOrderRequest(initiateOrderRequest);
//        Book book = getBookFromOrderRequest(initiateOrderRequest);
//
//
//        validateRequest(student,book);
//
//        if(book.getStudent() == null || !book.getStudent().getId().equals(student.getId())) {
//            throw new Exception("This book isn't assigned to the particular student");
//        }
//
//        // 2. Get the corresponding issuance transaction
//        Transaction issuanceTransaction = transactionRepository.findTransactionByStudentAndBookAndOrderTypeOrderByIdDesc(student, book, OrderType.CHECKOUT);
//        if(issuanceTransaction == null) {
//            throw new Exception("This book hasn't been issued to anyone");
//        }
//
//        Transaction transaction = null;
//        try {
//            Integer fine = calculateFine(issuanceTransaction.getCreatedOn());
//
//            transaction = Transaction.builder()
//                    .transactionId(UUID.randomUUID().toString())
//                    .orderType(initiateOrderRequest.getOrderType())
//                    .orderStatus(OrderStatus.PENDING)
//                    .book(book)
//                    .student(student)
//                    .fine(fine)
//                    .build();
//
//            transactionRepository.save(transaction);
//
//            //pay Fine
//
//            if(fine == 0) {
//                // de allocating the book
//                book.setStudent(null);
//                bookService.createOrUpdateBook(book);
//                transaction.setOrderStatus(OrderStatus.SUCCESS);
//            }
//
//
//        } catch (Exception e) {
//            transaction.setOrderStatus(OrderStatus.FAILURE);
//        } finally {
//            transactionRepository.save(transaction);
//        }

        return OrderStatus.PENDING;
    }

    private Integer calculateFine(Date issuanceTime) {
        long issuanceTimeInMillis = issuanceTime.getTime();
        long currentTime = System.currentTimeMillis();

        long diff = currentTime - issuanceTimeInMillis;

        long daysPassed = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

//        if (daysPassed > allowedDuration) {
//            return (int) (daysPassed - allowedDuration);
//        }
        return 0;
    }

    public void payFine(Integer amount, Integer studentId, String txnId) {
        // get the return trxn from DB using txnId
        //              Payment
        // Deallocate the book, mark this transaction as successful
        // save this transaction in DB
    }

    public InitiateOrderRequest convertFormRequestToOrderRequest(FormRequestDto formRequestDto, Authentication authentication) {
        Student currentStudent = studentService.getCurrentStudent(authentication);
        ShoppingCartInfo shoppingCartInfo = shoppingSessionService.getShoppingCartInfo(currentStudent);
        ShippingAddress shippingAddress = formRequestDto.getShippingAddress();
        PaymentMethod payment = formRequestDto.getPayment();

        updateStudentAddressAndPayment(currentStudent, shippingAddress, payment);

        return createOrderRequest(payment, shippingAddress, shoppingCartInfo.getCartItems(), shoppingCartInfo.getShoppingSession());
    }

    public InitiateOrderRequest createOrderRequest(PaymentMethod paymentMethod,
                                                    ShippingAddress shippingAddress,
                                                    List<CartItem> cartItems,
                                                    ShoppingSession shoppingSession) {


        InitiateOrderRequest initiateOrderRequest = new InitiateOrderRequest();
        initiateOrderRequest.setPaymentMethod(paymentMethod);
        initiateOrderRequest.setShippingAddress(shippingAddress);
        initiateOrderRequest.setCartItemList(cartItems);
        initiateOrderRequest.setTotal(shoppingSession.getTotal());

        return initiateOrderRequest;
    }
    public void updateStudentAddressAndPayment(Student currentStudent, ShippingAddress shippingAddress, PaymentMethod payment) {
        shippingAddressService.setStudent(currentStudent,shippingAddress);
        paymentMethodService.setStudent(currentStudent,payment);
    }
}
