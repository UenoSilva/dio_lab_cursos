package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.model.Customer
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.CustomerService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class CustomerServiceImpl(
    private val repository: CustomerRepository
) : CustomerService {

    override fun save(customer: Customer): Customer =
        this.repository.save(customer)

    override fun findById(id: Long): Customer {
        val customer = this.repository.findById(id).orElseThrow {
            throw BusinessException("Id $id not found")
        }
        return customer
    }

    override fun delete(id: Long) {
        val entity = this.findById(id)
        this.repository.delete(entity)
    }
}