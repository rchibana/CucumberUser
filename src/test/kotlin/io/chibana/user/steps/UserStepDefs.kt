package io.chibana.user.steps

import io.chibana.user.model.User
import io.cucumber.java8.Pt
import io.restassured.RestAssured.given
import io.restassured.RestAssured.useRelaxedHTTPSValidation
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification

class UserStepdefs: Pt {

    private lateinit var user: User
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String

    init {
        Dado("um usuário com nome {string}"){ firstName: String -> this.firstName = firstName }

        E("sobrenome {string}") { lastName: String -> this.lastName = lastName }

        E("email {string}") { email: String -> this.email = email }

        Quando("fizer a requisição de cadastro de usuário") {
            user = User(0, firstName, lastName, email).let {
                given()
                    .spec(builRequestSpec())
                    .body(it)
                    .post("/users")
                    .then()
                    .statusCode(201)
                    .extract()
                    .body()
                    .`as`(User::class.java)
            }

        }
        Então("um novo cadastro deve ser criado com sucesso$") {
            user.let {
                given()
                    .spec(builRequestSpec())
                    .get("/users/${it.id}")
                    .then()
                    .statusCode(200)
            }
        }
    }

    private fun builRequestSpec(): RequestSpecification? = RequestSpecBuilder()
        .apply {
            setBaseUri("http://localhost:8080")
            useRelaxedHTTPSValidation()
            setContentType("application/json")
        }.build()


}