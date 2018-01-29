import enkan.system.repl.PseudoRepl;
import enkan.system.repl.ReplBoot;
import enkan.system.repl.pseudo.ReplClient;
import kotowari.system.KotowariCommandRegister;

public class DevMain {
    public static void main(String[] args) throws Exception {
        PseudoRepl repl = new PseudoRepl("blockchain.BlockchainSystemFactory");
        ReplBoot.start(repl,
                new KotowariCommandRegister());

        new ReplClient().start(repl.getPort().get());
    }
}
