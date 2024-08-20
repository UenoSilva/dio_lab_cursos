package me.dio.credit.application.system.repository

import me.dio.credit.application.system.model.Address
import me.dio.credit.application.system.model.Credit
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.service.CustomerServiceTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {

    @Autowired
    lateinit var creditRepository: CreditRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach
    fun setup() {
        customer = testEntityManager.merge(buildCustomer())
        credit1 = testEntityManager.persistAndFlush(buildCredit(customer = customer))  // Reutilize o customer persistido
        credit2 = testEntityManager.persistAndFlush(buildCredit(customer = customer))  // Reutilize o customer persistido
    }

    @Test
    fun `should find credit by credit code`() {
        //give
        val creditCode1 = UUID.fromString("592ac4f3-b775-42e7-b6fe-9221bb713d5c")
        val creditCode2 = UUID.fromString("e63f12f2-50db-4451-8d8c-87a1b8aad14e")

        credit1.creditCode = creditCode1
        credit2.creditCode = creditCode2

        //when
        val fakeCredit1: Credit = creditRepository.findByCreditCode(creditCode1)!!
        val fakeCredit2: Credit = creditRepository.findByCreditCode(creditCode2)!!

        //then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(credit1)
        Assertions.assertThat(fakeCredit2).isSameAs(credit2)
    }

    @Test
    fun `should find all credits by customer id`(){
        //given
        val customerId = 1L

        //when
        val listCredits = creditRepository.findAllByCustomer(customerId)

        //then
        Assertions.assertThat(listCredits).isNotEmpty
        Assertions.assertThat(listCredits.size).isEqualTo(2)
        Assertions.assertThat(listCredits).contains(credit1, credit2)
    }

    fun buildCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(500),
        dayFirstInstallment: LocalDate = LocalDate.now(),
        numberOfInstallment: Int = 1,
        customer: Customer
    ) = Credit(
        numberOfInstallment = numberOfInstallment,
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        customer = customer
    )

    fun buildCustomer(
        id: Long = 1,
        firstName: String = "Ueno",
        lastName: String = "Souza Silva",
        cpf: String = "02883778264",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        email: String = "ueno@gmail.com",
        password: String = "1234",
        zipCode: String = "63689000",
        street: String = "Rua Sergipe"
    ) = Customer(
        id = id,
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        income = income,
        email = email,
        password = password,
        address = Address(zipCode, street)
    )

}