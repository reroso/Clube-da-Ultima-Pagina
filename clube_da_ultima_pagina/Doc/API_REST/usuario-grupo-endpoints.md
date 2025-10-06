# UsuarioGrupoRestController - Endpoints

## Base URL
```
http://localhost:8080/api/v1/usuario-grupos
```

---

## Descrição
Este controller gerencia a relação entre usuários e grupos, permitindo adicionar ou remover membros dos grupos de leitura.

---

## Endpoints Disponíveis

### GET - Listar todas as relações usuário-grupo
```http
GET /api/v1/usuario-grupos
```
*Não requer corpo da requisição*

### GET - Buscar relação por ID
```http
GET /api/v1/usuario-grupos/1
```
*Não requer corpo da requisição*

### POST - Criar nova relação usuário-grupo
```http
POST /api/v1/usuario-grupos
Content-Type: application/json

{
    "usuario": {
        "id": 1
    },
    "grupo": {
        "id": 2
    }
}
```

### PUT - Atualizar relação usuário-grupo
```http
PUT /api/v1/usuario-grupos/1
Content-Type: application/json

{
    "usuario": {
        "id": 2
    },
    "grupo": {
        "id": 1
    }
}
```

### DELETE - Excluir relação usuário-grupo
```http
DELETE /api/v1/usuario-grupos/1
```
*Não requer corpo da requisição*

---

## Exemplo com cURL

```bash
curl -X POST http://localhost:8080/api/v1/usuario-grupos \
  -H "Content-Type: application/json" \
  -d '{
    "usuario": {
        "id": 3
    },
    "grupo": {
        "id": 1
    }
  }'
```

---

## Validações Importantes

- **Usuário**: 
  - ⚠️ **OBRIGATÓRIO** - Não pode ser null
  - Deve referenciar um usuário existente através do ID
- **Grupo**: 
  - ⚠️ **OBRIGATÓRIO** - Não pode ser null  
  - Deve referenciar um grupo existente através do ID
- **🚨 REGRA DE UNICIDADE**: A combinação usuário-grupo deve ser única
  - Um usuário não pode estar associado ao mesmo grupo mais de uma vez
  - Na atualização, não pode criar duplicata com associação existente
- **Perfil**: Opcional, se não informado será definido como "Membro" automaticamente

## ❌ Principais Causas de Erro 400/404

### 1. **Associação Duplicada (Mais Comum)**
```json
// Cenário: Já existe Usuário 2 ↔ Grupo 1 no ID 5
// ❌ ERRO ao tentar atualizar ID 3 para:
{
  "usuario": {"id": 2},
  "grupo": {"id": 1}  // Conflito!
}

// ✅ CORRETO - Associação que não duplica:
{
  "usuario": {"id": 2},
  "grupo": {"id": 2}  // OK se não existir
}
```

### 2. **IDs Inválidos ou Inexistentes**
```json
// ❌ ERRO - Usuário não existe
{
  "usuario": {"id": 999},
  "grupo": {"id": 1}
}

// ❌ ERRO - Grupo não existe  
{
  "usuario": {"id": 1},
  "grupo": {"id": 999}
}
```

### 3. **Campos Obrigatórios Ausentes**
```json
// ❌ ERRO - Usuário faltando
{
  "grupo": {"id": 1}
}

// ❌ ERRO - Grupo faltando
{
  "usuario": {"id": 1}
}
```

---

## 🔧 Troubleshooting - Erro 400/404

### Passo a passo para debugar:

1. **Listar associações existentes:**
   ```bash
   curl -X GET http://localhost:8080/api/v1/usuario-grupos
   ```

2. **Verificar se o usuário existe:**
   ```bash
   curl -X GET http://localhost:8080/api/v1/usuarios/2
   ```

3. **Verificar se o grupo existe:**
   ```bash
   curl -X GET http://localhost:8080/api/v1/grupos/2
   ```

4. **Testar com combinação não duplicada:**
   ```bash
   curl -X PUT http://localhost:8080/api/v1/usuario-grupos/3 \
     -H "Content-Type: application/json" \
     -d '{
       "usuario": {"id": 1},
       "grupo": {"id": 3}
     }'
   ```

### 💡 Dicas para Atualização
- **Sempre verifique** as associações existentes antes de atualizar
- **Um usuário pode participar de vários grupos** (mas não do mesmo grupo duas vezes)
- **Use combinações únicas** de usuário-grupo
- **404 vs 400**: 404 geralmente indica conflito de duplicata, 400 indica dados inválidos

### 🔍 Como encontrar combinações válidas para atualização:
1. Liste usuários: `GET /api/v1/usuarios`
2. Liste grupos: `GET /api/v1/grupos`
3. Liste associações existentes: `GET /api/v1/usuario-grupos`
4. Escolha combinação que não existe ainda

---

## Códigos de Status HTTP

- **200**: OK - Operação bem-sucedida (GET, PUT)
- **201**: Created - Relação criada com sucesso (POST)
- **204**: No Content - Relação excluída com sucesso (DELETE)
- **400**: Bad Request - Dados inválidos
- **404**: Not Found - Relação não encontrada
- **500**: Internal Server Error - Erro interno do servidor
