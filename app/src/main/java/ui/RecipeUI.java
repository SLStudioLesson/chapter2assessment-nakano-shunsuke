package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

import data.RecipeFileHandler;

public class RecipeUI {
    private BufferedReader reader;
    private RecipeFileHandler fileHandler;

    public RecipeUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        fileHandler = new RecipeFileHandler();
    }

    public RecipeUI(BufferedReader reader, RecipeFileHandler fileHandler) {
        this.reader = reader;
        this.fileHandler = fileHandler;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        // 設問1: 一覧表示機能
                        displayRecipes();
                        break;
                    case "2":
                        // 設問2: 新規登録機能
                        addNewRecipe();
                        break;
                    case "3":
                        // 設問3: 検索機能
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exit the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /**
     * 設問1: 一覧表示機能
     * RecipeFileHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     */
    private void displayRecipes() {
        ArrayList<String> lists = new ArrayList<>();
        // 料理のリスト
        lists = fileHandler.readRecipes();
        System.out.println("Recipes:");
        System.out.println("-----------------------------------");
        for (String list : lists) {
            String[] recipeMenu = list.split(",");
            System.out.println("Recipe Name: " + recipeMenu[0]);
            System.out.print("Main Ingredients: ");
            for (int i = 1; i < recipeMenu.length; i++) {
                if (i == recipeMenu.length - 1) {
                    System.out.print(recipeMenu[i]);
                } else {
                    System.out.print(recipeMenu[i] + ",");
                }
            }
            System.out.println();
            System.out.println("-----------------------------------");
        }
    }

    /**
     * 設問2: 新規登録機能
     * ユーザーからレシピ名と主な材料を入力させ、RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void addNewRecipe() throws IOException {

        System.out.println("Enter recipe name: ");
        String recipeName = reader.readLine();
        
        System.out.println("Enter main ingredients (comma separated): ");
        String ingredients = reader.readLine();

        fileHandler.addRecipe(recipeName, ingredients);
    }

    /**
     * 設問3: 検索機能
     * ユーザーから検索クエリを入力させ、そのクエリに基づいてレシピを検索し、一致するレシピをコンソールに表示します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void searchRecipe() throws IOException {
        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String query = reader.readLine();
        String name;
        String ingredient;

        // 検索クエリの書き方が正しい場合(nameかingredientのキーがある場合)
        if (query.substring(0, 4).equals("name=")) {
            // name=から&ingredient=まで取得
            name = query.substring(4, query.indexOf("&") - 1);
        }

        if (query.substring(query.indexOf("&"), query.indexOf("&") + 11).equals("&ingredient=")) {
            // &ingredient=以降を取得
            ingredient = query.substring(query.indexOf("&") + 12);
        }

        // レシピ一覧を取得して比較
        // 料理のリスト
        ArrayList<String> lists = new ArrayList<>();
        lists = fileHandler.readRecipes();
    }

}

