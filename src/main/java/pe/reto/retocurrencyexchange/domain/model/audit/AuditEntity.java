package pe.reto.retocurrencyexchange.domain.model.audit;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class AuditEntity {

    @CreatedBy
    @Column("CREATED_USER")
    private String createdUser;

    @CreatedDate
    @Column("CREATED_DATE")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column("UPDATED_USER")
    private String updatedUser;

    @LastModifiedDate
    @Column("UPDATED_DATE")
    private LocalDateTime updatedDate;
}
