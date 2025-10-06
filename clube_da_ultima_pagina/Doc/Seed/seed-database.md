# Seed do Banco de Dados

## O que é?

Popula o banco com dados de exemplo para desenvolvimento e testes.

## Como usar?

```bash
curl -X POST http://localhost:8080/api/v1/seed
```

## Dados criados

- 2 usuários: Alice (admin) e Bob (membro)
- 2 livros: Dom Casmurro e 1984
- 2 grupos: Clube Machado e Clube Distopia
- 2 encontros (7 e 14 dias no futuro)
- Relacionamentos entre usuários, grupos e livros
- 3 perfis automáticos: ADMINISTRADOR, LIDER_GRUPO, MEMBRO

## Personalizar dados

Edite: `/src/main/java/.../service/SeedService.java`

## Limpar banco

```bash
# Pare a aplicação e delete:
rm -f data/exemplo.mv.db
```

## Verificar dados

H2 Console: `http://localhost:8080/h2-console`

- URL: `jdbc:h2:file:./data/exemplo`
- User: `CLUBEDOLIVRO`
- Password: (vazio)
