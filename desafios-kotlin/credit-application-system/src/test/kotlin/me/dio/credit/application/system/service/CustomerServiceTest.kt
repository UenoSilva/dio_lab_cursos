package me.dio.credit.application.system.service

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.model.Address
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.impl.CustomerServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.Optional
import java.util.Random
import kotlin.test.Test

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK(relaxed = true)
    lateinit var customerRepository: CustomerRepository

    // Teste Unitário: CustomerService - save
    @Test
    fun `should create customer`() {
        // Manual injection
        val customerService = CustomerServiceImpl(customerRepository)

        //given
        val fakeCustomer: Customer = buildCustomer()
        every { customerRepository.save(any()) } returns fakeCustomer //todas vez que save for chamado irá retornar um fakeCustomer

        //when
        val actual: Customer = customerService.save(fakeCustomer) // mockar

        //then
        Assertions.assertThat(actual).isNotNull //verificar se não é nulo
        Assertions.assertThat(actual).isSameAs(fakeCustomer) // realizar uma comparação
        verify(exactly = 1) { customerRepository.save(fakeCustomer) } //verifica se salvar apenas uma vez
    }

    //Teste Unitário: CustomerService - findById
    @Test
    fun `should find custmoner by id`() {
        // Manual injection
        val customerService = CustomerServiceImpl(customerRepository)

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)

        //when
        val actual: Customer = customerService.findById(fakeId)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java) //
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `should not find customer by invalid id and throw BusinessException`() {
        // Manual injection
        val customerService = CustomerServiceImpl(customerRepository)

        //given
        val fakeId: Long = Random().nextLong()
        every { customerRepository.findById(fakeId) } returns Optional.empty()

        //when and then
        Assertions.assertThatExceptionOfType(BusinessException::class.java) // verificar se dar a exception esperada
            .isThrownBy { customerService.findById(fakeId) } // local onde o error pode ocorrer
            .withMessage("Id $fakeId not found") // mensagem caso o error aconteça
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `should delete by id`() {
        // Manual injection
        val customerService = CustomerServiceImpl(customerRepository)

        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer) } just runs // just runs para funções que não tem retorno

        //when
        customerService.delete(fakeId)

        //then
        verify(exactly = 1) { customerRepository.findById(fakeId) }
        verify(exactly = 1) { customerRepository.delete(fakeCustomer) }
    }

    companion object {
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
}