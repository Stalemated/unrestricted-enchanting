package com.stalemated.unrestrictedench.config;

import com.stalemated.lib.config.annotation.Comment;
import com.stalemated.lib.config.annotation.Nest;
import com.stalemated.lib.config.annotation.Sync;
import com.stalemated.lib.config.network.SyncMode;

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
      - Invalid IDs in "allowed" and "restricted" are removed when this file loads (a backup is saved first).
      - Rules under an invalid ID are ignored, and so are IDs of enchantments that are not installed.
      - Rules are bidirectional: adding "minecraft:infinity" to "minecraft:mending"
        automatically makes "minecraft:mending" compatible with "minecraft:infinity".
    """)
    @Nest
    public Map<String, EnchantmentRules> rules = new TreeMap<>();
}
