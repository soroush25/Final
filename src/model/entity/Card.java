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
    private Account pin;
    private Account password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
