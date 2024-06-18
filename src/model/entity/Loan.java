package src.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import src.model.entity.enums.LoanType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Loan {
    private int id;
    private Double interest;
    private Double amount;
    private LoanType loanType;
    private LocalDateTime startDate;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
