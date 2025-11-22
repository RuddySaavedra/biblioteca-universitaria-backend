// java
package com.app.bibliotecauniversitariapa.servicesimpls;

import com.app.bibliotecauniversitariapa.dtos.LoanDTO;
import com.app.bibliotecauniversitariapa.entities.Book;
import com.app.bibliotecauniversitariapa.entities.BookReturn;
import com.app.bibliotecauniversitariapa.entities.Loan;
import com.app.bibliotecauniversitariapa.entities.Student;
import com.app.bibliotecauniversitariapa.entities.enums.LoanStatus;
import com.app.bibliotecauniversitariapa.exceptions.ResourceNotFoundException;
import com.app.bibliotecauniversitariapa.mappers.LoanMapper;
import com.app.bibliotecauniversitariapa.repositories.BookRepository;
import com.app.bibliotecauniversitariapa.repositories.BookReturnRepository;
import com.app.bibliotecauniversitariapa.repositories.LoanRepository;
import com.app.bibliotecauniversitariapa.repositories.StudentRepository;
import com.app.bibliotecauniversitariapa.services.LoanService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookReturnRepository bookReturnRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Loan loan = LoanMapper.mapLoanDTOToLoan(loanDTO);

        loan.setStatus(LoanStatus.ACTIVE);

        Book book = verifyBook(loanDTO.getBookId());
        // mantener bidireccionalidad
        book.addLoan(loan);

        if (loanDTO.getStudentId() == null) {
            throw new IllegalStateException("Student id is required to create a loan.");
        }
        Student student = verifyStudent(loanDTO.getStudentId());
        loan.setStudent(student);

        Loan savedLoan = loanRepository.save(loan);

        if (loanDTO.getReturnId() != null) {
            BookReturn br = verifyBookReturn(loanDTO.getReturnId());
            if (br.getLoan() != null && !br.getLoan().getId().equals(savedLoan.getId())) {
                Loan previous = br.getLoan();
                previous.setBookReturn(null);
                recalculateStatus(previous);
                loanRepository.save(previous);
            }
            br.setLoan(savedLoan);
            savedLoan.setBookReturn(br);
            bookReturnRepository.save(br);
        }

        recalculateStatus(savedLoan);
        savedLoan = loanRepository.save(savedLoan);

        return LoanMapper.mapLoanToLoanDTO(savedLoan);
    }

    @Override
    public LoanDTO updateLoan(Long loanId, LoanDTO loanDTO) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id " + loanId));

        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setDueDate(loanDTO.getDueDate());
        // status ignorado desde DTO

        // Manejar cambio de libro (gestionar por el lado propietario para evitar orphanRemoval inesperado)
        if (loanDTO.getBookId() != null) {
            Long newBookId = loanDTO.getBookId();
            Book currentBook = loan.getBook();
            if (currentBook == null || !currentBook.getId().equals(newBookId)) {
                Book newBook = verifyBook(newBookId);

                // Remover del libro anterior de forma segura (comparando id)
                if (currentBook != null) {
                    currentBook.getLoans().removeIf(l -> {
                        if (l == null) return false;
                        if (l.getId() != null && loan.getId() != null) {
                            return l.getId().equals(loan.getId());
                        }
                        return l == loan;
                    });
                    // persistir el libro anterior para reflejar la colección modificada
                    bookRepository.save(currentBook);
                }

                // asignar el nuevo libro en el lado propietario y añadir a la lista del nuevo libro
                loan.setBook(newBook);
                if (newBook.getLoans() == null || newBook.getLoans().stream().noneMatch(l -> l.getId() != null && l.getId().equals(loan.getId()))) {
                    newBook.getLoans().add(loan);
                }
                bookRepository.save(newBook);
            }
        }

        // manejar BookReturn asociado (igual que antes)
        if (loanDTO.getReturnId() != null) {
            BookReturn br = verifyBookReturn(loanDTO.getReturnId());
            if (br.getLoan() != null && !br.getLoan().getId().equals(loan.getId())) {
                Loan previous = br.getLoan();
                previous.setBookReturn(null);
                recalculateStatus(previous);
                loanRepository.save(previous);
            }
            br.setLoan(loan);
            loan.setBookReturn(br);
            bookReturnRepository.save(br);
        } else {
            if (loan.getBookReturn() != null) {
                BookReturn old = loan.getBookReturn();
                old.setLoan(null);
                bookReturnRepository.save(old);
                loan.setBookReturn(null);
            }
        }

        recalculateStatus(loan);
        Loan savedLoan = loanRepository.save(loan);
        return LoanMapper.mapLoanToLoanDTO(savedLoan);
    }

    @Override
    public void deleteLoan(Long loanId) {
        Loan loan = verifyLoan(loanId);
        if (loan.getBookReturn() != null) {
            BookReturn br = loan.getBookReturn();
            // eliminar el BookReturn asociado
            bookReturnRepository.delete(br);
            loan.setBookReturn(null);
        }
        // quitar de la lista del book si existe
        if (loan.getBook() != null) {
            Book b = loan.getBook();
            b.getLoans().remove(loan);
            loan.setBook(null);
        }
        loanRepository.delete(loan);
    }

    @Override
    public LoanDTO getLoanById(Long loanId) {
        Loan loan = verifyLoan(loanId);
        recalculateStatus(loan);
        loanRepository.save(loan);
        return LoanMapper.mapLoanToLoanDTO(loan);
    }

    @Override
    public List<LoanDTO> getLoans() {
        List<Loan> loans = loanRepository.findAll();
        for (Loan l : loans) {
            LoanStatus before = l.getStatus();
            recalculateStatus(l);
            if (before != l.getStatus()) {
                loanRepository.save(l);
            }
        }
        return loans.stream().map((LoanMapper::mapLoanToLoanDTO)).collect(Collectors.toList());
    }

    private Loan verifyLoan(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id " + loanId));
    }

    private Book verifyBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
    }

    private BookReturn verifyBookReturn(Long returnId) {
        return bookReturnRepository.findById(returnId)
                .orElseThrow(() -> new ResourceNotFoundException("BookReturn not found with id " + returnId));
    }

    private Student verifyStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
    }

    private void recalculateStatus(Loan loan) {
        if (loan == null) return;
        if (loan.getBookReturn() != null) {
            loan.setStatus(LoanStatus.RETURNED);
            return;
        }
        LocalDate due = loan.getDueDate();
        if (due != null && due.isBefore(LocalDate.now())) {
            loan.setStatus(LoanStatus.LATE);
        } else {
            loan.setStatus(LoanStatus.ACTIVE);
        }
    }
}
