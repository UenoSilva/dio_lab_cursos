
enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

data class Usuario (val nome : String, val matricula : Int)

data class ConteudoEducacional(val nome: String, val duracao: Int = 60, val nivel: Nivel){
    var concluida : Boolean = false

    fun conteudoConcuido(){
        concluida = true
    }

    override fun toString(): String {
        return "(Nome= $nome, duracao= $duracao, nivel= $nivel, concluida=$concluida)"
    }
}

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>) {

    val inscritos = mutableListOf<Usuario>()

    fun matricular(usuario: Usuario) {
        //TODO("Utilize o parâmetro $usuario para simular uma matrícula (usar a lista de $inscritos).")
        inscritos.add(usuario)
    }

    override fun toString(): String {
        var str = "Formação: $nome\n" +
                "\tConteudos=$conteudos\n" +
                "\tAlunos=$inscritos"
        return str
    }
}

fun informacoesDaFormacaoPorUsuario(usuario: Usuario, formacao: Formacao) : Unit {
    if(usuario in formacao.inscritos){
        println("Nome = ${usuario.nome}, Formação=${formacao.nome}\n\t${formacao.conteudos}")
    } else{
        println("Usuário não cadrastrado na formação ${formacao.nome}")
    }
}

fun main() {
    //TODO("Analise as classes modeladas para este domínio de aplicação e pense em formas de evoluí-las.")
    //TODO("Simule alguns cenários de teste. Para isso, crie alguns objetos usando as classes em questão.")

    var use1 = Usuario("Ueno", 20233089)
    var use2 = Usuario("Silva", 20233090)

    var java_conteudos : MutableList<ConteudoEducacional> = mutableListOf(
        ConteudoEducacional("Introdução",80,Nivel.BASICO),
        ConteudoEducacional("POO", 180, Nivel.INTERMEDIARIO))

    var java = Formacao("Java Developer",java_conteudos)
    java.matricular(use1)
    java.matricular(use2)

    println(java)

    println("\nInformações:")
    informacoesDaFormacaoPorUsuario(use1, java)
}