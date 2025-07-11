package com.automationexercise.tests.util.screenshot;

import lombok.Builder;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class ScreenDiffTable {

    private static final int
            KEYS_COLUMN_LENGTH = 16,
            VALUES_COLUMN_LENGTH = 14;

    private static final String
            SEPARATOR = "+%1$s+%2$s+%2$s+%n".formatted("-".repeat(KEYS_COLUMN_LENGTH), "-".repeat(VALUES_COLUMN_LENGTH)),
            ROW_FORMAT = "|%-" + KEYS_COLUMN_LENGTH + "s|%-" + VALUES_COLUMN_LENGTH + "s|%-" + VALUES_COLUMN_LENGTH + "s|\n";

    private final long expectedDiffSize;
    private final long actualDiffSize;

    private final double expectedDiffPercent;
    private final double actualDiffPercent;

    private final StringBuilder diffTable;

    @Builder
    ScreenDiffTable(long expectedDiffSize, long actualDiffSize, double expectedDiffPercent, double actualDiffPercent) {
        this.expectedDiffSize = expectedDiffSize;
        this.actualDiffSize = actualDiffSize;
        this.expectedDiffPercent = expectedDiffPercent;
        this.actualDiffPercent = actualDiffPercent;
        this.diffTable = new StringBuilder();
        buildDiffTable();
    }

    public String getDiffTable() {
        return diffTable.toString();
    }

    private void buildDiffTable() {
        appendSeparator();
        appendRow("", "Expected", "Actual");
        appendSeparator();
        appendRow("diff_size", String.valueOf(expectedDiffSize), actualDiffSize);
        appendRow("diff_percent", expectedDiffPercent, String.format("%.3f", actualDiffPercent));
        appendSeparator();
    }

    private void appendSeparator() {
        diffTable.append(SEPARATOR);
    }

    private <T> void appendRow(String label, T expected, T actual) {
        diffTable.append(String.format(ROW_FORMAT, label, expected, actual));
    }

}
