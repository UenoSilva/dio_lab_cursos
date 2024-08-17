package me.dio.credit.application.system.service

import me.dio.credit.application.system.model.Credit
import java.util.UUID

interface CreditService {

    fun save(credit: Credit): Credit

    fun findAllCustomers(customerId: Long): List<Credit>

    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit

}