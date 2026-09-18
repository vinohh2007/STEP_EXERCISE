package pageturner;

public class LibraryMember {
    // Inaccessible outside LibraryMember itself.
    private String membershipPin;
    // Reachable only by classes in the same package.
    String branchCode;
    // Reachable by same-package code (and subclasses, per Problem 2's rules).
    protected double finesOwed;
    // Reachable from anywhere.
    public String displayName;

    private String membershipId;
    private boolean membershipIdSet;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash;

    public LibraryMember() {
    }

    public String getMembershipId() {
        return membershipId;
    }

    // Write-once: a public setter exists (required by the JavaBean scanner),
    // but only the first call ever takes effect.
    public void setMembershipId(String id) {
        if (!membershipIdSet) {
            this.membershipId = id;
            this.membershipIdSet = true;
        }
        // later calls are silently ignored
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // True write-only property: stores a one-way transform, no getter anywhere.
    public void setSecurityAnswer(String answer) {
        this.securityAnswerHash = String.valueOf(answer.hashCode());
    }
}
