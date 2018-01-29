package blockchain.controller;

import blockchain.boundary.ForgedBlock;
import blockchain.boundary.TransactionRequest;
import blockchain.component.Block;
import blockchain.component.Blockchain;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BlockchainController {
    @Inject
    private Blockchain blockchain;

    private String nodeIdentifier = UUID.randomUUID().toString().replaceAll("\\-", "");

    public Map<String, Object> chain() {
        Map<String, Object> response = new HashMap<>();
        List<Block> chain = blockchain.getChain();
        response.put("chain", chain);
        response.put("lengh", chain.size());
        return response;
    }

    public void newTransaction(TransactionRequest req) {
        long index = blockchain.newTransaction(
                req.getSender(),
                req.getRecipient(),
                req.getAmount());
    }

    public ForgedBlock mine() {
        Block lastBlock = blockchain.getLastBlock();
        long lastProof = lastBlock.getProof();
        long proof = blockchain.proofOfWork(lastProof);

        // Reward mining
        blockchain.newTransaction(
                /*sender*/ "0",
                nodeIdentifier,
                /*amount*/ 1);

        String previousHash = Blockchain.hash(lastBlock);
        Block block = blockchain.newBlock(proof, previousHash);

        return new ForgedBlock("New Block Forged",
                block.getIndex(),
                block.getTransactions(),
                block.getProof(),
                block.getPreviousHash());
    }
/*

 */
}
