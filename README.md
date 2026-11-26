# brutils

[![CI](https://github.com/MIcodebot/brutils/actions/workflows/ci.yml/badge.svg)](https://github.com/MIcodebot/brutils/actions/workflows/ci.yml)
![Java 17+](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)
![Licença MIT](https://img.shields.io/badge/licen%C3%A7a-MIT-blue)

Biblioteca Java **sem dependências** para validar, formatar e gerar documentos brasileiros:
**CPF**, **CNPJ** (inclusive o novo **CNPJ alfanumérico**) e **CEP**.

## Requisitos

- Java 17+
- Maven 3.8+

## Uso

```java
import dev.brutils.*;

// CPF
Cpf.isValid("529.982.247-25");   // true
Cpf.format("52998224725");       // "529.982.247-25"
Cpf.unformat("529.982.247-25");  // "52998224725"
Cpf.generate();                  // CPF válido aleatório (só dígitos)

// CNPJ (numérico e alfanumérico)
Cnpj.isValid("11.222.333/0001-81");   // true
Cnpj.isValid("12.ABC.345/01DE-35");   // true
Cnpj.format("12abc34501de35");        // "12.ABC.345/01DE-35"
Cnpj.unformat("12.abc.345/01de-35");  // "12ABC34501DE35"
Cnpj.generate();                      // CNPJ numérico válido (filial 0001)
Cnpj.generateAlphanumeric();          // CNPJ alfanumérico válido

// CEP
Cep.isValid("14400-000");  // true
Cep.format("14400000");    // "14400-000"
```

`format` lança `IllegalArgumentException` quando a entrada não tem o tamanho certo; `isValid` nunca lança exceção (inclusive com `null`).

## Como funciona o CNPJ alfanumérico

Desde 31 de julho de 2026 a Receita Federal emite CNPJs em que as **12 primeiras posições** podem ter letras e números; os **2 dígitos verificadores continuam numéricos**.

O cálculo do dígito verificador continua sendo o módulo 11, mas cada caractere passa a valer o seu **código ASCII menos 48**:

| Caractere | Valor |
|---|---|
| `0`–`9` | 0–9 |
| `A` | 17 |
| `B` | 18 |
| … | … |
| `Z` | 42 |

Como `'0'..'9'` menos 48 dá exatamente 0..9, a mesma implementação valida os CNPJs antigos (só números) e os novos, sem nenhum caso especial. Veja `Cnpj.checkDigits`.

## Desenvolvimento

```bash
mvn test       # roda os testes
mvn package    # gera o jar em target/
```

Os testes (JUnit 5) rodam no GitHub Actions a cada push, em Java 17 e 21.

## Ideias para evoluir

- [ ] Validação de CNH, Título de Eleitor e PIS/NIS
- [ ] Formatação de telefone (fixo e celular)
- [ ] Extenso de valores em reais (`1234,56` → "mil duzentos e trinta e quatro reais...")
- [ ] Publicar no Maven Central / JitPack

## Licença

MIT — veja [LICENSE](LICENSE).
