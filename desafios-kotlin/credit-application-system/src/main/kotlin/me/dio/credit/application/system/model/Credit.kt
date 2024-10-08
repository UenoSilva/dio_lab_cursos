package me.dio.credit.application.system.model

import jakarta.persistence.*
import me.dio.credit.application.system.enummeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "tb_credits")
data class Credit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    var creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    val creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false)
    val dayFirstInstallment: LocalDate,
    @Column(nullable = false)
    val numberOfInstallment: Int = 0,
    @Enumerated
    val status: Status = Status.IN_PROGRESS,
    @ManyToOne
    var customer: Customer? = null
)
