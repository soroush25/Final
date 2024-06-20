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
public class Card {
    private int id;
    private Account accountNumber;
    private int pin;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
