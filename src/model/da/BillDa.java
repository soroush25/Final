package src.model.da;

import lombok.extern.log4j.Log4j;
import src.model.entity.Bill;
import src.model.entity.Customer;
import src.model.entity.enums.BillTypes;
import src.model.tools.CRUD;
import src.model.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class BillDa implements AutoCloseable, CRUD<Bill> {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public BillDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }


    @Override
    public Bill save(Bill bill) throws Exception {
        bill.setId(ConnectionProvider.getConnectionProvider().getNextId("bill_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO BILL (id,customer_id,billNumber,billTypes,amount) VALUES (?,?,?,?,?)"
        );
        preparedStatement.setInt(1, bill.getId());
        preparedStatement.setInt(2, bill.getCustomerId().getId());
        preparedStatement.setString(3, bill.getBillNumber());
        preparedStatement.setString(4, String.valueOf(bill.getBillTypes()));
        preparedStatement.setInt(5,bill.getAmount());
        preparedStatement.execute();
        return bill;
    }

    @Override
    public Bill edit(Bill bill) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE BILL SET customer_id = ?, billNumber = ?, billTypes = ?,amount=? WHERE id = ?"
        );
        preparedStatement.setInt(1, bill.getCustomerId().getId());
        preparedStatement.setString(2, bill.getBillNumber());
        preparedStatement.setString(3, String.valueOf(bill.getBillTypes()));
        preparedStatement.setInt(4,bill.getAmount());
        preparedStatement.setInt(5, bill.getId());
        preparedStatement.execute();
        return bill;
    }

    @Override
    public Bill remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM BILL WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<Bill> findAll() throws Exception {
        List<Bill> billList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM BILL ORDER BY ID");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Bill bill = Bill
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .billNumber(resultSet.getString("Bill_ID"))
                    .customerId(Customer.builder().id(resultSet.getInt("Customer_ID")).build())
                    .amount(resultSet.getInt("Amount"))
                    .billTypes(BillTypes.valueOf(resultSet.getString("BillType")))
                    .build();
            billList.add(bill);
        }
        return billList;
    }

    @Override
    public Bill findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM BILL WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Bill bill = null;
        if (resultSet.next()) {
            bill = Bill
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .billNumber(resultSet.getString("Bill_ID"))
                    .customerId(Customer.builder().id(resultSet.getInt("Customer_ID")).build())
                    .amount(resultSet.getInt("Amount"))
                    .billTypes(BillTypes.valueOf(resultSet.getString("BillType")))
                    .build();
        }
        return bill;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }

}
