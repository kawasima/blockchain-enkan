package blockchain.controller;

import blockchain.boundary.TransactionRequest;
import blockchain.component.Blockchain;

import javax.inject.Inject;

public class BlockchainController {
    @Inject
    private Blockchain blockchain;

    public void newTransaction(TransactionRequest req) {
        long index = blockchain.newTransaction(
                req.getSender(),
                req.getRecipient(),
                req.getAmount());
    }
}
