package blockchain.boundary;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionRequest implements Serializable {
    private String sender;
    private String recipient;
    private Long amount;
}
