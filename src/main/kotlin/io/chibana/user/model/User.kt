package io.chibana.user.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @NotBlank
    var firstName: String,

    @NotBlank
    var lastName: String,

    @NotBlank
    var email: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', email='$email')"
    }


}
