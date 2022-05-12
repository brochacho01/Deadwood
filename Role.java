public class Role {
    private String roleName;
    private String roleDescription;
    private int rank;
    private String roleType;

    // Returns the name of the role
    public String getRoleName() {
        return this.roleName;
    }

    public String getRoleType(){
        return this.roleType;
    }

    // Returns the description of the role
    public String getRoleDescription() {
        return this.roleDescription;
    }

    // returns the rank of the role
    public int getRank() {
        return this.rank;
    }

    // Constructor
    public Role(String roleName, String roleDescription, int rank, String roleType) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.rank = rank;
        this.roleType = roleType;
    }

    // Prints the name and rank of a role
    public void printRole() {
        System.out.print("Role " + roleName.toUpperCase() + " has rank " + rank + ". ");
    }
}
