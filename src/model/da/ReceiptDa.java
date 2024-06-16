package src.model.da;

import lombok.extern.log4j.Log4j;
import src.model.entity.Account;
import src.model.entity.Customer;
import src.model.entity.Loan;
import src.model.entity.Receipt;
import src.model.entity.enums.LoanType;
import src.model.entity.enums.TransactionTypes;
import src.model.tools.CRUD;
import src.model.tools.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class ReceiptDa implements AutoCloseable, CRUD<Receipt> {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public ReceiptDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }
    @Override
    public Receipt save(Receipt receipt) throws Exception {
        receipt.setId(ConnectionProvider.getConnectionProvider().getNextId("receipt_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO RECEIPT (id,amount,transactionDateTime,transactionType,account_src,account_dst,fname,lname) VALUES (?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, receipt.getId());
        preparedStatement.setDouble(2, receipt.getAmount());
        preparedStatement.setString(3, String.valueOf(receipt.getTransactionDateTime()));
        preparedStatement.setString(4, String.valueOf(receipt.getTransactionType()));
        preparedStatement.setInt(5, receipt.getSourceAccount().getAccountNumber());
        preparedStatement.setInt(6, receipt.getDestinationAccount().getAccountNumber());
        preparedStatement.setString(7, String.valueOf(receipt.getFirstName()));
        preparedStatement.setString(8, String.valueOf(receipt.getLastName()));
        preparedStatement.execute();
        return receipt;
    }

    @Override
    public Receipt edit(Receipt receipt) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE RECEIPT SET amount = ?, transactionDateTime = ?, transactionType = ?,account_src=?,account_dst=?,fname=?,lname=? WHERE id = ?"
        );
        preparedStatement.setDouble(1, receipt.getAmount());
        preparedStatement.setString(2, String.valueOf(receipt.getTransactionDateTime()));
        preparedStatement.setString(3, String.valueOf(receipt.getTransactionType()));
        preparedStatement.setInt(4, receipt.getSourceAccount().getAccountNumber());
        preparedStatement.setInt(5, receipt.getDestinationAccount().getAccountNumber());
        preparedStatement.setString(6, String.valueOf(receipt.getFirstName()));
        preparedStatement.setString(7, String.valueOf(receipt.getLastName()));
        preparedStatement.setInt(8, receipt.getId());
        preparedStatement.execute();
        return receipt;
    }

    @Override
    public Receipt remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM LOAN WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<Receipt> findAll() throws Exception {
        List<Receipt> receiptList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM RECEIPT ORDER BY ID");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Receipt receipt = Receipt
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .amount(resultSet.getDouble("Amount"))
                    .transactionDateTime(resultSet.getTimestamp("Date_Time"))
                    .transactionType(TransactionTypes.valueOf(String.valueOf(resultSet.getString("TransactionType"))))
                    .sourceAccount(Account.builder().accountNumber(resultSet.getInt("Account_src")).build())
                    .destinationAccount(Account.builder().accountNumber(resultSet.getInt("Account_dst")).build())
                    .firstName(Customer.builder().firstName(resultSet.getString("FName")).build())
                    .lastName(Customer.builder().lastName(resultSet.getString("LName")).build()).build();
            receiptList.add(receipt);
        }
        return receiptList;
    }

    @Override
    public Receipt findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM RECEIPT WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Receipt receipt = null;
        if (resultSet.next()) {
            receipt = Receipt
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .amount(resultSet.getDouble("Amount"))
                    .transactionDateTime(resultSet.getTimestamp("Date_Time"))
                    .transactionType(TransactionTypes.valueOf(String.valueOf(resultSet.getString("TransactionType"))))
                    .sourceAccount(Account.builder().accountNumber(resultSet.getInt("Account_src")).build())
                    .destinationAccount(Account.builder().accountNumber(resultSet.getInt("Account_dst")).build())
                    .firstName(Customer.builder().firstName(resultSet.getString("FName")).build())
                    .lastName(Customer.builder().lastName(resultSet.getString("LName")).build())
                    .build();
        }
        return receipt;
    }
    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }


}
