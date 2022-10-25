package in.joshbetz.careproject.user;

public enum UserRole {

    USER,
    RESEARCH_TEAM,
    HEALTHCARE_STAFF;


    public static UserRole getFromName(String name) {
        for(UserRole role : values()) {
            if(role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }
}
