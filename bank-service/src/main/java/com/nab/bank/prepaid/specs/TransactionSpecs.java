package com.nab.bank.prepaid.specs;

import com.nab.bank.prepaid.entity.TransactionEntity;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
    @Spec(path = "mobile", spec = Like.class),
})
public interface TransactionSpecs extends Specification<TransactionEntity> {

}
