package src.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import src.model.entity.enums.TransactionTypes;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Reciept {
    private Transaction id;
    private Transaction amount;
    private Transaction transactionDateTime;
    private TransactionTypes transactionType;
    private Account sourceAccount;
    private Account destinationAccount;
    private Customer firstName;
    private Customer lastName;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
