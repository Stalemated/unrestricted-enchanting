package com.stalemated.unrestrictedench.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EnchantmentRules that = (EnchantmentRules) obj;
        return Objects.equals(allowed, that.allowed) &&
               Objects.equals(restricted, that.restricted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allowed, restricted);
    }
}
