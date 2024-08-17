package me.dio.credit.application.system.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import me.dio.credit.application.system.model.Credit
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.validation.FutureWithin
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "Invalid input ") val creditValue: BigDecimal,
    @field:FutureWithin(
        months = 3,
        message = "The first installment date must be within three months from today"
    ) val dayFirstInstallment: LocalDate,
    @Min(value = 1, message = "Min 2 parcels")
    @Max(value = 48, message = "Max 20 parcels") val numberOfInstallment: Int,
    @field:NotNull(message = "Invalid input ") val customerId: Long
) {
    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstInstallment,
        numberOfInstallment = this.numberOfInstallment,
        customer = Customer(id = this.customerId)
    )
}
