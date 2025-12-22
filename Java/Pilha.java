public class Pilha{
    public int quantp = 0; // quantidade real
    public int[] pilha;
    public int topo;

    public void aloca(Pilha filha, int n){
        filha.pilha[topo] = this.pilha[topo]; // Movo o disco no topo da mãe pro topo da filha
        filha.quantp++; // atualizo a quantidade de discos na filha

        int i = 0; 
        while(filha.pilha[i] != this.pilha[topo] && i != n-1){i++;} // Começando da base, eu varro a lista em busca do disco que acabou de ser movido. O índice dele será o novo topo
        filha.topo = i;
        this.quantp--; // atualizo a quantidade de discos na mãe
        this.pilha[topo] = 0; // Removo o disco movido para a filha do topo da mãe.
        for(i = n-1; i != 0;i--){ // Começando do final, eu varro a lista até encontrar um disco. Ele será o novo topo. Caso não encontre o topo vai ser o final, como padrão.
            if(this.pilha[i] != 0){
                break;
            }
        }
        this.topo = i;
        
    }
}