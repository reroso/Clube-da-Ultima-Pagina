# Diagrama de Classe – Clube do Livro Virtual

```mermaid
classDiagram
    class Usuario {
        - id: Long
        - nome: String
        - email: String
        - senha: String
        - tipo: PerfilEnum
    }

    class GrupoLeitura {
        - id: Long
        - nome: String
        - descricao: String
        - livroAtual: String
    }

    class Livro {
        - id: Long
        - titulo: String
        - autor: String
        - genero: String
    }

    class Leitura {
        - id: Long
        - usuarioId: Long
        - livroId: Long
        - progresso: int
    }

    Usuario "1" --> "0..*" GrupoLeitura : participa
    GrupoLeitura "1" --> "0..*" Livro : possui
    Usuario "1" --> "0..*" Leitura : realiza