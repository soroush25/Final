package src.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import src.model.entity.enums.BillTypes;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Bill {
        private int id;
        private Customer customerId;
        private String billNumber;
        private BillTypes billTypes;
        private int amount;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
