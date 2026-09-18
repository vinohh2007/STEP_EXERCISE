package pageturner;

public class LibraryMember {
   
    private String membershipPin;
    
    String branchCode;
    
    protected double finesOwed;
    
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

    
    public void setMembershipId(String id) {
        if (!membershipIdSet) {
            this.membershipId = id;
            this.membershipIdSet = true;
        }
      
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

  
    public void setSecurityAnswer(String answer) {
        this.securityAnswerHash = String.valueOf(answer.hashCode());
    }
}
