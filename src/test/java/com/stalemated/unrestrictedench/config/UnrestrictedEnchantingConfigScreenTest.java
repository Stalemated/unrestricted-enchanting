package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.compat.yacl.SLibTesting;
import com.stalemated.lib.config.InMemoryConfigManager;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class UnrestrictedEnchantingConfigScreenTest {
    @Test
    void theConfigScreenBuilds() throws Exception {
        SLibTesting.assertScreenBuilds(
                UnrestrictedEnchantingConfigScreen.builder(null, new InMemoryConfigManager<>(new UnrestrictedEnchantingConfig(), UnrestrictedEnchantingConfig::new, null)),
                Path.of(UnrestrictedEnchantingConfigScreenTest.class.getResource("/assets/unrestricted_enchanting/lang/en_us.json").toURI())
        );
    }
}
