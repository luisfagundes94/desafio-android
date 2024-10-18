# PicPay - Desafio Android

<img src="https://github.com/mobilepicpay/desafio-android/blob/master/desafio-picpay.gif" width="300"/>

Um dos desafios de qualquer time de desenvolvimento é lidar com código legado e no PicPay isso não é diferente. Um dos objetivos de trazer os melhores desenvolvedores do Brasil é atacar o problema. Para isso, essa etapa do processo consiste numa proposta de solução para o desafio abaixo e você pode escolher a melhor forma de resolvê-lo, de acordo com sua comodidade e disponibilidade de tempo:
- Resolver o desafio previamente, e explicar sua abordagem no momento da entrevista.
- Discutir as possibilidades de solução durante a entrevista, fazendo um pair programming (bate-papo) interativo com os nossos devs.

Com o passar do tempo identificamos alguns problemas que impedem esse aplicativo de escalar e acarretam problemas de experiência do usuário. A partir disso elaboramos a seguinte lista de requisitos que devem ser cumpridos ao melhorar nossa arquitetura:

- Em mudanças de configuração o aplicativo perde o estado da tela. Gostaríamos que o mesmo fosse mantido.
- Nossos relatórios de crash têm mostrado alguns crashes relacionados a campos que não deveriam ser nulos sendo nulos e gerenciamento de lifecycle. Gostaríamos que fossem corrigidos.
- Gostaríamos de cachear os dados retornados pelo servidor.
- Haverá mudanças na lógica de negócios e gostaríamos que a arquitetura reaja bem a isso.
- Haverá mudanças na lógica de apresentação. Gostaríamos que a arquitetura reaja bem a isso.
- Com um grande número de desenvolvedores e uma quantidade grande de mudanças ocorrendo testes automatizados são essenciais.
  - Gostaríamos de ter testes unitários testando nossa lógica de apresentação, negócios e dados independentemente, visto que tanto a escrita quanto execução dos mesmos são rápidas.
  - Por outro lado, testes unitários rodam em um ambiente de execução diferenciado e são menos fiéis ao dia-a-dia de nossos usuários, então testes instrumentados também são importantes.

## Arquitetura
Uma arquitetura bem planejada é crucial para a escalabilidade de um aplicativo e para o gerenciamento de sua complexidade. Embora aplicativos menores talvez não precisem disso, é muito útil para projetos maiores, com tempos de desenvolvimento mais longos e equipes maiores.

Robert C. Martin introduziu o conceito de arquitetura limpa em 2012 por meio do Clean Code Blog, e ele segue o princípio SOLID.

<img src="https://miro.medium.com/v2/resize:fit:772/1*wOmAHDN_zKZJns9YDjtrMw.jpeg" width="500" />

Esse projeto segue o padrão de arquitetura [_**MVVM (Model-View-ViewModel)**_]([https://proandroiddev.com/mvi-architecture-with-kotlin-flows-and-channels-d36820b2028d](https://developer.android.com/topic/architecture)).
 
<img src="https://github.com/MuhammadKhoshnaw/BasicMVIApp/blob/master/.github/res/ComponentDiagram.svg" width=500 />

## Modularização

A modularização foi feita por camada. Cada camada tem seus próprios módulos (view, viewModel, domain, repository, data source etc.). Segue abaixo alguns benefícios da modularização em um projeto de grande escala em android

#### 1. Escalabilidade e Isolamento de Funcionalidades

Modularizar permite dividir o aplicativo em diferentes módulos, como features ou camadas arquiteturais (view, domain, data). Com isso, as equipes podem trabalhar de forma independente em diferentes módulos sem afetar outras partes do sistema. Cada módulo tem um escopo limitado e claro, o que facilita a adição de novas funcionalidades ou a modificação de partes específicas do app.

Exemplo: Se uma equipe está trabalhando no módulo de autenticação e outra no módulo de pagamento, ambas podem evoluir suas partes de forma independente, sem gerar conflitos.

#### 2. Maior Controle sobre Dependências

Com a modularização, você pode definir dependências claras entre os módulos, evitando acoplamento excessivo. Isso também permite impor restrições de acesso entre módulos, garantindo que um módulo de UI (por exemplo) não tenha acesso direto a um módulo de banco de dados, respeitando a arquitetura proposta.

Exemplo: A camada de view só pode se comunicar com a ViewModel, que por sua vez interage com a camada de domain e assim por diante. Isso garante que regras de arquitetura sejam seguidas.

#### 3. Compilação mais Rápida

Projetos Android grandes podem demorar muito para compilar. A modularização pode reduzir o tempo de compilação, pois apenas os módulos alterados precisam ser recompilados. Isso otimiza o ciclo de desenvolvimento, permitindo que os desenvolvedores sejam mais produtivos, já que não precisam esperar longos períodos para testar suas mudanças.

Exemplo: Se você faz uma alteração em um módulo de feature específico, como o módulo de login, você só recompila esse módulo em vez de recompilar o projeto inteiro.

## Injeção de Dependência

A injeção de dependência é usada em um projeto para promover a flexibilidade, facilitar o teste e melhorar a manutenção do código. Ela permite que as dependências (objetos que uma classe precisa para funcionar) sejam fornecidas externamente em vez de serem criadas internamente pela própria classe.

O framework utilizado para a injeção de depedência no projeto foi o Hilt. Escolhi essa tecnologia ao invés do Koin por alguns motivos:

#### 1. Verificação em Tempo de Compilação

Hilt realiza verificação em tempo de compilação, o que permite detectar erros de configuração de dependência antes mesmo de rodar a aplicação. Isso inclui erros como dependências não resolvidas, ciclos de dependência, etc.

#### 2. Compatibilidade com WorkManager, ViewModel e Jetpack

Hilt tem integração direta com vários componentes Jetpack, como o WorkManager e ViewModel. Ao usar Hilt, você pode injetar dependências diretamente no construtor de um ViewModel ou de um Worker, por exemplo.

#### 3. Escopos e Ciclo de Vida Automatizado

Hilt lida automaticamente com escopos e o ciclo de vida dos componentes do Android. Ele cria escopos para Activity, Fragment, ViewModel, e outros componentes com facilidade.

## Testes

Testes unitários e instrumentados em um projeto grande é crucial para garantir qualidade, manutenção e escalabilidade do código.

O framework utilizado para mock de objetos e compartamentos foi o MockK. Escolhi essa tecnologia ao invés do Mockito, pois:

#### 1. Suporte Nativo a Kotlin

MockK foi projetado para trabalhar diretamente com as características da linguagem Kotlin, enquanto Mockito, sendo uma biblioteca para Java, tem algumas limitações quando adaptado para Kotlin.

#### 2. Mock de Classes e Funções Final

No Kotlin, por padrão, todas as classes e funções são final, ou seja, não podem ser estendidas ou sobrescritas, o que pode causar dificuldades com Mockito, pois ele não consegue fazer mocking de classes final sem uma configuração extra.

## Integração Contínua

(CI) é essencial para automatizar, agilizar e garantir a qualidade do processo de desenvolvimento de software.

Foi utilizado o github actions para garantir que toda vez em que um push é feito no repositório do projeto, algumas etapas são acionadas para garantiar a confiabilidade e consistência da aplicação:

- Verificar todos os testes unitários (Unit Testing)
- Verificar a padronização e formatação de código em Kotlin (Ktlint)
- Analisar o código do projeto em busca de potenciais erros ou problemas relacionados à qualidade do código, desempenho, segurança e compatibilidade. (Android Lint)
