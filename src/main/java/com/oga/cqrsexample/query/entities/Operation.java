package com.oga.cqrsexample.query.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oga.cqrsexample.commonapi.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
