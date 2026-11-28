# Biblioteca de Jogos

Aplicação desktop simples em Java para gerenciar uma biblioteca de jogos de tabuleiro e cartas.

Os registros são armazenados no arquivo `board_games.csv`.

## Funcionalidades

- Adicionar jogos
- Visualizar o catálogo
- Atualizar jogos
- Remover jogos

## Pré-requisitos

- Java Development Kit (JDK) 8 ou superior

## Como executar

No terminal, dentro da pasta do projeto:

```bash
javac CRUD.java
java CRUD
```

A aplicação abre uma interface gráfica com opções para gerenciar o catálogo.

## Capturas de tela

### Tela principal

![Biblioteca de Jogos](imagens/{27898E5B-67FE-4481-BE1E-46E0DDC87630}.png)

### Catálogo de jogos

![Catálogo de jogos](imagens/{F93324F1-572C-4CC6-963C-227925A9E51F}.png)

### Adicionar jogo

![Adicionar jogo](imagens/{E3D6218B-BA3E-448C-ADF9-C8516587DA72}.png)

### Atualizar jogo

![Selecione o jogo para atualizar](imagens/{EFB274C3-B637-4350-86AC-2A50B8528927}.png)

### Remover jogo

![Selecione o jogo para remover](imagens/{8B34518E-0FD8-4F12-A9A7-17EA3B312879}.png)

## Formato dos dados

Os jogos são armazenados em CSV, com os seguintes campos:

```text
Nome,Categoria,Número de jogadores,Tipo
```

O campo `Tipo` pode ser `Tabuleiro` ou `Cartas`.
