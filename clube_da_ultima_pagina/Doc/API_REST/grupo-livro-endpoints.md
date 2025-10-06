# GrupoLivroRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/grupo-livros
```

---

## Descrição
Este controller gerencia a relação entre grupos e livros, permitindo associar livros específicos aos grupos de leitura.

---

## Endpoints Disponíveis

### GET - Listar todas as relações grupo-livro
```http
GET /api/v1/grupo-livros
```
*Não requer corpo da requisição*

### GET - Buscar relação por ID
```http
GET /api/v1/grupo-livros/1
```
*Não requer corpo da requisição*

### POST - Criar nova relação grupo-livro
```http
POST /api/v1/grupo-livros
Content-Type: application/json

{
    "grupo": {
        "id": 1
    },
    "livro": {
        "id": 2
    },
    "observacao": "Livro escolhido para discussão no primeiro semestre. Foco na análise das técnicas de horror psicológico e construção de personagens."
}
```

### PUT - Atualizar relação grupo-livro
```http
PUT /api/v1/grupo-livros/1
Content-Type: application/json

{
    "grupo": {
        "id": 1
    },
    "livro": {
        "id": 2
    },
    "observacao": "Livro escolhido para discussão no primeiro semestre. Foco na análise das técnicas de horror psicológico, construção de personagens e o estilo narrativo único de Stephen King."
}
```

### DELETE - Excluir relação grupo-livro
```http
DELETE /api/v1/grupo-livros/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/grupo-livros \
  -H "Content-Type: application/json" \
  -d '{
    "grupo": {
        "id": 1
    },
    "livro": {
        "id": 2
    },
    "observacao": "Livro selecionado para leitura do próximo mês."
  }'
```

---

## Validações Importantes

- **Grupo**: 
  - ⚠️ **OBRIGATÓRIO** - Não pode ser null
  - Deve referenciar um grupo existente através do ID
- **Livro**: 
  - ⚠️ **OBRIGATÓRIO** - Não pode ser null
  - Deve referenciar um livro existente através do ID
- **🚨 REGRA DE UNICIDADE**: Não pode existir associação duplicada entre o mesmo grupo e livro
- **Observação**: Opcional, texto livre para anotações sobre a relação

## ❌ Principais Causas do Erro 400

### 1. **Associação Duplicada (Mais Comum)**
```json
// ❌ ERRO - Se já existe Grupo 1 ↔ Livro 1
{
  "grupo": {"id": 1},
  "livro": {"id": 1},
  "observacao": "Tentativa de duplicar associação"
}

// ✅ CORRETO - Associação nova
{
  "grupo": {"id": 1},
  "livro": {"id": 2},  // Livro diferente
  "observacao": "Nova associação válida"
}
```

### 2. **IDs Inválidos ou Inexistentes**
```json
// ❌ ERRO - Grupo não existe
{
  "grupo": {"id": 999},
  "livro": {"id": 1}
}

// ❌ ERRO - Livro não existe
{
  "grupo": {"id": 1},
  "livro": {"id": 999}
}
```

### 3. **Campos Obrigatórios Ausentes**
```json
// ❌ ERRO - Grupo faltando
{
  "livro": {"id": 1},
  "observacao": "Teste"
}

// ❌ ERRO - Livro faltando
{
  "grupo": {"id": 1},
  "observacao": "Teste"
}
```

---

## 🔧 Troubleshooting - Erro 400

### Passo a passo para debugar:

1. **Verificar associações existentes:**
   ```bash
   curl -X GET http://localhost:8080/api/v1/grupo-livros
   ```

2. **Verificar se grupo existe:**
   ```bash
   curl -X GET http://localhost:8080/api/v1/grupos/1
   ```

3. **Verificar se livro existe:**
   ```bash
   curl -X GET http://localhost:8080/api/v1/livros/1
   ```

4. **Testar com combinação não duplicada:**
   ```bash
   curl -X POST http://localhost:8080/api/v1/grupo-livros \
     -H "Content-Type: application/json" \
     -d '{
       "grupo": {"id": 1},
       "livro": {"id": 2},
       "observacao": "Associação teste"
     }'
   ```

### 💡 Dica: Como encontrar combinações válidas
- Liste grupos: `GET /api/v1/grupos`
- Liste livros: `GET /api/v1/livros`  
- Liste associações existentes: `GET /api/v1/grupo-livros`
- Use IDs que não estejam já associados

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Relação criada com sucesso (POST)
- **204**: No Content - Relação excluída com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Relação não encontrada
- **500**: Internal Server Error - Erro interno do servidor
