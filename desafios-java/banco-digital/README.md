## Desafio Banco Digital com Java e Orientação a Objetos

### Descrição

Desafio de java, onde é implementado um banco digital com: usuário e contas (corrente e poupança). Também é implementado servicos de saquer, deposito e impressão de extrato.

### Diagrama de classes

```mermaid
classDiagram
  class Banco {
    - banco: String
    - contas: Map<String, List<Conta>>
    + adicionarConta(conta: Conta): void
    + removerConta(conta: Conta): void
    + listarContas(): List<Conta>
    + buscarConta(numero: int): Conta
  }

  class Conta {
    - agencia: int
    - numero: int
    - saldo: double
    - cliente: Cliente
    + getAgencia(): int
    + getNumero(): int
    + getSaldo(): double
    + getCliente(): Cliente
    + depositar(valor: double): void
    + sacar(valor: double): void
    + transferir(valor: double, conta: Conta): void
    + imprimirExtrato(): void
  }

  class Cliente {
    - nome: String
    + getNome(): String
  }

  class ContaCorrente {
  }

  class ContaPoupanca {
  }

  interface ContaInterface {
    + sacar(valor: double): void
    + depositar(valor: double): void
    + transferir(valor: double, conta: ContaInterface): void
    + imprimirExtrato(): void
  }

  Conta <|-- ContaCorrente
  Conta <|-- ContaPoupanca
  Conta <|.. ContaInterface

```