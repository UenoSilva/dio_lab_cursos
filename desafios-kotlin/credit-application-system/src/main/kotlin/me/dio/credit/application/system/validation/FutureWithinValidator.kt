package me.dio.credit.application.system.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate

class FutureWithinValidator : ConstraintValidator<FutureWithin, LocalDate> {

    private var months: Int = 0

    override fun initialize(constraintAnnotation: FutureWithin) {
        this.months = constraintAnnotation.months
    }

    override fun isValid(p0: LocalDate?, p1: ConstraintValidatorContext?): Boolean {
        if (p0 == null) {
            return true
        }

        val today = LocalDate.now()
        val maxDate = today.plusMonths(months.toLong())

        return !p0.isBefore(today) && !p0.isAfter(maxDate)
    }
}