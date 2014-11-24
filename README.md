#Play - Testes

######Playframework version 2.2.3 
---
[ ![Codeship Status for ClaudivanFilho/PlayTestes](https://www.codeship.io/projects/62221cd0-f8c3-0131-6ba1-42ecadc609ef/status)](https://www.codeship.io/projects/28753)

>Sua aplicação Play pode ser testada em três níveis básicos:

  - UNIDADE
  - FUNCIONAL
  - INTERFACE / BROWSER
  
>Utilização

* todas as suas classes de teste devem estar na pasta 'test' gerada pelo play

* na raiz da sua aplicação play digite o comando
```sh
$>play test
//ou então para executar determinado teste.
$>play "test-only <sua classe de testes ou seu namespace>"
```
```sh
// rodar a classe de testes IndexViewTest do pacote funcional
$>play "test-only funcional.IndexViewTest"
// rodar todas as classes de teste do pacote funcional 
$>play "test-only funcional.*"
``` 

## Testes de Unidade

> A Maneira padrão de testar sua apicação, com a ajuda do [JUnit]

> Dicas
* Conter um 'extends AbstractTest' para testar com banco de dados
* [AbstractTest.java]

Exemplo:
```sh
// Sua unidade a ser testada, geralmente algum Model 
public class UnidadeTest extends AbstractTest{
    import org.junit.*;
    import play.mvc.*;
    import play.test.*;
    import play.libs.F.*;
    import static play.test.Helpers.*;
    import static org.fest.assertions.Assertions.*;
    
    // é também uma boa prática guiar seus testes pelo comportamento 
    // da entidade
    @Test 
    public void comportamentoDaUnidadeTest() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
        // usando asserts para verificação
    }
    ...
}
```
-[exemplo completo][4]
##Testes Funcionais

> Testa o comportamento externo do software

> Dicas
* Conter um 'extends AbstractTest' para testar com BD e outras transações
* [AbstractTest.java]

Nesse tipo de teste são testados(as):
* Template (View)

```sh
@Test
public void indexTemplate() {
	String title = "Your new application is ready.";
	// guarda o resultado da renderização do index.scala.html 
	// passado como parametro 'title'
    Content html = index.render(title);
    //verifica se o html contém a determimnada string
    assertThat(contentAsString(html)).contains(title);
}
``` 
 
-[exemplo completo][1]
* Controllers

```sh
@Test
public void callBooks() {
    // guarda o resultado da chamada ao método books() do controller Application
    Result result = callAction(controllers.routes.ref.Application.books(),
		fakeRequest());
    assertThat(status(result)).isEqualTo(Http.Status.OK);
    assertThat(charset(result)).isEqualTo("utf-8");
    assertThat(contentAsString(result)).contains("0 livro(s)");
}
```

-[exemplo completo][2]
* Routes

o arquivo de routes pode ser visto [aqui][routes]
```sh
// testa a route inicial, no caso "/"
@Test
public void rootRoute() {
    // guarda o resultado da requisição à url "/"
    Result result = Helpers.route(new FakeRequest(GET, "/"));
    // testa se a resultado da requisição à url "/" não é nula
    assertThat(result).isNotNull();
}
```
-[exemplo completo][3]
* Documentação do play sobre testes funcionais [aqui][TestesFuncionais]

##Testes de Interface
>Utiliza de testes automaticos, uma das principais ferramentas para esse tipo de teste é o [selenium]

## Duvidas 

Utilizem o Piazza!

[AbstractTest.java]:https://github.com/ClaudivanFilho/PlayTestes/blob/master/test/base/AbstractTest.java
[routes]:https://github.com/ClaudivanFilho/PlayTestes/blob/master/conf/routes
[selenium]:http://docs.seleniumhq.org/
[1]:https://github.com/ClaudivanFilho/PlayTestes/blob/master/test/funcional/IndexViewTest.java
[2]:https://github.com/ClaudivanFilho/PlayTestes/blob/master/test/funcional/ApplicationControllerTest.java
[3]:https://github.com/ClaudivanFilho/PlayTestes/blob/master/test/funcional/IndexRouteTest.java
[4]:https://github.com/ClaudivanFilho/PlayTestes/blob/master/test/unidade/LivroTest.java
[JUnit]:http://www.junit.org/
[TestesFuncionais]:http://www.playframework.com/documentation/2.2.x/JavaFunctionalTest
