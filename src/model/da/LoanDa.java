package src.model.da;

import lombok.extern.log4j.Log4j;
import src.model.entity.Bill;
import src.model.entity.Customer;
import src.model.entity.Loan;
import src.model.entity.enums.BillTypes;
import src.model.entity.enums.LoanType;
import src.model.tools.CRUD;
import src.model.tools.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class LoanDa implements AutoCloseable, CRUD<Loan> {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public LoanDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public Loan save(Loan loan) throws Exception {
        loan.setId(ConnectionProvider.getConnectionProvider().getNextId("loan_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO LOAN (id,interest,amount,maturityDate,loanType,startDate) VALUES (?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, loan.getId());
        preparedStatement.setDouble(2, loan.getInterest());
        preparedStatement.setDouble(3, loan.getAmount());
        preparedStatement.setString(4, String.valueOf(loan.getMaturityDate()));
        preparedStatement.setString(5,String.valueOf(loan.getLoanType()));
        preparedStatement.execute();
        return loan;
    }


    @Override
    public Loan edit(Loan loan) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE LOAN SET interest = ?, amount = ?, maturityDate = ?,loanType=?,startDate=? WHERE id = ?"
        );
        preparedStatement.setDouble(1, loan.getInterest());
        preparedStatement.setDouble(2, loan.getAmount());
        preparedStatement.setString(3, String.valueOf(loan.getMaturityDate()));
        preparedStatement.setString(4,String.valueOf(loan.getLoanType()));
        preparedStatement.setInt(5, loan.getId());
        preparedStatement.execute();
        return loan;
    }

    @Override
    public Loan remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM LOAN WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<Loan> findAll() throws Exception {
        List<Loan> loanList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM LOAN ORDER BY ID");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Loan loan = Loan
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .interest(resultSet.getDouble("Interest"))
                    .amount(resultSet.getDouble("Amount"))
                    .maturityDate(resultSet.getTimestamp("Maturity_Date"))
                    .loanType(LoanType.valueOf(String.valueOf(resultSet.getString("Loan_Type"))))
                    .startDate(resultSet.getTimestamp("Start_Date").toLocalDateTime())
                    .build();
            loanList.add(loan);
        }
        return loanList;
    }

    @Override
    public Loan findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM LOAN WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Loan loan = null;
        if (resultSet.next()) {
            loan = Loan
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .interest(resultSet.getDouble("Interest"))
                    .amount(resultSet.getDouble("Amount"))
                    .maturityDate(resultSet.getTimestamp("Maturity_Date"))
                    .loanType(LoanType.valueOf(String.valueOf(resultSet.getString("Loan_Type"))))
                    .startDate(resultSet.getTimestamp("Start_Date").toLocalDateTime())
                    .build();
        }
        return loan;
    }
    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }

}
