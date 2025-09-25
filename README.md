# Projeto Login Android (Estudo)

![Linguagem](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Plataforma](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Licença](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)

Um aplicativo Android simples, porém robusto, desenvolvido como um projeto de estudo para demonstrar a implementação de um sistema completo de autenticação de usuários, incluindo registro, login, persistência de dados e gerenciamento de sessão.

---

## 📸 Screenshots

| Tela de Login | Tela de Registro | Tela de Perfil |
| :-----------: | :--------------: | :------------: |
| ![Tela de Login](https://github.com/PedroPog/ProjetoLogin/blob/4fb966dc924358d920163610111b69ccd931d48e/documeta%C3%A7%C3%A3o/login.png) | ![Tela de Registro](https://github.com/PedroPog/ProjetoLogin/blob/4fb966dc924358d920163610111b69ccd931d48e/documeta%C3%A7%C3%A3o/register.png) | ![Tela de Perfil](https://github.com/PedroPog/ProjetoLogin/blob/4fb966dc924358d920163610111b69ccd931d48e/documeta%C3%A7%C3%A3o/perfil.png) |

---

## ✨ Features

-   **Cadastro de Usuário**: Permite que novos usuários criem uma conta com nome, e-mail e senha.
-   **Login de Usuário**: Autenticação de usuários com e-mail e senha.
-   **Validação de Formulários**: Verificação de campos de e-mail e senha para garantir que os dados inseridos sejam válidos.
-   **Persistência de Dados Locais**: Utiliza um banco de dados **SQLite** para armazenar as informações dos usuários de forma segura no dispositivo.
-   **Gerenciamento de Sessão**: Usa `SharedPreferences` para manter o usuário logado mesmo após fechar o aplicativo.
-   **Tela de Perfil**: Exibe as informações do usuário logado.
-   **Confirmação de Saída**: Exibe um diálogo de confirmação antes de fechar o aplicativo, melhorando a experiência do usuário.

---

## 🛠️ Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias e bibliotecas:

-   **[Java](https://www.java.com/)**: Linguagem de programação principal.
-   **[Android SDK](https://developer.android.com/)**: Plataforma de desenvolvimento nativo para Android.
-   **[AndroidX Libraries](https://developer.android.com/jetpack/androidx)**: Conjunto de bibliotecas para desenvolvimento moderno de Android.
-   **[Material Components for Android](https://material.io/develop/android)**: Para componentes de UI modernos e consistentes, como `TextInputLayout` e `AlertDialog`.
-   **[SQLite](https://www.sqlite.org/)**: Banco de dados relacional embarcado para armazenamento local de dados.
-   **[Gson](https://github.com/google/gson)**: Biblioteca para serializar e desserializar objetos Java para JSON, utilizada para salvar o perfil do usuário no `SharedPreferences`.

---

## 🚀 Como Executar o Projeto

Para clonar e executar este projeto em sua máquina local, siga os passos abaixo:

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/PedroPog/ProjetoLogin.git](https://github.com/PedroPog/ProjetoLogin.git)
    ```

2.  **Abra no Android Studio:**
    -   Inicie o Android Studio.
    -   Selecione **"Open an existing project"**.
    -   Navegue até o diretório onde você clonou o repositório e selecione-o.

3.  **Sincronize as dependências:**
    -   O Android Studio irá detectar o projeto e o Gradle irá sincronizar as dependências automaticamente.

4.  **Execute o aplicativo:**
    -   Conecte um dispositivo Android ou inicie um Emulador.
    -   Clique no botão **Run** (ícone de play verde) na barra de ferramentas superior.

---

## 📂 Estrutura do Projeto

O código-fonte está organizado nos seguintes pacotes para manter uma arquitetura limpa e de fácil manutenção:

-   `br.codehive.projetologin.database`: Contém as classes para gerenciamento do banco de dados SQLite (`DBHandler`, `UsuarioDatabase`).
-   `br.codehive.projetologin.login`: Contém as `Activities` relacionadas ao fluxo de autenticação (`LoginNoSecurityActivity`, `RegisterNoSecurityActivity`).
-   `br.codehive.projetologin.model`: Contém as classes de modelo de dados (`PerfilModel`).
-   `br.codehive.projetologin.perfil`: Contém a `Activity` da tela principal após o login (`PerfilActivity`).
-   `br.codehive.projetologin.services`: Camada de serviço que lida com a lógica de negócios (`UsuarioService`).
-   `br.codehive.projetologin.shared`: Classes utilitárias, como o gerenciador de `SharedPreferences` (`UtilidadeGerais`).

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE]() para mais detalhes.

*Observação: Você precisará criar um arquivo chamado `LICENSE` no seu repositório e colocar o texto da licença MIT nele.*

---

## 👨‍💻 Autor

Feito por **PedroPog**.

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)]()
[![github](https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PedroPog/)
