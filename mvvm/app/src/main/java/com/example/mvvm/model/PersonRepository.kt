package com.example.mvvm.model

class PersonRepository {

    fun login(email: String, password: String): Boolean {
        return (email == "test@email.com" && password == "1234")
    }
}