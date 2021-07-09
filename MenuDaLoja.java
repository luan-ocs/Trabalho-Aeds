// Autor: Luan Otávio
// ultima mudança 7 de julho de 2021
// objetivo do programa: Criar um gerenciador de estoque/vendas/lucro.
// e gerar relatórios Diários.

package menu;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Classe principal do Programa
public class MenuDaLoja {

  // Parte principal do Programa
  public static void main(String[] args) {
    final Scanner input = new Scanner(System.in);
    Product[] products;
    System.out.println("por favor, digite o nome da sua loja: ");
    final String storeName = input.nextLine();
    System.out.println("Como deseja registrar os produtos?");
    System.out.println("C- Console");
    System.out.println("F- Arquivo");
    final String choice = input.nextLine();

    //  Maneiras diferentes de inicializar o Programa, Atravez do console,
    //  ou Atravez de um arquivo.
    if (choice.equalsIgnoreCase("C")) {

      //  Metodo que inicializa pelo console,
      //  retorna um Vetor com os produtos cadastrados
      products = inicializeByConsole(input);
    } else {

      //  metodo que inicializa pelo arquivo,
      //  tambem retorna um Vetor com os produtos cadastrados
      products = inicializeByFile(input);
    }

    // Metodo que pega os valores de cada produto do array,
    // e transforma em uma Matriz de Strings
    String[][] storeMatrix = fillMatrix(products);
    int productsqnty = products.length;

    // Metodo que printa a Matriz na tela
    printMatrix(storeMatrix, productsqnty, storeName);

    // Metodo que printa o menu na tela
    menu(input, products, storeName);
    input.close();
  }


  //    Esse e o metodo que le um arquivo e transforma-os em
  //    Produtos e adiciona-os em um array
  private static Product[] inicializeByFile(Scanner input) {
    System.out.println("por favor digite o caminho completo do arquivo que deseja importar: ");
    Product[] productsArray;
    String path = input.nextLine();
    File fileproducts = new File(path);
    try {
      Product[] products = new Product[0];
      String productString;
      Scanner fileReader = new Scanner(fileproducts);
      while (fileReader.hasNext()) {
        productString = fileReader.nextLine();
        if (!productString.equalsIgnoreCase("")) {
          String[] values = productString.split(",");
          Product newProduct = new Product(values[0], Double.parseDouble(values[1]),
                  Double.parseDouble(values[2]));
          // metodo que cria um Vetor dinamico que
          // adciona um novo produto ao vetor Produtos
          products = addElement(products, newProduct);
        }
      }

      // retorna o vetor com todos os Produtos
      return products;
    } catch (FileNotFoundException e) {
      // Tratamento de erro caso o arquivo não seja encontrado
      System.err.println("Erro- Arquivo não encontrado.");
      System.out.print("1- tentar novamente ");
      System.out.println("2- Inicializar pelo console");
      String choice;
      choice = input.nextLine();
      switch (choice) {
        case "1":
          //  Aciona o metodo iniciar por arquivo novamente
          productsArray = inicializeByFile(input);
          break;
        case "2":
          // Inicia o metodo pelo console;
          productsArray = inicializeByConsole(input);
          break;

        default:
          // caso o usuario digite uma opção que não exista,
          // programa iniciará automaticamente atravez do console
          System.out.println("escolha Invalida!");
          productsArray = inicializeByConsole(input);
          break;
      }
      return productsArray;
    }
  }

  //    Metodo que retorna um Vetor igual ao vetor declarado, com mais um elemento;
  public static Product[] addElement(Product[] products, Product prod) {

    //cria um vetor com 1 elemento a mais que o vetor passado por parametro
    Product[] array = new Product[products.length + 1];

    if (products.length > 0) {

      //repetição que percorre o vetor e copia o array passado como parametro
      for (int i = 0; i <= products.length - 1; i++) {
        array[i] = products[i];
      }
    }
    //adiciona o elemento mais novo ao Vetor que foi declarado dentro da função.
    array[products.length] = prod;
    return  array;
  }

  //Esse método Inicia a parte de cadastro de produtos através de um console,
  // ele retorna um vetor.
  private static Product[] inicializeByConsole(Scanner input) {

    int typeOfProduct;
    System.out.println("por favor, digite a quantidade de tipos de produtos diferentes "
            + "que serao vendidos na loja: ");
    typeOfProduct = input.nextInt();
    Product[] products = new Product[typeOfProduct];

    //percorre o vetor com a quantidade de elementos que o usuário digitou e
    // a elemento chama o metodo que cria produtos
    for (int i = 0; i < typeOfProduct; i++) {
      products[i] = createProductByConsole(input);
    }
    //retorna o vetor com todos os produtos preenchidos
    return products;
  }

  //metodo que pega as caracteristicas do produto e cria um produto com essas caracteristicas.
  private static Product createProductByConsole(Scanner input) {
    System.out.print("por favor digite o nome do produto: ");
    String name = input.nextLine();
    System.out.print("por favor digite o preço de produção do produto: R$");
    double cost = input.nextDouble();
    System.out.print("Por favor digite o preco de venda do produto: R$");
    double price = input.nextDouble();
    return new Product(name, price, cost);
  }

  //  Esse metodo cria uma matriz com os valores dos produtos que estao no vetor,
  //  ele retorna a matriz preenchida com os valores
  private static String[][] fillMatrix(Product[] products) {
    int rows = products.length;
    int cols = 7;
    String[][] storeMatrix = new String[rows][cols];
    //esse metodo percorre as linhas da matriz, que tem o mesmo tamanho vetor de produtos
    for (int i = 0; i < rows; i++) {
      storeMatrix[i][0] = products[i].getName();
      storeMatrix[i][1] = String.valueOf(products[i].getProductStorage());
      storeMatrix[i][2] = String.valueOf(products[i].getCostOfProduction());
      storeMatrix[i][3] = String.valueOf(products[i].getDayProductSell());
      storeMatrix[i][4] = String.valueOf(products[i].getPrice());
      storeMatrix[i][5] = String.valueOf(products[i].getDayAllSalesofthatProduct());
      storeMatrix[i][6] = String.valueOf(products[i].getProfit());
    }
    return storeMatrix;

  }

  // esse metodo Printa a matriz na tela
  private static void printMatrix(String[][] products, Integer productQnty,
                                  String storeName) {
    int biggestName = 5;
    //  esse for percorre os nomes dos produtos na matriz e
    //  verifica se algum dele é maior que 5.
    //  se for, entao o maior nome se torna o tamanho desse nome;
    for (int i = 0; i < productQnty; i++) {
      if ((products[i][0].length()) > biggestName) {
        biggestName = products[i][0].length();
      }
    }
    //  printa o nome da Loja, antes de printar a matriz,
    //  tambem da um espacamento de (maiorNome + 45 espacos)
    System.out.println(spacing(biggestName + 45, "Seja Bem vindo à " + storeName) + "\n");

    //  printa os nomes da coluna da matriz, cada um com um espaçamento dinamico
    //  o espacamento que varia e somente o primeiro que pega o produto com maior nome e
    //  adiciona um espacamento de 5

    System.out.println(spacing(biggestName + 5, "Estoque")
            + spacing(5, "Custo de Produção")
            + spacing(5, "Itens vendidos")
            + spacing(5, "Preço")
            + spacing(5, "Vendas")
            + spacing(5, "Lucro"));

    //  esse for printa cada produto,
    //  chamando os metodos get de cada produto e printando na tela;
    //  os espacamentos dos nomes irão variar de acordo com a diferença entre
    //  tamanho do proprio nome em relação ao tamanho do maior nome mais 5 de espacamento.
    //  os proximos elementos, seus espacamentos irão variar de acordo com a diferença entre
    //  o nome da coluna + espacamento(fixo para cada coluna) e o seu proprio tamanho.
    //
    for (int i = 0; i < productQnty; i++) {
      System.out.print(spacing(biggestName - products[i][0].length(),
              products[i][0]) + spacing(5, ""));
      System.out.print(products[i][1] + spacing(12 - products[i][1].length(),
              ""));
      System.out.print(products[i][2] + spacing(22 - products[i][2].length(),
              ""));
      System.out.print(products[i][3] + spacing(19 - products[i][3].length(),
              ""));
      System.out.print(products[i][4] + spacing(10 - products[i][4].length(),
              ""));
      System.out.print(products[i][5] + spacing(11 - products[i][5].length(),
              ""));
      System.out.print(products[i][6]);
      System.out.println();
    }
  }

  //    metodo que printa as escolhas que o usuário pode fazer durante o programa
  //    a cada escolha feita durante o menu, é printado a mudança e,
  //    novamente o menu é mostrado ao usuário
  //    até o usuario decidir sair do programa.
  //    todos os metodos chamados no switch buscam o produto pelo nome,
  //    e então mudam seus atributos.

  private static void menu(Scanner input, Product[] products, String st) {
    int day = 0;
    String choice = "";
    String[][] matrix;
    while (!choice.equals("6")) {
      System.out.println("Seus Produtos foram cadastrados, O que deseja fazer agora? ");
      System.out.println("1- Mudar o estoque de um dos Produtos");
      System.out.println("2- Efetuar a venda de Um dos Produtos");
      System.out.println("3- Mudar o preco de algum produto");
      System.out.println("4- Mudar o custo de produção de algum produto");
      System.out.println("5- Finalizar o Dia");
      System.out.println("6- Terminar o Programa");
      choice = input.next();
      switch (choice) {
        case "1":
          setStorage(input, products);
          matrix = fillMatrix(products);
          printMatrix(matrix, products.length, st);
          break;
        case "2":
          setSales(input, products);
          matrix = fillMatrix(products);
          printMatrix(matrix, products.length, st);
          break;
        case "3":
          setPrice(input, products);
          matrix = fillMatrix(products);
          printMatrix(matrix, products.length, st);
          break;
        case "4":
          setCost(input, products);
          matrix = fillMatrix(products);
          printMatrix(matrix, products.length, st);
          break;
        case "5":
          day = setEndDay(products, input,day);
          matrix = fillMatrix(products);
          printMatrix(matrix, products.length, st);
          break;
        case "6":
          break;
        default:
          System.err.println("Erro- escolha inválida");
      }
    }
  }

  private static int setEndDay(Product[] products, Scanner input, int day) {
    reportGenerator(products, input, day);
    for (Product product : products) {
      product.endDay();
    }
    return ++day;
  }

  private static void reportGenerator(Product[] products, Scanner input, int day) {
    System.out.print("digite o caminho onde deseja salvar o relatorio: ");
    input.nextLine();
    String path = input.nextLine();
    File saveFile = new File(path + "dia" + day + ".csv");
    try {
      FileWriter writeSave = new FileWriter(saveFile);
      writeSave.write("Nome,Estoque,Custo de Produção,Itens vendidos,Preço,Vendas,Lucro\n");
      for (Product product : products) {
        writeSave.write(product.getName() + ","
                + product.getProductStorage() + ","
                + product.getCostOfProduction() + ","
                + product.getDayProductSell() + ","
                + product.getPrice() + ","
                + product.getDayAllSalesofthatProduct() + ","
                + product.getProfit() + "\n");
      }
      writeSave.close();
      System.out.println("o arquivo foi salvo com sucesso!\n");
    } catch (IOException e) {
        System.err.println(e.getMessage());
    }
  }
  //metodo do menu que muda o custo de um produto especificado pelo nome.
  //utiliza um for que percorre o vetor de produtos.
  //O metodo somente aceita valores maiores que 0
  //caso o usuario digite alguma letra há um tratamento de erro.

  private static void setCost(Scanner input, Product[] products) {
    System.out.println("digite o nome do produto que deseja mudar o custo de produção: ");
    input.nextLine();
    String name = input.nextLine();
    for (Product product : products) {
      if (product.getName().equalsIgnoreCase(name)) {
        System.out.print("digite o novo Custo de produção: ");
        try {
          double quantidade = input.nextDouble();
          if (quantidade > 0) {
            product.setCostOfProduction(quantidade);
          } else {
            System.out.println("Preço Inválido");
          }
        } catch (NumberFormatException e) {
          System.err.println("ERRO - Numero Inválido");
          System.out.println("Deseja tentar novamente? (Y para sim)");
          String choice = input.next();
          if (choice.equalsIgnoreCase("Y")) {
            setPrice(input, products);
          }
        }
      }
    }
  }

  //    metodo do menu que muda o preço de um produto especificado pelo nome.
  //    utiliza um for que percorre o vetor de produtos.
  //    O metodo somente aceita valores maiores que 0
  //    caso o usuario digite alguma letra há um tratamento de erro.

  private static void setPrice(Scanner input, Product[] products) {
    System.out.println("digite o nome do produto que deseja mudar o preço:");
    input.nextLine();
    String name = input.nextLine();
    for (Product product : products) {
      if (product.getName().equalsIgnoreCase(name)) {
        System.out.print("digite o novo preço: ");
        try {
          double quantidade = input.nextDouble();
          if (quantidade > 0) {
            product.setPrice(quantidade);
          } else {
            System.out.println("Preço Inválido");
          }
        } catch (NumberFormatException e) {
          System.err.println("ERRO - Numero Inválido");
          System.out.println("Deseja tentar novamente? (Y para sim)");
          String choice = input.next();
          if (choice.equalsIgnoreCase("Y")) {
            setPrice(input, products);
          }
        }
      }
    }
  }

  //    metodo do menu que registra as vendas de um produto especificado pelo nome
  //    utiliza um for que percorre o vetor de produtos.
  //    O metodo somente aceita valores maiores que 0 e menores ou
  //    iguais à quantidade de produtos no estoque
  //    caso o usuario digite alguma letra há um tratamento de erro.

  private static void setSales(Scanner input, Product[] products) {
    System.out.println("digite o nome do produto que deseja realizar a venda:");
    input.nextLine();
    String name = input.nextLine();
    for (Product product : products) {
      if (product.getName().equalsIgnoreCase(name)) {
        System.out.print("digite a quantidade de itens que deseja vender: ");
        try {
          int quantidade = input.nextInt();
          if (quantidade > 0 && quantidade <= product.getProductStorage()) {
            product.setDaySales(quantidade);
          } else {
            System.out.println("quantidade invalida");
          }
        } catch (NumberFormatException e) {
          System.err.println("ERRO - Numero Inválido");
          System.out.println("Deseja tentar novamente? (Y para sim)");
          String choice = input.next();
          if (choice.equalsIgnoreCase("Y")) {
            setSales(input, products);
          }
        }
      }
    }
  }

  //    metodo do menu que muda a quantidade de itens que esta
  //    no estoque de um produto especificado pelo nome.
  //    utiliza um for que percorre o vetor de produtos.
  //    O metodo somente aceita valores maiores que 0
  //    caso o usuario digite alguma letra há um tratamento de erro.

  private static void setStorage(Scanner input, Product[] products) {
    System.out.println("digite o nome do produto que deseja Mudar o estoque:");
    input.nextLine();
    String name = input.nextLine();
    for (Product product : products) {
      if (product.getName().equalsIgnoreCase(name)) {
        System.out.println("digite a quantidade de produtos que será comprada: ");
        try {
          int quantidade = input.nextInt();
          if (quantidade >= 0) {
            product.setStorage(quantidade);
          } else {
            System.out.println("quantidade Negativa, Tem certeza que "
                    + "deseja continuar? (Y para sim)");
            String choice1 = input.next();
            if (choice1.equalsIgnoreCase("Y")) {
              product.setStorage(quantidade);
            }
          }
        } catch (NumberFormatException e) {
          System.err.println("Erro - Quantidade Inválida.");
          System.out.println("deseja tentar novamente? (Y para sim)");
          String choice2 = input.next();
          if (choice2.equalsIgnoreCase("y")) {
            setStorage(input, products);
          }
        }
      }
    }
  }

  //    metodo que concatena espacamentos em uma palavra passada como parametro.
  private static String spacing(int spacing, String name) {
    String space = "";
    for (int i = 0; i < spacing; i++) {
      space =  space.concat(" ");
    }
    space = space.concat(name);
    return space;
  }
}

class Product {

  private final String name;
  private double price;
  private double costOfProduction;
  private int productStorage = 0;
  private int dayProductSell = 0;
  private double dayAllSalesofthatProduct = 0.0;
  private double profit = 0.0;

  Product(final String n, final double p, final double cost) {
    this.name = n;
    this.price = p;
    this.costOfProduction = cost;
  }

  public void setStorage(Integer productQnty) {
    this.productStorage += productQnty;
    this.profit += -(productQnty * costOfProduction);
  }

  public void setDaySales(int productQnty) {
    this.dayProductSell = productQnty;
    this.dayAllSalesofthatProduct = productQnty * this.price;
    this.productStorage -= productQnty;
    this.profit += productQnty * this.price;
  }

  public void endDay() {
    this.dayProductSell = 0;
    this.dayAllSalesofthatProduct = 0.0;
    this.profit = 0;
  }

  public void setPrice(final double price) {
    this.price = price;
  }

  public void setCostOfProduction(final double costOfProduction) {
    this.costOfProduction = costOfProduction;
  }

  public double getPrice() {
    return this.price;
  }

  public String getName() {
    return this.name;
  }

  public double getCostOfProduction() {
    return this.costOfProduction;
  }

  public double getProfit() {
    return this.profit;
  }

  public double getDayAllSalesofthatProduct() {
    return this.dayAllSalesofthatProduct;
  }

  public int getProductStorage() {
    return this.productStorage;
  }

  public int getDayProductSell() {
    return dayProductSell;
  }
}
