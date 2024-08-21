package me.dio.credit.application.system.controler

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.credit.application.system.dto.CustomerDto
import me.dio.credit.application.system.dto.CustomerUpdateDto
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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CustomerResourceTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should create a customer and return 201 status`() {
        //given
        val customerDto = buildCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        //when then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Ueno"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Souza Silva"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("02883778264"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ueno@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("68639000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua Santa Maria"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not create a customer with same CPF and return 409 status `() {
        //give
        customerRepository.save(buildCustomerDto().toEntity())
        val customerDto = buildCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Conflict! Fields uniques"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(409))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.dao.DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not save a customer with first name empty and return 400`() {
        //given
        val customerDto: CustomerDto = buildCustomerDto(firstName = "")
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should find by id and return 200 status`() {
        //given
        val customer = customerRepository.save(buildCustomerDto().toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${customer.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should not find customer within invalid id and return 400 status`() {
        //given
        val invalidId = 2L

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/$invalidId")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
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
    fun `should delete customer by id and return 204 status`() {
        //given
        val customer = customerRepository.save(buildCustomerDto().toEntity())

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${customer.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should not delete by id and return 400 status`() {
        //given
        val invalidId = 3L

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/$invalidId")
                .accept(MediaType.APPLICATION_JSON)
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
    fun `should update a customer and return 200 status`() {
        //given
        val customer = customerRepository.save(buildCustomerDto().toEntity())
        val customerUpdateDto = buildCustomerUpadteDto()
        val valueAsString = objectMapper.writeValueAsString(customerUpdateDto)

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=${customer.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("UenoUpdate"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Souza Silva Update"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("02883778264"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("ueno@gmail.com"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.income").value(1500.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value("68639000"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua Santa Maria"))
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `should not update a customer with invalid id and return 400 status`() {
        //given
        val invalidId = 3L
        val customerUpdateDto = buildCustomerUpadteDto()
        val valueAsString = objectMapper.writeValueAsString(customerUpdateDto)

        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.patch("$URL?customerId=${invalidId}")
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

    private fun buildCustomerUpadteDto(
        firstName: String = "UenoUpdate",
        lastName: String = "Souza Silva Update",
        income: BigDecimal = BigDecimal.valueOf(1500.0),
        zipCode: String = "68639000",
        street: String = "Rua Santa Maria"
    ) = CustomerUpdateDto(
        firstName = firstName,
        lastName = lastName,
        income = income,
        zipCode = zipCode,
        street = street
    )

    companion object {
        const val URL = "/api/customers"
    }
}