package com.medfusion.pages.provisioning.partials;

/**
 * Created by lubson on 12.01.16.
 */
public enum Section {
    GENERAL_INFO("edit"),
    ACCOUNTS_IDS("accounts"),
    STATEMENT_OPTIONS("statements"),
    RATES("rates"),
    FRAUDS_RISKS("frauds");

    private final String text;

    Section(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}