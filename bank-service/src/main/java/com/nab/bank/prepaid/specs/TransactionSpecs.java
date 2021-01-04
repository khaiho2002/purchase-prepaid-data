package com.nab.bank.prepaid.specs;

import com.nab.bank.prepaid.entity.TransactionEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
    @Spec(path = "id", spec = Equal.class),
    @Spec(path = "mobile", spec = Like.class),
    @Spec(path = "status", spec = Equal.class),
    @Spec(path = "purchaseDate", params = {"purchaseDateAfter"},
        spec = GreaterThanOrEqual.class, config = "yyyy/MM/dd HH:mm:ss"),
    @Spec(path = "purchaseDate", params = {"purchaseDateBefore"},
        spec = LessThanOrEqual.class, config = "yyyy/MM/dd HH:mm:ss"),
})
public interface TransactionSpecs extends Specification<TransactionEntity> {

}
