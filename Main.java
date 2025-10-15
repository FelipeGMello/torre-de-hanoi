import java.util.Scanner;

public class Main{

    public static int n;
    public static Pilha[] torres = new Pilha[3];
    public static int contador = 0; //Conta as jogadas
    public static boolean debug = false;

    //Apenas imprime as torres
    public static void imprime(){
        int t = 1;
        System.out.println("Jogada " + contador);
        for (Pilha torre : torres) {
            System.out.printf("Torre %d | ", t);
            for (int i = 0;i < n;i++) {
                if(torre.pilha[i] == 0 && !debug)continue;
                System.out.printf(" %d ", torre.pilha[i]);
            }
            if(debug){
                System.out.printf("quant: %d, ", torre.quantp);
                System.out.printf("topo: %d", torre.pilha[torre.topo]);
            }
            System.out.printf("\n");
            t++;
        }
        System.out.printf("\n\n");
    }
    /* Preenche as torres com os pesos dos discos e zeros que significam espaço vazio */
    public static void inicializa(Pilha[] torres){
        Scanner input = new Scanner(System.in);
        System.out.print("Digite o numero de discos: ");
        n = input.nextInt();
        System.out.println();
        for(int j=0;j<3;j++){
            torres[j] = new Pilha();
            torres[j].pilha = new int[n];
            for(int i=0; i < n;i++){
                if(j == 0){
                    torres[j].pilha[i] = n - i;
                    torres[j].quantp++;
                }
                else torres[j].pilha[i] = 0;
                
            }
            torres[j].topo = n-1;
        }
    }

    public static int jogada(Pilha atual, Pilha objetivo, Pilha auxiliar, int flag){
        int quant = 0;
        /* quantidade virtual(flag) flag = o mínimo onde atual.topo pode ir na torre objetivo ou auxiliar */
        if(flag != 0){
            int i = atual.topo;
            for(; i != -1 && flag > atual.pilha[i];i--){
                if(atual.pilha[i] != 0){
                    quant++;
                }
            }
        }
        else quant = atual.quantp;
        if(quant == 0)return 0; 

        if(quant % 2 == 0){
            // Checa se o disco no topo da atual é menor do que o da pilha auxiliar. Se for, é possível alocar
            if(atual.pilha[atual.topo] < auxiliar.pilha[auxiliar.topo] || auxiliar.pilha[auxiliar.topo] == 0){
                atual.aloca(auxiliar, n);
                contador++;
                imprime();
                /* Verificação da flag. Se o disco no topo da pilha for maior que a flag, a pilha continua sendo esvaziada.
                 * Se o disco no topo for 0(zero), então não há discos na torre, logo o processo de esvaziar para.
                 */
                if(atual.pilha[atual.topo] == 0 && flag != 0 && flag < atual.pilha[atual.topo])return 0;
                jogada(atual, objetivo, auxiliar, flag);
            }
            else{
                /* A flag é um limitador que diz quando que o esvaziamento deve parar.
                 * Na lógica, eu tiro os discos até o disco no topo da torre atual puder ser movido pra outra torre.
                 */
                int flag2 = atual.pilha[atual.topo];
                jogada(auxiliar, objetivo, atual, flag2);
                jogada(atual, objetivo, auxiliar, flag);
            }
        }
        else{ 
            // Checa se o disco no topo da atual é menor do que o da pilha objetivo. Se for, é possível alocar
            if(atual.pilha[atual.topo] < objetivo.pilha[objetivo.topo] || objetivo.pilha[objetivo.topo] == 0){
                atual.aloca(objetivo, n);
                contador++;
                imprime();
                /* Verificação da flag. Se o disco no topo da pilha for maior que a flag, a pilha continua sendo esvaziada.
                 * Se o disco no topo for 0(zero), então não há discos na torre, logo o processo de esvaziar para.
                 */
                if(atual.pilha[atual.topo] == 0 && flag != 0 && flag < atual.pilha[atual.topo])return 0;
                jogada(atual, objetivo, auxiliar, flag);
            }
            else{
                /* A flag é um limitador que diz quando que o esvaziamento deve parar.
                 * Na lógica, eu tiro os discos até o disco no topo da torre atual puder ser movido pra outra torre.
                 */
                int flag2 = atual.pilha[atual.topo];;
                jogada(objetivo, auxiliar, atual, flag2);
                jogada(atual, objetivo, auxiliar, flag);
            }
        }
        return 1;
    }
    public static void main(String[] args) {
        inicializa(torres);
        Scanner input = new Scanner(System.in);
        System.out.print("Há o modo debug para ver como o programa move os discos. Ativar? (s/n) ");
        if(input.nextLine().equals("s"))debug = true;
        imprime();        
        boolean alternador = true;
        while(torres[0].topo != 0 || torres[1].topo != 0){
            if(jogada(torres[0], torres[2], torres[1], 0) == 1 && alternador){
                alternador = false;
            }else if(jogada(torres[1], torres[2], torres[0], 0) == 1){
                alternador = true;
            }
        }
        input.close();
    }
}