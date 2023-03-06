package iss.nus.Assessment_PAF_2.Models;

public class Form {
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private String comments;

    private String fromName;
    private String fromId;
    private String toName;
    private String toId;
    
    @Override
    public String toString() {
        return "Form [fromAccount=" + fromAccount + ", toAccount=" + toAccount + ", amount=" + amount + ", comments="
                + comments + ", fromName=" + fromName + ", fromId=" + fromId + ", toName=" + toName + ", toId=" + toId
                + "]";
    }

        public String getFromName() {
        return fromName;
    }


    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }



    public String getToName() {
        return toName;
    }



    public void setToName(String toName) {
        this.toName = toName;
    }



    public String getToId() {
        return toId;
    }



    public void setToId(String toId) {
        this.toId = toId;
    }



    public String getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
        this.fromName = fromAccount.split(" ")[0];
        this.fromId = fromAccount.split(" ")[1].replace("(", "").replace(")", "");
    }
    public String getToAccount() {
        return toAccount;
    }
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
        this.toName = toAccount.split(" ")[0];
        this.toId = toAccount.split(" ")[1].replace("(", "").replace(")", "");
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    
}
