public class Role {
    String roleName;
    String roleDescription;
    int rank;
    String roleType;

    public String getRoleName() {
        return this.roleName;
    }

    public String getRoleDescription() {
        return this.roleDescription;
    }

    public int getRank() {
        return this.rank;
    }

    public Role(String roleName, String roleDescription, int rank, String roleType) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.rank = rank;
        this.roleType = roleType;
    }

    public void printRole()
    {
        System.out.print("Role " + roleName.toUpperCase() + " has rank " + rank + ". ");
    }
}
