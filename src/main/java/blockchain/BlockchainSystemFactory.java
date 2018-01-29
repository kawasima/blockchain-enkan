package blockchain;

import blockchain.component.Blockchain;
import enkan.Env;
import enkan.component.ApplicationComponent;
import static enkan.component.ComponentRelationship.*;
import enkan.component.jetty.JettyComponent;
import enkan.config.EnkanSystemFactory;
import enkan.system.EnkanSystem;

import static enkan.util.BeanBuilder.builder;

public class BlockchainSystemFactory implements EnkanSystemFactory {
    @Override
    public EnkanSystem create() {
        return EnkanSystem.of(
                "blockchain", new Blockchain(),
                "app", new ApplicationComponent("blockchain.BlockchainApplicationFactory"),
                "http", builder(new JettyComponent())
                        .set(JettyComponent::setPort, Env.getInt("PORT", 3000))
                        .build()

        ).relationships(
                component("http").using("app"),
                component("app").using("blockchain")
        );
    }
}
