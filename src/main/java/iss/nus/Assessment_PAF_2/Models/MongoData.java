package iss.nus.Assessment_PAF_2.Models;

import org.bson.Document;

public class MongoData {
    private String underscoreId;
    private String accountId;
    private String name;
    private Double amount;

    private static final String FIELD_UNDERSCORE_ID = "_id";
    private static final String FIELD_ACCOUNT_ID = "account_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_AMOUNT = "amount";


    public static MongoData fromDocument(Document doc) {
        MongoData data = new MongoData();
        data.setUnderscoreId(doc.getObjectId(FIELD_UNDERSCORE_ID).toString());
        data.setAccountId(doc.getString(FIELD_ACCOUNT_ID));
        data.setName(doc.getString(FIELD_NAME));
        data.setAmount(doc.getDouble(FIELD_AMOUNT));

        return data;
    }

    @Override
    public String toString() {
        return "MongoData [underscoreId=" + underscoreId + ", accountId=" + accountId + ", name=" + name + ", amount="
                + amount + "]";
    }
    public String getUnderscoreId() {
        return underscoreId;
    }
    public void setUnderscoreId(String underscoreId) {
        this.underscoreId = underscoreId;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    

}
