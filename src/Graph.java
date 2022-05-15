import java.util.LinkedList; // импорт класса линкед лист (двусвязный список)
import java.util.Scanner; // импорт класса сканер (ввод)

public class Graph {
    // Функция, делающая праверку на входы  вершину (из каких вершин можно в неё попасть)
    // Входые данные: сам граф; массив вершин, в которые мы зашли; стартовая вершина; кол-во вершин.
    public static void dfs_inverse(int[][] g, boolean[] used, int v, int a) {
        used[v] = true;
        for (int i = 0; i < a; i++)
            if (!used[i] && g[v][i] == 1)
                dfs_inverse(g, used, i, a);
    }

    // Функция, делающая проверку на выходы из вершины (в какие вершины из неё можно попасть)
    // Входые данные: сам граф; массив вершин, в которые мы зашли; стартовая вершина; кол-во вершин.
    public static void dfs(int[][] g, boolean used[], int v, int a) {
        used[v] = true;
        for (int i = 0; i < a; i++)
            if (!used[i] && g[i][v] == 1)
                dfs(g, used, i, a);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // создаем сканер
        int a = scanner.nextInt(); // вводим размер матрицы (кол-во вершин графа)
        int[][] A = new int[a][a]; // создаем массив, куда будем задавать отношения смежности

        for (int i = 0; i < a; i++) { // заполнение матрицы смежности
            for (int j = 0; j < a; j++) {
                A[i][j] = scanner.nextInt();
            }
        }

        int B[][] = A; // делаем копию графа на всякий случай, чтоб, если вдруг, понадобилось бы вывести старый граф
        LinkedList<Integer> total[] = new LinkedList[a]; // тута мы записываем ответ
        int col = 0; // счетчик подграфов
        boolean[] spis = new boolean[a]; // массив использованных вершин
        for (int i = 0; i < a; i++) {
            if (spis[i]) continue; // проверка на использованность
            LinkedList<Integer> list = new LinkedList<Integer>(); // создаем список вершин, куда будем записывать вершины, кторые мы выделим в отдельный граф
            boolean[] used = new boolean[a]; // массив использованных вершин для двух функций
            boolean[] used_s = new boolean[a];
            dfs(B, used, i, a);
            dfs_inverse(B, used_s, i, a);

            for (int j = 0; j < a; j++) { // делаем пересечение двух массивов, чтоб определить вершины, которые запишем в список
                if (used[j] == used_s[j] && used[j]) {
                    list.add(j);
                    spis[j] = true;
                    for (int k = 0; k < a; k++) { // зануляем используемые вершины (удаляем  к ним пути и из них)
                        B[j][k] = 0;
                        B[k][j] = 0;
                    }
                }
            }

            if (!list.isEmpty()) {
                total[col++] = new LinkedList<Integer>(list); // записываем полученный подграф и присваиваем ему номер начальной вершины
            }
        }

        for (int i = 0; i < col; i++) { // вывод
            System.out.println(total[i]);
        }

    }
}
