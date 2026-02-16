# 🏋️ DevFit

> **Sistema de Gestão para Academias** — Uma API RESTful completa para gerenciamento de unidades de academia, equipamentos, membros, personal trainers, planos de treino e acompanhamento de evolução.

[![Java](https://img.shields.io/badge/Java-25-FF0000?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MariaDB](https://img.shields.io/badge/MariaDB-11-003545?style=for-the-badge&logo=mariadb&logoColor=white)](https://mariadb.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Como Rodar o Projeto](#-como-rodar-o-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Documentação Swagger](#-documentação-swagger)
- [Monitoramento](#-monitoramento)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

---

## 🎯 Sobre o Projeto

O **DevFit** é uma solução backend robusta para gestão completa de academias. Desenvolvido com **Spring Boot 4.0.1** e **Java 25**, o sistema oferece uma arquitetura moderna e escalável, com autenticação JWT, validação de dados, mapeamento DTO/Entity via MapStruct, e monitoramento integrado com Prometheus e Grafana.

O projeto segue as melhores práticas de desenvolvimento, incluindo:
- ✅ **RESTful API** com design consistente
- ✅ **Segurança** com Spring Security + JWT
- ✅ **Validação** de dados de entrada
- ✅ **Tratamento de exceções** centralizado
- ✅ **Documentação** automática com OpenAPI/Swagger
- ✅ **Containerização** completa com Docker

---

## ✨ Funcionalidades

### 🔐 Autenticação e Usuários
- Login com geração de token JWT
- Cadastro de diferentes perfis (Membro, Personal Trainer, Manager, Admin)
- Gerenciamento de perfil e atualização de dados
- Controle de acesso baseado em roles (RBAC)

### 🏢 Gestão de Unidades e Equipamentos
- CRUD de unidades de academia
- Gestão de equipamentos por unidade
- Categorização de equipamentos

### 👥 Gestão de Membros e Personal Trainers
- Cadastro completo de membros com métricas físicas
- Associação de membros a personal trainers
- Histórico de evolução de métricas

### 📋 Planos e Sessões de Treino
- Criação e gerenciamento de planos de treino
- Sessões de treino com exercícios detalhados
- Registro de séries e repetições (Workout Logs)

### 📅 Eventos
- Agendamento de eventos nas unidades
- Gestão de participação em eventos

---

## 🏗️ Arquitetura e Tecnologias

### Stack Principal

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 25 | Linguagem principal |
| **Spring Boot** | 4.0.1 | Framework base |
| **Spring Data JPA** | - | Persistência de dados |
| **Spring Security** | - | Segurança e autenticação |
| **MariaDB** | 11 | Banco de dados relacional |
| **Lombok** | - | Redução de boilerplate |
| **MapStruct** | 1.6.3 | Mapeamento DTO ↔ Entity |
| **SpringDoc OpenAPI** | 3.0.0 | Documentação Swagger |
| **JWT (auth0)** | 4.4.0 | Tokens de autenticação |

### Monitoramento e Observabilidade

| Ferramenta | Finalidade |
|------------|------------|
| **Spring Actuator** | Endpoints de saúde e métricas |
| **Micrometer Prometheus** | Export de métricas |
| **Prometheus** | Coleta e armazenamento de métricas |
| **Grafana** | Dashboards e visualização |

### Containerização

- **Docker** com multi-stage build
- **Docker Compose** para orquestração de serviços
- Usuário não-root no container (segurança)

---

## 📦 Pré-requisitos

Para rodar o projeto, você precisará de:

- [ ] **Java 25+** instalado
- [ ] **Maven 3.8+** (ou use o `mvnw` incluído)
- [ ] **Docker** e **Docker Compose** (recomendado)
- [ ] **Git** para clonar o repositório

---

## 🚀 Como Rodar o Projeto

### Opção 1: Docker Compose (Recomendado)

Esta opção sobe a aplicação, banco de dados, Prometheus e Grafana automaticamente:

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/DevFit.git
cd DevFit

# Copie o arquivo de ambiente e edite as variáveis (opcional)
cp .env.example .env

# Suba todos os serviços
docker-compose up -d
```

**Serviços disponíveis:**

| Serviço | URL | Descrição |
|---------|-----|-----------|
| **API** | `http://localhost:8080` | Aplicação principal |
| **Swagger** | `http://localhost:8080/swagger-ui.html` | Documentação |
| **Prometheus** | `http://localhost:9090` | Métricas |
| **Grafana** | `http://localhost:3000` | Dashboards (admin/admin) |
| **MariaDB** | `localhost:3306` | Banco de dados |

```bash
# Ver logs da aplicação
docker-compose logs -f app

# Parar todos os serviços
docker-compose down
```

### Opção 2: Execução Local (Desenvolvimento)

```bash
# 1. Suba apenas o banco de dados
docker-compose up -d database

# 2. Compile o projeto
./mvnw clean install

# 3. Execute a aplicação
./mvnw spring-boot:run
```

Ou configure as variáveis de ambiente e execute o JAR:

```bash
export DB_USERNAME=app_user
export DB_PASSWORD=app_pass
export JWT_SECRET_KEY=your_secret_key

java -jar target/DevFit-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Endpoints da API

> **Nota:** Todos os endpoints (exceto `/auth/login` e cadastrar membros) requerem token JWT no header:
> ```
> Authorization: Bearer <seu_token>
> ```

### Autenticação
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/auth/login` | Realizar login e obter token JWT |

### Usuários
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/users` | Listar todos os usuários |
| `GET` | `/users/{id}` | Obter usuário por ID |
| `PUT` | `/users/{id}` | Atualizar usuário |
| `DELETE` | `/users/{id}` | Excluir usuário |
| `GET` | `/users/profile` | Obter perfil do usuário autenticado |
| `PUT` | `/users/profile` | Atualizar dados do perfil |

### Cadastros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/register/member` | Cadastrar novo membro |
| `POST` | `/register/personal` | Cadastrar personal trainer |
| `POST` | `/register/manager` | Cadastrar manager |

### Membros
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/members` | Listar todos os membros |
| `GET` | `/members/unit/{unitId}` | Listar membros por unidade |
| `GET` | `/members/{id}` | Obter membro por ID |
| `PATCH` | `/members/update-metrics/{id}` | Atualizar métricas do membro |
| `PATCH` | `/members/goal/{id}` | Atualizar objetivo do membro |

### Personal Trainers
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/personal-trainer` | Listar todos personals |
| `GET` | `/personal-trainer/unit/{unitId}` | Listar personals por unidade |
| `GET` | `/personal-trainer/{id}` | Obter personal por ID |

### Managers
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/managers` | Listar todos managers |
| `GET` | `/managers/{id}` | Obter manager por ID |
| `DELETE` | `/managers/{id}` | Excluir manager |

### Unidades de Academia
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/gym-units` | Listar unidades |
| `POST` | `/gym-units` | Criar nova unidade |
| `GET` | `/gym-units/{id}` | Obter unidade por ID |
| `PUT` | `/gym-units/{id}` | Atualizar unidade |
| `PATCH` | `/gym-units/update-monthly-fee/{id}` | Atualizar mensalidade |
| `DELETE` | `/gym-units/{id}` | Excluir unidade |
| `PUT` | `/gym-units/{destinationUnitId}/members/{memberId}/transfer` | Transferir membro para unidade |
| `PUT` | `/gym-units/{destinationUnitId}/personal-trainers/{trainerId}/transfer` | Transferir personal para unidade |
| `DELETE` | `/gym-units/managers/{id}` | Remover manager de unidade |
| `PATCH` | `/gym-units/{destinationUnitId}/managers/{managerId}` | Atribuir manager à unidade |

### Equipamentos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/gym-equipment` | Listar equipamentos |
| `GET` | `/gym-equipment/category/{category}` | Listar equipamentos por categoria |
| `POST` | `/gym-equipment` | Cadastrar equipamento |
| `GET` | `/gym-equipment/{id}` | Obter equipamento por ID |
| `PUT` | `/gym-equipment/{id}` | Atualizar equipamento |
| `DELETE` | `/gym-equipment/{id}` | Excluir equipamento |

### Planos de Treino
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/training-plan` | Listar planos de treino |
| `GET` | `/training-plan/member/{memberId}` | Listar planos por membro |
| `GET` | `/training-plan/personal/{personalId}` | Listar planos por personal |
| `POST` | `/training-plan` | Criar plano de treino |
| `GET` | `/training-plan/{id}` | Obter plano por ID |
| `PUT` | `/training-plan/{id}` | Atualizar plano |
| `DELETE` | `/training-plan/{id}` | Excluir plano |

### Sessões de Treino
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/training-session/{trainingPlanId}/{personalId}` | Criar sessão de treino |
| `GET` | `/training-session/{id}` | Obter sessão por ID |
| `GET` | `/training-session/training-session/{trainingSessionId}` | Listar sessões por plano |
| `DELETE` | `/training-session/{id}` | Excluir sessão |

### Séries de Exercícios
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/exercise-set` | Criar série de exercício |
| `GET` | `/exercise-set/{id}` | Obter série por ID |
| `GET` | `/exercise-set/training-session/{trainingSessionId}` | Listar séries por sessão |
| `DELETE` | `/exercise-set/{id}` | Excluir série |

### Registro de Treinos (Workout Logs)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/workout-log/{trainingSessionId}` | Registrar treino realizado |
| `GET` | `/workout-log/{id}` | Obter registro por ID |
| `DELETE` | `/workout-log/{id}` | Excluir registro |

### Eventos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/events` | Listar eventos |
| `GET` | `/events/gym/{gymId}` | Listar eventos por academia |
| `POST` | `/events` | Criar evento |
| `GET` | `/events/{id}` | Obter evento por ID |
| `PUT` | `/events/{id}` | Atualizar evento |
| `DELETE` | `/events/{id}` | Excluir evento |
| `POST` | `/events/{id}/join/profile/{profileId}` | Participar do evento |
| `POST` | `/events/{id}/leave/profile/{profileId}` | Sair do evento |

---

## 📚 Documentação Swagger

Acesse a documentação interativa da API em:

```
http://localhost:8080/swagger-ui.html
```

A interface Swagger permite:
- Visualizar todos os endpoints disponíveis
- Testar requisições diretamente pelo navegador
- Ver schemas de request/response
- Autenticar para testar endpoints protegidos

---

## 📊 Monitoramento

### Prometheus

Acesse o dashboard do Prometheus em `http://localhost:9090` para:
- Visualizar métricas da aplicação
- Consultar dados com PromQL
- Monitorar saúde dos endpoints Actuator

### Grafana

Acesse o Grafana em `http://localhost:3000`:
- **Login:** admin / admin
- Configure datasources conectando ao Prometheus
- Crie dashboards personalizados para métricas da API

### Endpoints Actuator

```bash
# Saúde da aplicação
curl http://localhost:8080/actuator/health

# Métricas completas
curl http://localhost:8080/actuator/metrics

# Prometheus scrape endpoint
curl http://localhost:8080/actuator/prometheus
```

---

## 📁 Estrutura do Projeto

```
DevFit/
├── src/
│   ├── main/
│   │   ├── java/dev/borriguel/devfit/
│   │   │   ├── config/           # Configurações (OpenAPI, DB Seeder)
│   │   │   ├── controller/       # Controllers REST
│   │   │   ├── exception/        # Handlers de exceção
│   │   │   ├── mapper/           # MapStruct DTO ↔ Entity
│   │   │   ├── model/
│   │   │   │   ├── dtos/         # Data Transfer Objects
│   │   │   │   └── entities/     # Entidades JPA
│   │   │   ├── repository/       # Repositórios Spring Data
│   │   │   ├── security/
│   │   │   │   ├── SecurityConfigurations.java
│   │   │   │   ├── SecurityFilter.java
│   │   │   │   ├── TokenService.java
│   │   │   │   └── UserDetails*.java
│   │   │   ├── service/          # Regras de negócio
│   │   │   └── DevFitApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Testes unitários e integração
├── docker-compose.yml            # Orquestração de containers
├── Dockerfile                    # Build multi-stage
├── prometheus.yml                # Configuração do Prometheus
├── pom.xml                       # Dependências Maven
└── README.md
```

---

## 🤝 Contribuição

Contribuições são bem-vindas! Para contribuir:

1. Faça um **fork** do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um **Pull Request**

### Padrões de Código

- Siga as convenções do Spring Boot
- Use Lombok para reduzir boilerplate
- Documente endpoints com anotações Swagger
- Mantenha testes para novas funcionalidades

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Desenvolvedor

**DevFit** — Sistema de Gestão para Academias

Desenvolvido com ❤️ por [Rodolpho Henrique](https://github.com/Borriguel)

---

<div align="center">

**Se o projeto te ajudou, considere dar uma ⭐!**

[⬆ Topo](#-devfit)

</div>
