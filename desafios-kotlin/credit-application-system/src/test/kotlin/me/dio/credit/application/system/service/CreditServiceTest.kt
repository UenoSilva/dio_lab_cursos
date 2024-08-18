package me.dio.credit.application.system.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.model.Credit
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.impl.CreditServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.Test

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {

    @MockK(relaxed = true)
    lateinit var creditRepository: CreditRepository

    @MockK(relaxed = true)
    lateinit var customerService: CustomerService

    @Test
    fun `should create credit`() {
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val fakeCredit: Credit = buildCredit()
        val customerId = 1L
        every { customerService.findById(customerId) } returns fakeCredit.customer!!
        every { creditRepository.save(fakeCredit) } returns fakeCredit

        //when
        val actual: Credit = creditService.save(fakeCredit)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { customerService.findById(customerId) }
        verify(exactly = 1) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun `should not create credit with invalid day first instalment`() {
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val fakeCredit: Credit = buildCredit(dayFirstInstallment = LocalDate.now().plusMonths(5))
        every { creditRepository.save(fakeCredit) } answers { fakeCredit }

        //when end then
        Assertions.assertThatThrownBy { creditService.save(fakeCredit) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Invalid date!")
        verify(exactly = 0) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun `should not create credit with invalid number of instalment`() {
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val fakeCredit: Credit = buildCredit(numberOfInstallment = 57)
        every { creditRepository.save(fakeCredit) } answers { fakeCredit }

        //when and then
        Assertions.assertThatThrownBy { creditService.save(fakeCredit) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Invalid number of instalment!")
        verify(exactly = 0) { creditRepository.save(fakeCredit) }
    }

    @Test
    fun `should find all customers`() {
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val listCredits: List<Credit> = listOf(buildCredit(), buildCredit(), buildCredit())
        val customerId = 1L
        every { creditRepository.findAllByCustomer(customerId) } returns listCredits

        //when
        val actual: List<Credit> = creditService.findAllCustomers(customerId)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isNotEmpty
        Assertions.assertThat(actual).isSameAs(listCredits)
    }

    @Test
    fun `should find credit code for valid customer id and credit code`() {
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val customerId = 1L
        val fakeCreditCode: UUID = UUID.randomUUID()
        val fakeCredit: Credit = buildCredit(customer = Customer(id = customerId))

        every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit

        //when
        val actual: Credit = creditService.findByCreditCode(customerId, fakeCreditCode)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCredit)
        verify(exactly = 1) { creditRepository.findByCreditCode(fakeCreditCode) }
    }

    @Test
    fun `should not find credit code for invalid credit code`() {
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val customerId = 1L
        val invalidCreditCode: UUID = UUID.randomUUID()

        every { creditRepository.findByCreditCode(invalidCreditCode) } returns null

        //when and then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, invalidCreditCode) }
            .isInstanceOf(BusinessException::class.java)
            .hasMessage("Credit code $invalidCreditCode not find")
        verify(exactly = 1) { creditRepository.findByCreditCode(invalidCreditCode) }
    }

    @Test
    fun `should not find credit for invalid customer id`(){
        // Manual injection
        val creditService: CreditService = CreditServiceImpl(creditRepository, customerService)

        //given
        val customerId = 1L
        val fakeCreditCode: UUID = UUID.randomUUID()
        val fakeCredit: Credit = buildCredit(customer = Customer(id = 2L))

        every { creditRepository.findByCreditCode(fakeCreditCode) } returns fakeCredit

        //when then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, fakeCreditCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Contact admin")
        verify { creditRepository.findByCreditCode(fakeCreditCode) }
    }

    companion object {
        fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(500),
            dayFirstInstallment: LocalDate = LocalDate.now(),
            numberOfInstallment: Int = 1,
            customer: Customer = CustomerServiceTest.buildCustomer()
        ) = Credit(
            numberOfInstallment = numberOfInstallment,
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            customer = customer
        )
    }
}