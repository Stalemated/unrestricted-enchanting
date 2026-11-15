package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.annotation.Comment;
import com.stalemated.lib.config.annotation.Nest;
import com.stalemated.lib.config.annotation.Sync;
import com.stalemated.lib.config.network.SyncMode;
import com.stalemated.unrestrictedench.model.EnchantmentRules;

import java.util.Map;
import java.util.TreeMap;

@Sync(SyncMode.OVERRIDE_CLIENT)
public class UnrestrictedEnchantingConfig {
    @Comment("""
    Rules for enchantment compatibilities and incompatibilities.

    Fields:
      - allowed: Forced COMPATIBLE with this enchantment (overrides vanilla restrictions).
      - restricted: Forced INCOMPATIBLE (takes absolute precedence over vanilla and allowed).
    Notes:
      - All entries with invalid registry IDs get ignored.
      - Rules are bidirectional: adding "minecraft:infinity" to "minecraft:mending"
        automatically makes "minecraft:mending" compatible with "minecraft:infinity".
    """)
    @Nest
    public Map<String, EnchantmentRules> rules = new TreeMap<>();
}
