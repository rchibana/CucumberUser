# language: pt
# encoding: utf-8

  @userTest
  Funcionalidade: Usuários sendo cadastrados e consultados na plataforma

    Cenário: Usuário cadastrado com sucesso
      Dado um usuário com nome "Rodrigo"
      E sobrenome "Chibana"
      E email "chibana@test.com"
      Quando fizer a requisição de cadastro de usuário
      Então um novo cadastro deve ser criado com sucesso

