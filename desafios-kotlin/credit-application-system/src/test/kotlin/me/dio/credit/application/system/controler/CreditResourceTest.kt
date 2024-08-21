package me.dio.credit.application.system.controler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import me.dio.credit.application.system.dto.CreditDto
import me.dio.credit.application.system.dto.CustomerDto
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditResourceTest {

    @Autowired
    lateinit var creditRepository: CreditRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() = creditRepository.deleteAll()

    @AfterEach
    fun tearDown() = creditRepository.deleteAll()

    @Test
    fun `should create a credit and return 201 status`() {
        //given
        val creditDto = builderCredit()
        val valueAsString = objectMapper.writeValueAsString(creditDto)
        customerRepository.save(buildCustomerDto(cpf = "40425253007", email = "u@gmail.com").toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
//            .andExpect(MockMvcResultMatchers.jsonPath("$.creditValue").value(200.0))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.dayFirstInstallment").value("2024-10-21"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfInstallment").value(2))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1))
            .andDo(MockMvcResultHandlers.print())

        customerRepository.deleteAll()
    }

    @Test
    fun `should not create a credit with invalid day first installment and return 400 status`() {
        //given
        val creditDto = builderCredit(dayFirstInstallment = LocalDate.now().plusMonths(5))
        val valueAsString = objectMapper.writeValueAsString(creditDto)
        customerRepository.save(buildCustomerDto(cpf = "11644388081", email = "ue1@gmail.com").toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the url"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class me.dio.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not create a credit with invalid number of installment and return 400 status`() {
        //given
        val creditDto = builderCredit(numberOfInstallment = 67)
        val valueAsString = objectMapper.writeValueAsString(creditDto)
        customerRepository.save(buildCustomerDto(cpf = "18141376020", email = "y@gmail.com").toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the url"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class me.dio.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not create a credit with invalid customer id and return 400 status`() {
        //given
        val creditDto = builderCredit(customerId = 300L)
        val valueAsString = objectMapper.writeValueAsString(creditDto)
        customerRepository.save(buildCustomerDto(cpf = "24619606073", email = "a@gmail.com").toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the url"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class me.dio.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find all customer by customer id and return 201 status`() {
        //given
        val customerDto = buildCustomerDto(cpf = "60215735013", email = "d@gmail.com").toEntity()
        customerRepository.save(customerDto)
        creditRepository.save(builderCredit().toEntity())
        creditRepository.save(builderCredit().toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL?customerId=${customerDto.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find all customer by customer id and return 400 status`() {
        //given
        val customerDto = buildCustomerDto(cpf = "84305054078", email = "ass@gmail.com").toEntity()
        val invalidId = customerDto.id?.plus(1L)
        customerRepository.save(customerDto)
        creditRepository.save(builderCredit().toEntity())
        creditRepository.save(builderCredit().toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL?customerId=${invalidId}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the url or Body"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class java.lang.NumberFormatException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find customer by credit code and return 200 status`() {
        //given
        val customerDto = buildCustomerDto().toEntity()
        val credit = builderCredit().toEntity()
        customerRepository.save(customerDto)
        creditRepository.save(credit)

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${credit.creditCode}?customerId=${customerDto.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find customer by invalid credit code and return 400 status`() {
        //given
        val customerDto = buildCustomerDto(cpf = "19530596006", email = "iu@gmail.com").toEntity()
        val creditCode = UUID.randomUUID()
        customerRepository.save(customerDto)
        creditRepository.save(builderCredit().toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${creditCode}?customerId=${customerDto.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the url"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class me.dio.credit.application.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not find customer by invalid customer id and return 400 status`() {
        //given
        val customerDto = buildCustomerDto(cpf = "85454277002", email = "asfe@gmail.com").toEntity()
        val invalidId = 44L
        val credit = builderCredit().toEntity()
        customerRepository.save(customerDto)
        creditRepository.save(credit)

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${credit.creditCode}?customerId=${invalidId}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the url or Body"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class java.lang.IllegalArgumentException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    private fun builderCredit(
        creditValue: BigDecimal = BigDecimal.valueOf(200.0),
        dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2),
        numberOfInstallment: Int = 2,
        customerId: Long = 1
    ) = CreditDto(
        creditValue = creditValue,
        dayFirstInstallment = dayFirstInstallment,
        numberOfInstallment = numberOfInstallment,
        customerId = customerId
    )

    private fun buildCustomerDto(
        firstName: String = "Ueno",
        lastName: String = "Souza Silva",
        cpf: String = "02883778264",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        email: String = "ueno@gmail.com",
        password: String = "1234",
        zipCode: String = "68639000",
        street: String = "Rua Santa Maria"
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        income = income,
        email = email,
        password = password,
        zipCode = zipCode,
        street = street
    )

    companion object {
        const val URL = "/api/credits"
    }
}