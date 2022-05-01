public class Role {
    String roleName;
    String roleDescription;
    int rank;

    public String getRoleName() {
        return this.roleName;
    }

    public String getRoleDescription() {
        return this.roleDescription;
    }

    public int getRank() {
        return this.rank;
    }

    public Role(String roleName, String roleDescription, int rank) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.rank = rank;
    }
}
