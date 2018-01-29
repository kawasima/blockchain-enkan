package blockchain.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import enkan.component.ComponentLifecycle;
import enkan.component.SystemComponent;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Blockchain extends SystemComponent {
    private static ObjectMapper mapper = new ObjectMapper();
    private LinkedList<Block> chain;
    private List<Transaction> currentTransaction;

    @Override
    protected ComponentLifecycle<Blockchain> lifecycle() {
        return new ComponentLifecycle<Blockchain>() {
            @Override
            public void start(Blockchain component) {
                component.chain = new LinkedList<>();
                component.currentTransaction = new ArrayList<>();
                component.newBlock(100, "1");
            }

            @Override
            public void stop(Blockchain component) {
                component.chain.clear();
                component.currentTransaction.clear();
            }
        };
    }

    public Block newBlock(long proof, String previousHash) {
        Block block = new Block(
                /*index*/        chain.size() + 1,
                /*timestamp*/    System.currentTimeMillis(),
                /*transactions*/ new ArrayList<>(currentTransaction),
                /*proof*/        proof,
                /*previous hash*/previousHash);
        currentTransaction.clear();
        chain.add(block);
        return block;
    }

    public long newTransaction(String sender, String recipient, long amount) {
        Transaction tx = new Transaction(sender, recipient, amount);
        currentTransaction.add(tx);
        return getLastBlock().getIndex() + 1;
    }

    public Block getLastBlock() {
        return chain.getLast();
    }

    public long proofOfWork(long lastProof) {
        long proof = 0;
        while (!validProof(lastProof, proof)) {
            proof += 1;
        }

        return proof;
    }

    public List<Block> getChain() {
        return Collections.unmodifiableList(chain);
    }

    public static boolean validProof(long lastProof, long proof) {
        String guess = Long.toString(lastProof) + Long.toString(proof);
        return DigestUtils.sha256Hex(guess).startsWith("0000");
    }

    public static String hash(Block block) {
        try {
            String json = mapper.writeValueAsString(block);
            return DigestUtils.sha256Hex(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
