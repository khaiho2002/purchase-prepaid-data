package com.nab.bank.prepaid.entity;

import com.nab.bank.prepaid.type.TransactionStatus;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {

  @Id
  @GeneratedValue(
      generator = "UUID"
  )
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator",
      parameters = {@Parameter(
          name = "uuid_gen_strategy_class",
          value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
      )}
  )
  @Column(
      name = "id",
      updatable = false,
      nullable = false
  )
  private UUID id;

  @Column(name = "mobile")
  String mobile;

  @Column(name = "code")
  String code;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  TransactionStatus status;

  @Column(name = "purchase_date", insertable = false, updatable = false)
  Timestamp purchaseDate;
}
