import java.util.*;
import java.util.regex.*;
/**
 *
 * @author Fiacre Tsevi & Rifat Jian Lia
 */
public class MarkovModelProblem {

    public static class Grid {
        String name;
        int nbTransitions;
        float percentage;
        
        public Grid(String name, int nbTr, float percent) {
            this.name = name;
            nbTransitions = nbTr;
            percentage = percent;
        }
    }
    
    public static void main(String[] args) {
        
        int nb_pages = 0;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of pages: ");
        nb_pages = sc.nextInt(); // +2 For Quit Page and LineCount
        
        Grid[][] grid = new Grid[nb_pages + 2][nb_pages + 3];
        
        // Initialise our grid
        for(int i = 0; i <= nb_pages + 1; i++) {
            for(int j = 0; j <= nb_pages + 2; j++) {
                if(i > 0 && j==0) {
                    grid[i][j] = new Grid((i-1)+"", 0, 0f);
                }
                else if(i == 0 && j>0) {
                    grid[i][j] = new Grid((j-1)+"", 0, 0f);
                }
                else grid[i][j] = new Grid(" ", 0, 0f);
            }
        }
        grid[nb_pages + 1][0] = new Grid("q", 0, 0f);
        grid[0][nb_pages + 1] = new Grid("q", 0, 0f);
        grid[0][nb_pages + 2] = new Grid("LineCount", 0, 0f);
        
        // Printing the Grid
        printGrid(grid, nb_pages + 2, nb_pages + 3);
        
        String fromm = "/";
        String curr = "0";
        
        sc.nextLine();
        
        while(true) {
            System.out.println("Where you from: "+fromm);
            System.out.println("Where you are: "+curr);
            System.out.println("Where you go: (please enter a number 0-"+(nb_pages-1)+", q: quit, Q: Quit Program");
            System.out.print("> ");
            String inp = sc.nextLine();
            if("Q".equals(inp)) break;
            if(Pattern.compile("([0-"+(nb_pages-1)+"]|q|Q)$").matcher(inp).matches()) {
                try {
                    int int_val = ("q".equals(inp))? nb_pages + 1 : Integer.parseInt(inp) + 1;
                    //System.out.println(int_val);
                    // Processing Here!
                    // Update LineCount
                    int int_curr = Integer.parseInt(curr)+1;
                    grid[int_curr][nb_pages + 2].nbTransitions += 1;
                    // Update nb_transitions
                    grid[int_curr][int_val].nbTransitions += 1;
                    // Update percentage all cols of curr row
                    for(int i = 1; i <= nb_pages + 1; i++) {
                        grid[int_curr][i].percentage = grid[int_curr][i].nbTransitions * 100f / (grid[int_curr][nb_pages + 2].nbTransitions);
                    }
                    // Print Grid
                    printGrid(grid, nb_pages + 2, nb_pages + 3);
                    // Update strings
                    fromm = curr;
                    curr = inp;
                } catch(Exception e) {
                    System.out.println("Wrong value entered!");
                }
            }else {
                System.out.println("Wrong value entered!");
            }
            System.out.println("");
        }
    }
    
    public static void printGrid(Grid[][] grid, int N, int M) {
        for(int i = 0; i < N; i++) {
            int somme = 0;
            for(int j = 0; j < M-1; j++) {
                String str = grid[i][j].name;
                somme += grid[i][j].nbTransitions;
                if(i!=0 && j!=0) {
                    str = String.format("%2d", grid[i][j].nbTransitions) + ", " + String.format("%3.2f", grid[i][j].percentage) + "%";
                }
                if(i==0 && j==0) System.out.print(center(str, 20, false));
                else System.out.print(center(str, 20, true));
            }
            if(i==0) {
                String str = grid[i][M-1].name;
                System.out.print(center(str, 20, true));
            }
            if(i > 0) {
                String str = String.format("%3d", somme);
                System.out.print(center(str, 20, true));
            }
            System.out.println("");
        }
    }
    
    public static String center(String str, int len, boolean br) {
      if(str.length()%2!=0) str = str + " ";
      int spx = (int)(len-(str.length()+2))/2;
      return (br? "[":" ") + String.format("%"+spx+"s", "") + str + String.format("%"+spx+"s", "") + (br? "]":" ");
    }
    
}
