package io.chibana.user.model

import javax.persistence.*

@Entity
@Table
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String
) {
    fun fullName(): String = "$firstName $lastName"
}
