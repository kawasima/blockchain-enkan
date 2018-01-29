package blockchain.component;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Block {
    private long index;
    private long timestamp;
    private List<Transaction> transactions;
    private long proof;
    private String previousHash;
}
