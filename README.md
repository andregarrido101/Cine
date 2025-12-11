# Cine

Cine é um projeto de portfólio desenvolvido para funcionar como um sistema de gestão para cinemas, oferecendo controle organizado de sessões, salas, ingressos...

## Tecnologias

- **Java:** 21
- **Spring Boot:** Spring Boot
- **Banco de Dados:** PostgreSQL via Docker
- **Segurança:** JWT (JSON Web Token)
- **Testes:** Unitários
- **Documentação de API:** Swagger/OpenAPI (Spring Doc)

## Endpoint

### 1. Autenticação

#### Base URL: `/api/v1/auth`

#### Operações disponíveis:

- **POST** `/api/v1/auth/login` - Realizar login e obter token JWT

### 2. Usuários

#### Base URL: `/api/v1/users`

#### Operações disponíveis:

- **POST** `/api/v1/users` - Cadastrar novo usuário (Rota pública - Não requer autenticação)

### 3. Sessões

#### Base URL: `/api/v1/sessions`

#### Operações disponíveis:

- **GET** `/api/v1/sessions` - Obter todas as sessões para um filme específico
- **POST** `/api/v1/sessions` - Criar nova sessão (Apenas o Administrador do sistema pode operar)

### 4. Filmes

#### Base URL: `/api/v1/movies`

#### Operações disponíveis:

- **GET** `/api/v1/movies` - Obter todos os filmes que possuem uma sessão ativa

### 5. Tickets

#### Base URL: `/api/v1/tickets`

#### Operações disponíveis:

- **POST** `/api/v1/tickets` - Comprar um ingresso para uma sessão

## Entidades


### User

- username (String)
- email (String)
- password (String)

### Filme (Movie)

- movieTitle (String)
- synopsis (String)
- releaseYear (int)
- genre (String)
- director (String)
- durationMinutes (int)
- activeSession (Enum: UNAVAILABLE_MOVIE, AVAILABLE_MOVIE)

### Sala (Room)

- roomNumber (Integer)
- capacity (Integer)
- isAvailable (Enum: AVAILABLE_ROOM, OCCUPIED_ROOM)

### Assento (Seat)

- seatCode (String)
- session (SessionEntity)
- ticket (TicketEntity)

### Sessão (Session)

- movie (MovieEntity)
- room (RoomEntity)
- pricePerSeat (Double)
- sessionTime (String)
- availableSeats (int)

### Ingresso (Ticket)

- user (UserEntity)
- session (SessionEntity)
- seatCode (String)

### Relações

- Movie 1:N Session (Um filme pode estar em várias sessões)
- Room 1:N Session (Uma sala pode estar em várias sessões)
- User 1:N Ticket (Um usuário pode comprar vários tickets)
- Session 1:N Ticket (Uma sessão pode ter vários tickets comprados)
- Session 1:N Seat (Uma sessão pode ter vários assentos)
- Ticket 1:N Seat (Um ticket pode ter um assento)

## Autenticação

A maioria dos endpoints requer autenticação via JWT. Para acessar rotas protegidas:

1. Faça login em `/api/v1/auth/login`
2. Copie o token retornado no campo `token`
3. Adicione o token no header `Authorization` como: `Bearer {seu-token-aqui}`

**Rotas públicas** (não requerem token):
- `POST /api/v1/auth/login`
- `POST /api/v1/users`

## Documentação Swagger

A API possui documentação interativa completa via **Swagger UI**, acessível em:

```
http://localhost:8081/swagger-ui/index.html
```

### Recursos do Swagger:
- **Documentação completa** de todos os endpoints
- **Testes interativos** - Execute requisições diretamente pelo navegador
- **Autenticação integrada** - Configure o token JWT uma vez e use em todas as requisições
- **Schemas e exemplos** - Veja a estrutura de cada request/response
- **Validações** - Documentação dos códigos de resposta HTTP

### Como usar autenticação no Swagger:
1. Acesse a rota de login em `/api/v1/auth/login`
2. Execute a requisição e copie o token retornado
3. Clique no botão **"Authorize"** no topo da página
4. Cole o token no campo (formato: `Bearer {seu-token}` ou apenas o token)
5. Clique em "Authorize" e depois "Close"
6. Agora você pode testar todas as rotas protegidas!

## Postman

Arquivo de collection disponível em:
```
/src/main/resources/postman/Cine  - Movie Reservation.postman_collection.json
```

Importe este arquivo no Postman para ter acesso a todas as requisições pré-configuradas.

## Execução via Docker

O projeto inclui um `docker compose.yml` que provisiona:
- **PostgreSQL** (postgres_db) - Banco de dados
- **Aplicação Spring Boot** (cine_app) - API

### Passos

1. **Build e subida dos containers:**

```bash
docker compose up -d --build
```

2. **A aplicação estará acessível em:**

```
http://localhost:8080
```

3. **Para parar os containers:**

```bash
docker-compose down
```

4. **Para parar e remover volumes (limpar banco de dados):**

```bash
docker-compose down -v
```

## Executando Localmente (sem Docker)

### Pré-requisitos:
- Java 21
- Maven 3.9+
- PostgreSQL instalado

### Configuração:

1. Configure o banco de dados PostgreSQL
2. Ajuste as credenciais em `/src/main/resources/application.yml`
3. Execute:

```bash
cd app
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Observações

- ✅ Segue a **normalização do banco de dados**
- ✅ API **totalmente documentada** com Swagger/OpenAPI
- ✅ Segue padrões **RESTful**
- ✅ **Autenticação JWT** implementada
- ✅ **Validações** completas nos DTOs
- ✅ **Testes unitários e de integração** implementados
- ✅ **Tratamento de exceções** centralizado
- ✅ **Criptografia de senhas** com BCrypt

## Fluxo de Uso

1. **Cadastrar usuários** (2 ou mais) via `POST /api/v1/users`
2. **Fazer login** com um usuário via `POST /api/v1/auth/login`
3. **Criar sessões** via `/api/v1/sessions`
4. **Obter sessões para um filme** via `/api/v1/sessions/1`
5. **Obter todos os filmes** via `/api/v1/movies`
6. **Comprar ingressos** via `/api/v1/tickets`

---

**Desenvolvido por**: Andre Garrido