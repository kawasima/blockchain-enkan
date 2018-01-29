package blockchain.boundary;

import blockchain.component.Transaction;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class ForgedBlock implements Serializable {
    private final String message;
    private final long index;
    private final List<Transaction> transactions;
    private final long proof;
    private final String previousHash;
}
