package aed;
//public static void buildHeap(int[][] arr) {//O(P)
//public static void heapify(int[][] arr, int n, int i) {
//public void revertirHeap(int diputadosEnDistrito, int cantPartidos){
//public void ordenarHeap(int i){//O(log P)     
public class heap {
    private int[][] heapVotosDiputados;

    public  heap(int n){
        heapVotosDiputados = new int[n][4];//O(n)
    }

    public int[][] arrHeap(){//O(1)
        return heapVotosDiputados;

    }

    public heap buildHeap(int[][] arr) {//O(P)
        //Implementamos algoritmo de Floyd para armar heap en O(P)
        int n = arr.length;
        // Comenzar desde el último nodo que tiene hijos
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        heap heap = new heap(arr.length);//O(P)
        heap.heapVotosDiputados = arr;//O(1), aliasing
        return heap;
    }

    public static void heapify(int[][] arr, int n, int i) {
        int padre = i;
        int hijoIzq = 2 * i + 1;
        int hijoDer = 2 * i + 2;

        // Comprobar si el hijo izquierdo es más grande que el nodo actual
        if (hijoIzq < n && arr[hijoIzq][3] > arr[padre][3]) {
            padre = hijoIzq;
        }
        // Comprobar si el hijo derecho es más grande que el nodo actual o el hijo izquierdo
        if (hijoDer < n && arr[hijoDer][3] > arr[padre][3]) {
            padre = hijoDer;
        }
        // Si el nodo más grande no es el nodo actual, intercambiarlos y seguir heapificando
        if (padre != i) {
            int[] temp = arr[i];
            arr[i] = arr[padre];
            arr[padre] = temp;

            heapify(arr, n, padre);
        }
    }

    private void swapParcial( int[][] votosPart, int x, int y){
        int[] antx = new int[4];
        antx[0] = votosPart[x][0];
        antx[1] = votosPart[x][1];
        antx[2] = votosPart[x][2];
        antx[3] = votosPart[x][3];
        votosPart[x][0] = votosPart[y][0];
        votosPart[x][1] = votosPart[y][1];
        votosPart[y][0] = antx[0];
        votosPart[y][1] = antx[1];               
    }

    public void revertirHeap(int diputadosEnDistrito, int cantPartidos){
        //este for es para reestablecer D_d  valores del heap a como estaba justo despues 
        //de la ultima vez que se contaron votos con registrar mesa.
        //Sólo D_d valores ya que en peor caso se reparten 1 diputado en D_d partidos.
        for(int i = 0; i < diputadosEnDistrito; i++){//O( D_d * 1 ) in O(D_d) _diputadosPorDistrito[idDistrito]
            int idPartido = i % (cantPartidos-1); 
            heapVotosDiputados[idPartido][0] = heapVotosDiputados[idPartido][2];
            heapVotosDiputados[idPartido][1] = heapVotosDiputados[idPartido][3];            
        }        

    }

    public void ordenarHeap(int i){//O(log P) 
        //En cada iteración recursiva es O(1), 
        // itera a lo sumo la altura del heap, que es a lo sumo log P.
        if( (2*i+2) < heapVotosDiputados.length){
            int izq = heapVotosDiputados[2*i+1][1];
            int der = heapVotosDiputados[2*i+2][1];
            if( heapVotosDiputados[i][1] < izq && izq > der ){
                swapParcial(heapVotosDiputados, i, 2*i+1);
                ordenarHeap(i*2+1);
            }else if( heapVotosDiputados[i][1] < der && der > izq){
                swapParcial(heapVotosDiputados, i, (i*2+2));
                ordenarHeap(i*2+2);
            }            
        }else if( (2*i+1) < heapVotosDiputados.length){
            int izq = heapVotosDiputados[2*i+1][1];            
            if( heapVotosDiputados[i][1] < izq  ){
                swapParcial(heapVotosDiputados, i, 2*i+1);
                ordenarHeap(i*2+1);
            }            
        }
        

    }
    
    
}
