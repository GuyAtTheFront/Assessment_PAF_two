package iss.nus.Assessment_PAF_2.Repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class AcccountsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private final String SQL_GET_NAMES_CONCAT_ID = 
    """
        SELECT CONCAT(name, ' (', account_id, ')') AS name from accounts;
    """;

    private final String SQL_GET_BALANCE_BY_ID = 
    """
        SELECT balance from accounts where account_id = ?;
    """;

    private final String SQL_UPDATE_BALANCE_BY_DELTA_AND_ID = 
    """
        UPDATE accounts SET balance = balance + ? where account_id = ?;
    """;


    public List<String> getNamesForForm() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_NAMES_CONCAT_ID);

        List<String> names = new LinkedList<>();
        while(rs.next()) {
            names.add(rs.getString("name"));
        }

        return names;
    }

    public Optional<Double> getBalanceByID(String id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_BALANCE_BY_ID, id);

        return (rs.next()) ? Optional.of(rs.getDouble("balance")) : Optional.empty();
    }

    public Boolean updateBalanceDeltaById(String id, Double amount) {

        Integer updated = jdbcTemplate.update(SQL_UPDATE_BALANCE_BY_DELTA_AND_ID, amount, id);

        return updated == 1;
    }

}
