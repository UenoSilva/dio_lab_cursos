package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.model.Credit
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.CreditService
import me.dio.credit.application.system.service.CustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class CreditServiceImpl(
    private val repository: CreditRepository,
    private val service: CustomerService
) : CreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = service.findById(credit.customer?.id!!)
        }
        return repository.save(credit)
    }

    override fun findAllCustomers(customerId: Long): List<Credit> {
        return this.repository.findAllByCustomer(customerId)
    }

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit =
            this.repository.findByCreditCode(creditCode) ?: throw BusinessException("Credit code $creditCode not find")
        return if (credit.customer?.id == customerId) credit else throw IllegalArgumentException("Contact admin")
    }
}