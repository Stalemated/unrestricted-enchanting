package com.stalemated.unrestrictedench;

import com.stalemated.unrestrictedench.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UnrestrictedEnchanting {
    public static final String MOD_ID = "unrestricted_enchanting";
    public static final Logger LOGGER = LoggerFactory.getLogger("Unrestricted Enchanting");

    public static void init() {
        ConfigManager.register();
    }
}
