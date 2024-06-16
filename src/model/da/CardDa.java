package src.model.da;

import lombok.extern.log4j.Log4j;
import src.model.entity.Account;
import src.model.entity.Bill;
import src.model.entity.Card;
import src.model.entity.Customer;
import src.model.entity.enums.BillTypes;
import src.model.tools.CRUD;
import src.model.tools.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class CardDa  implements AutoCloseable, CRUD<Card> {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public CardDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }



    @Override
    public Card save(Card card) throws Exception {
        card.setId(ConnectionProvider.getConnectionProvider().getNextId("card_seq"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO CARD (id,accountNumber,pin) VALUES (?,?,?)"
        );
        preparedStatement.setInt(1, card.getId());
        preparedStatement.setInt(2, Integer.parseInt(String.valueOf(card.getAccountNumber())));
        preparedStatement.setString(3, String.valueOf(card.getPin()));
        preparedStatement.execute();
        return card;
    }

    @Override
    public Card edit(Card card) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE CARD SET id = ?, accountNumber = ?, pin = ? WHERE id = ?"
        );
        preparedStatement.setInt(1, Integer.parseInt(String.valueOf(card.getAccountNumber())));
        preparedStatement.setString(2, String.valueOf(card.getPin()));
        preparedStatement.setString(3, String.valueOf(card.getPin()));
        preparedStatement.execute();
        return card;
    }

    @Override
    public Card remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM CARD WHERE ID=?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<Card> findAll() throws Exception {
        List<Card> cardList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT * FROM CARD ORDER BY ID");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Card card = Card
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .accountNumber(resultSet.getString("AccountNumber"))
                    .pin(resultSet.getInt("PIN"))
                    .build();
            cardList.add(card);
        }
        return cardList;
    }

    public Card findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM CARD WHERE ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Card card = null;
        if (resultSet.next()) {
            card = Card
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .accountNumber(resultSet.getString("AccountNumber"))
                    .pin(resultSet.getInt("PIN"))
                    .build();
        }
        return card;
    }

    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
