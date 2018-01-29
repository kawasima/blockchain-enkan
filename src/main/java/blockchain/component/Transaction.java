package blockchain.component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private String sender;
    private String recipient;
    private long amount;
}
