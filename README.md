# brutils

Biblioteca Java **sem dependências** para validar, formatar e gerar documentos brasileiros:
**CPF**, **CNPJ** (inclusive o novo **CNPJ alfanumérico**) e **CEP**.

![CI](https://github.com/MICODEBOT/brutils/actions/workflows/ci.yml/badge.svg)

## Requisitos

- Java 17+
- Maven 3.8+

## Uso

```java
import dev.brutils.*;

// CPF
Cpf.isValid("529.982.247-25");   // true
Cpf.format("52998224725");       // "529.982.247-25"
Cpf.generate();                  // CPF válido aleatório (só dígitos)

// CNPJ (numérico e alfanumérico)
Cnpj.isValid("11.222.333/0001-81");   // true
Cnpj.isValid("12.ABC.345/01DE-35");   // true
Cnpj.format("12abc34501de35");        // "12.ABC.345/01DE-35"
Cnpj.generateAlphanumeric();

// CEP
Cep.isValid("14400-000");  // true
Cep.format("14400000");    // "14400-000"
```

## Desenvolvimento

```bash
mvn test       # roda os testes
mvn package    # gera o jar em target/
```

## Ideias para evoluir

- [ ] Validação de CNH, Título de Eleitor e PIS/NIS
- [ ] Formatação de telefone (fixo e celular)
- [ ] Extenso de valores em reais (`1234,56` → "mil duzentos e trinta e quatro reais...")
- [ ] Publicar no Maven Central / JitPack

## Licença

MIT — veja [LICENSE](LICENSE).
