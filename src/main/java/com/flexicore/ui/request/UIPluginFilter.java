package com.flexicore.ui.request;

import com.flexicore.model.FilteringInformationHolder;

import java.util.Set;

public class UIPluginFilter extends FilteringInformationHolder {

        private Set<String> associationReferences;

    public Set<String> getAssociationReferences() {
        return associationReferences;
    }

    public <T extends UIPluginFilter> T setAssociationReferences(Set<String> associationReferences) {
        this.associationReferences = associationReferences;
        return (T) this;
    }
}
