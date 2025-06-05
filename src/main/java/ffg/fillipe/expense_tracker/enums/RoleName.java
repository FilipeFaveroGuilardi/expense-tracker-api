package ffg.fillipe.expense_tracker.enums;

import lombok.Getter;

@Getter
public enum RoleName {
    USER("USER"),
    ADMIN("ADMIN");

    private final String name;

    RoleName(String name) {
        this.name = name;
    }

}
