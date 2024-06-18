package src.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Bill {
        private int id;
        private Customer customerId;
        private int billNumber;
        private int amount;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
