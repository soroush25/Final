package src.model.da;

import lombok.extern.log4j.Log4j;
import src.model.entity.Account;
import src.model.entity.Receipt;
import src.model.entity.Transaction;
import src.model.tools.CRUD;
import src.model.tools.ConnectionProvider;

import java.sql.*;
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
                "INSERT INTO RECEIPT (id, amount, transactionDateTime, account_src, account_dst) VALUES (?,?,?,?,?)"
        );
        preparedStatement.setInt(1, receipt.getId());
        preparedStatement.setInt(2, Integer.parseInt(String.valueOf(receipt.getAmount())));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(receipt.getTransactionDateTime().toString()));
        preparedStatement.setInt(4, receipt.getSourceAccount().getAccountNumber());
        preparedStatement.setInt(5, receipt.getDestinationAccount().getAccountNumber());
        preparedStatement.execute();
        return receipt;
    }

    @Override
    public Receipt edit(Receipt receipt) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE RECEIPT SET amount = ?, transactionDateTime = ?, account_src = ?, account_dst = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, Integer.parseInt(String.valueOf(receipt.getAmount())));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(receipt.getTransactionDateTime().toString()));
        preparedStatement.setInt(3, receipt.getSourceAccount().getAccountNumber());
        preparedStatement.setInt(4, receipt.getDestinationAccount().getAccountNumber());
        preparedStatement.setInt(5, receipt.getId());
        preparedStatement.execute();
        return receipt;
    }

    @Override
    public Receipt remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM RECEIPT WHERE id = ?"
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
                    .id(resultSet.getInt("id"))
                    .amount(Transaction.builder().amount(resultSet.getInt("amount")).build())
//                    .transactionDateTime(Transaction.builder().transactionDateTime(resultSet.getTimestamp("transactionDateTime")).build())
                    .sourceAccount(Account.builder().accountNumber(resultSet.getInt("account_src")).build())
                    .destinationAccount(Account.builder().accountNumber(resultSet.getInt("account_dst")).build())
                    .build();
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
                    .id(resultSet.getInt("id"))
                    .amount(Transaction.builder().amount(resultSet.getInt("amount")).build())
//                    .transactionDateTime(Transaction.builder().transactionDateTime(resultSet.getTimestamp("transactionDateTime")).build())
                    .sourceAccount(Account.builder().accountNumber(resultSet.getInt("account_src")).build())
                    .destinationAccount(Account.builder().accountNumber(resultSet.getInt("account_dst")).build())
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
