package org.ada.biblioteca.repository.mongo.loan;

import org.ada.biblioteca.bo.mongo.LoanMongo;
import org.ada.biblioteca.bo.mongo.LoanMongoId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepositoryNoSql extends MongoRepository<LoanMongo, LoanMongoId> {
}
