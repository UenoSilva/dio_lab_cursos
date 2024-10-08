package me.dio.credit.application.system.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tb_clients")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var firstName: String = "",
    @Column(nullable = false)
    var lastName: String = "",
    @Column(nullable = false, unique = true)
    var cpf: String = "",
    @Column(nullable = false)
    var income: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    val password: String = "",
    @Column(nullable = false)
    @Embedded
    var address: Address = Address(),
    @Column(nullable = false)
    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
        mappedBy = "customer"
    )
    var credit: List<Credit> = mutableListOf(),
)
