package src.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import src.model.entity.enums.TransactionTypes;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Receipt {
    private int id;
    private Transaction amount;
    private Transaction transactionDateTime;
    private Account sourceAccount;
    private Account destinationAccount;
    private Customer name;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
