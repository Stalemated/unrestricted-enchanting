package com.stalemated.unrestrictedench.model;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentRules {
    public List<String> allowed = new ArrayList<>();
    public List<String> restricted = new ArrayList<>();

    public EnchantmentRules() {}
    
    public EnchantmentRules(EnchantmentRules other) {
        if (other != null) {
            if (other.allowed != null) this.allowed = new ArrayList<>(other.allowed);
            if (other.restricted != null) this.restricted = new ArrayList<>(other.restricted);
        }
    }
}
