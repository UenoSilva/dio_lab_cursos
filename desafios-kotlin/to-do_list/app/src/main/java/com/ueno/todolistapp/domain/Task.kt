package com.ueno.todolistapp.domain

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val date: String,
    val hour: String
)
