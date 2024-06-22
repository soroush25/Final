package src.model.bl;

import lombok.Getter;
import src.controller.exceptions.NotFoundException;
import src.model.da.LoanDa;
import src.model.entity.Loan;
import src.model.tools.CRUD;

import java.util.List;

public class LoanBl implements CRUD<Loan> {
    @Getter
    private static LoanBl loanBl = new LoanBl();

    private LoanBl() {
    }

    @Override
    public Loan save(Loan loan) throws Exception {
        try (LoanDa loanDa = new LoanDa()) {
            loanDa.save(loan);
            return loan;
        }
    }

    @Override
    public Loan edit(Loan loan) throws Exception {
        try (LoanDa loanDa = new LoanDa()) {
            if (loanDa.findById(loan.getId()) != null) {
                loanDa.edit(loan);
                return loan;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Loan remove(int id) throws Exception {
        try (LoanDa loanDa = new LoanDa()) {
            Loan loan = loanDa.findById(id);
            if (loan != null) {
                loanDa.remove(id);
                return loan;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public List<Loan> findAll() throws Exception {
        try (LoanDa loanDa = new LoanDa()) {
            List<Loan> loanList = loanDa.findAll();
            if (!loanList.isEmpty()) {
                return loanList;
            } else {
                throw new NotFoundException();
            }
        }
    }

    @Override
    public Loan findById(int id) throws Exception {
        try (LoanDa loanDa = new LoanDa()) {
            Loan loan = loanDa.findById(id);
            if (loan != null) {
                return loan;
            } else {
                throw new NotFoundException();
            }
        }
    }
}
