package iss.nus.Assessment_PAF_2.Repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import iss.nus.Assessment_PAF_2.Models.MongoData;

@Repository
public class MongoAccountsRepository {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public List<String> getNameForForm() {

        Query query = Query.query(new Criteria());
        List<Document> docs = mongoTemplate.find(query, Document.class, "accounts");
        
        // List<Document> --> List<MongoData>
        
        // List<String> datas = docs.stream()
        //                 .map(x -> MongoData.fromDocument(x))
        //                 .map(x -> "%s (%s)".formatted(x.getName(),x.getAccountId()))
        //                 .toList();

        // List<String> myNames = docs.stream()
                // .map(x -> "%s (%s)".formatted(x.getString("name"),x.getString("account_id")))
                // .toList();

        List<MongoData> datas = new LinkedList<>();

        for (Document doc : docs) {
            datas.add(MongoData.fromDocument(doc));
        }

        List<String> names = new LinkedList<>();

        for (MongoData data : datas) {
            names.add("%s (%s) - MONGO".formatted(data.getName(), data.getAccountId()));
        }
 
        return names;
    }

    public Optional<Double> getBalanceByID(String id) {

        /*
         * db.accounts.find({'account_id': 'if9l185l18'});
         */

        // Criteria criteria = Criteria.where("account_id").is(id);
        // Query query = Query.query(criteria);
        // Document doc = mongoTemplate.findOne(query, Document.class, "accounts");

        // return (null == doc) ? Optional.empty() : Optional.of(MongoData.fromDocument(doc).getAmount());

        // TODO: If id don't exist, will mongoTemplate return null or empty list

        
        /*
         * db.accounts.find({'account_id': 'if9l185l18'}, {'balance': 1, '_id': 0});
         */

        Criteria criteria = Criteria.where("account_id").is(id);
        Query query = Query.query(criteria);
        query.fields()
            .include("balance")
            .exclude("_id");
        Document doc = mongoTemplate.findOne(query, Document.class, "accounts");

        return (null == doc) ? Optional.empty() : Optional.of(Double.parseDouble(doc.getInteger("balance").toString()));
    }

    public Boolean updateBalanceDeltaById(String id, Double amount) {
        /*
         * db.accounts.updateOne({'account_id': 'if9l185l18'}, {$inc: {'balance' : 1}});
         */
        
        Criteria criteria = Criteria.where("account_id").is(id);
        Query query = Query.query(criteria);

        Update updateOps = new Update().inc("balance", amount);

        UpdateResult result = mongoTemplate.updateFirst(query, updateOps, Document.class, "accounts");

        return result.getModifiedCount() == 1;
    }

    public void insertLog(Document doc) {
        Document document = mongoTemplate.insert(doc, "logs");
        System.out.println("\n\n\n\n\n\n" + document.toJson());
        return;
    }

}
