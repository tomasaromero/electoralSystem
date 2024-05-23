package aed;
/**Invariante: 

 _diputadosPorDistrito:     
    _diputadosPorDistrito.length == _nombresDistritos.length  
    forall i :: int : 0 <= i < _diputadosPorDistrito.length =>L 
               _diputadosPorDistrito[i] >= 0       
 
 _rangoMesasDistrito :      
    _rangoMesasDistrito.length == _nombresDistritos.length &&L     
    forall i :: int : 0 < i < _rangoMesasDistrito.length =>L      
                 _rangoMesasDistrito[i].length == 2 &&      
                 _rangoMesasDistrito[i][0] <  _rangoMesasDistrito[i][1] &&     
                 _rangoMesasDistrito[i][0] == _rangoMesasDistrito[i-1][1]     
    &&     
    _rangoMesasDistrito[0].length == 2 &&      
    _rangoMesasDistrito[0][0] <  _rangoMesasDistrito[0][1] &&     
    _rangoMesasDistrito[0][0] == _rangoMesasDistrito[1][1]         
 
 _votosPresidenciales:      
    _votosPresidenciales.length == _nombresPartidos &&      
    forall i :: int : 0 <= i < _votosPresidenciales.length =>L _votosPresidenciales[i] >= 0        

 _votosDiputados:       
    _votosDiputados.length == _nombresDistritos.length &&L     
    forall i :: int : 0 <= i < _votosDiputados.length =>L     
                _votosDiputados[i].length == _nombresPartidos.length &&L     
                forall j :: int : 0 <= j < _nombresPartidos.length =>L     
                            _votosDiputados[i][j] >= 0     

_masVotado && _segundoMasVotado:     
    0 < _masVotado, _segundoMasVotado < _nombresPartidos.length &&L     
    (No existe un idPartido defindo en _nombresPartidos que tenga     
    más votos en votosPresidenciales que el partido _masVotado) y     
    (Sólo el partido más votado tiene más votos en votosPresidenciales      
    que _segundoMasVotado) 

 _votosPresidencialesTotales:     
    es igual a la suma de todos los votos de los partidos en _votosPresidenciales        
 
 _votosDiputadosTotales:     
    _votosDiputadosTotales.length == _nombreDistritos.length &&L     
    forall idDistrito :: int : 0 <= idDistrito < _nombresDistritos =>L     
                         _votosDiputadosTotales[idDistrito] == SumatoriaDeTodosLosPartidos(_votosDiputados[idDistrito]) &&
                                 

_heapVotosDiputadosPorDistrito:     
    _heapVotosDiputadosPorDistrito.length == _nombresDistritos.length &&L     
    forall idDistrito :: int : 0 <= idDistrito < _nombresDistritos =>L 
                        _heapVotosDiputadosPorDistrito[i].length == 4 && esHeap(_heapVotosDiputadosPorDistrito[i]) &&
                        Para todo j /  esIdPartidoValido(j) =>L
                                (_heapVotosDiputadosPorDistrito[i][j][0] es un idPartido válido &&
                                no existe otro nodo k en _heapVotosDiputadosPorDistrito[i] que 
                                _heapVotosDiputadosPorDistrito[i][j][0] == _heapVotosDiputadosPorDistrito[i][k][0] ) &&
                                (_heapVotosDiputadosPorDistrito[i][j][2] es un idPartido válido &&
                                no existe otro nodo k en _heapVotosDiputadosPorDistrito[i] que 
                                _heapVotosDiputadosPorDistrito[i][j][2] == _heapVotosDiputadosPorDistrito[i][k][2] ) &&
                                _votosDiputados[idDistrito][ _heapVotosDiputadosPorDistrito[i][j][2] ] == _heapVotosDiputadosPorDistrito[i][j][3]


                
    esHeap:
    Es un heap representado en arreglo, donde para todo nodo en heap, si tiene hijos, sus hijos son menores o iguales a sus 
    padres, considerando las variables en heap[nodoHijo][1] (heap[nodo][1] >= heap[nodoHijo][1]) 
    Lo mismo pasa para las variables en heap[nodo][3].

    _resDiputadosObtenidos && _calculadoResDiputados:
    _resDiputadosObtenidos.length == _nombresDistritos.length == _calculadoResDiputados.length &&L
    forall idDistrito :: int : 0 <= idDistrito < _nombresDistritos.length =>L 
                        _resDiputadosObtenidos[idDistrito] == _nombresPartidos.length &&L
                        forall idPartidos :: int : 0 <= idPartidos < _nombresPartidos.length =>L
                                            _resDiputadosObtenidos[idDistrito][idPartido] <= _diputadosPorDistrito[idDistrito] &&
                                            (
                                                ( sumaBancasRepartidas( _resDiputadosObtenidos[idDistrito] ) == _diputadosPorDistrito[idDistrito] &&
                                                 _calculadoResDiputados[idDistrito] == true) 
                                            || 
                                                ( sumaBancasRepartidas( _resDiputadosObtenidos[idDistrito] ) == 0 && 
                                                _calculadoResDiputados[idDistrito] == false)
                                            )
                                                              
 */

public class SistemaCNE {
    private String[] _nombresPartidos;
    private String[] _nombresDistritos;
    private int[] _diputadosPorDistrito;
    private int[][] _rangoMesasDistrito; //Pair[]
    private int[] _votosPresidenciales;
    private int[][] _votosDiputados;
    private int _masVotado;
    private int _segundoMasVotado;
    private int _votosPresidencialesTotales;
    private int[] _votosDiputadosTotales;  
    private heap[] _heapVotosDiputadosPorDistrito;
    private int[][] _resDiputadosObtenidos;
    private Boolean[] _calculadoResDiputados;  


    public class VotosPartido{
        private int presidente;
        private int diputados;
        VotosPartido(int presidente, int diputados){this.presidente = presidente; this.diputados = diputados;}
        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }

    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        _nombresPartidos = nombresPartidos; //O(1)
        _nombresDistritos = nombresDistritos;//O(1)        
        _diputadosPorDistrito = diputadosPorDistrito;//O(1)
        _rangoMesasDistrito = new int[nombresDistritos.length][2];//O(2*D) in O(D)
        _rangoMesasDistrito[0][0] = 0;
        _rangoMesasDistrito[0][1] = ultimasMesasDistritos[0] ;
        for (int i = 1; i < nombresDistritos.length; i++) {//O(D)                
            int[] rango =  new int[2];
            rango[0] = ultimasMesasDistritos[i-1];
            rango[1] = ultimasMesasDistritos[i] ;
            _rangoMesasDistrito[i] = rango;
        }
        _votosPresidenciales = new int[nombresPartidos.length];//O(P)
        _votosDiputados = new int[nombresDistritos.length][nombresPartidos.length];//O(D*P)
        _masVotado = 0;
        _segundoMasVotado = 1;
        _votosPresidencialesTotales = 0;
        _votosDiputadosTotales = new int[nombresDistritos.length];//O(D)
        _heapVotosDiputadosPorDistrito = new heap[nombresDistritos.length] ;//O(D*P)// el tercero y cuarto para calcular resultadoDiputados, de nuevo <idPartido, votsDip>
     
        
        //_heapVotosDiputadosPorDistrito[idCordoba][0][2] //id del partido mas votado en Cordoba, 
        for ( int i = 0; i < nombresDistritos.length; i++) {//O(D*P)        
            heap heap = new heap(nombresPartidos.length -1);//O(P)
            _heapVotosDiputadosPorDistrito[i] = heap; //O(1)  
            for (int j = 0; j < nombresPartidos.length-1; j++) {//O(P)
                _heapVotosDiputadosPorDistrito[i].arrHeap()[j][0] = j;
                _heapVotosDiputadosPorDistrito[i].arrHeap()[j][2] = j;
            }        
        }
        _resDiputadosObtenidos = new int[_nombresDistritos.length][_nombresPartidos.length];//O(D*P)
        _calculadoResDiputados = new Boolean[_nombresDistritos.length];//O(D)
    }

    public String nombrePartido(int idPartido) {
        return _nombresPartidos[idPartido];
    }

    public String nombreDistrito(int idDistrito) {
        return _nombresDistritos[idDistrito];
    }

    public int diputadosEnDisputa(int idDistrito) {
        return _diputadosPorDistrito[idDistrito];
    }

    public String distritoDeMesa(int idMesa) {//O(log D)
        int idDistritoRes = busquedaBinaria( idMesa);//O(log D)
        return _nombresDistritos[idDistritoRes];
    }
    
    private int busquedaBinaria(int idMesa){
        //T(D) = T(D/2) + O(1) ==> O(log D) 
        int primIdDistrito = 0; 
        int ultIdDistrito  = _rangoMesasDistrito.length - 1;
        while( primIdDistrito < ultIdDistrito){
            int idDistritoMid = (primIdDistrito + ultIdDistrito)/2;
            int[] rango = new int[2];
            rango[0] = _rangoMesasDistrito[idDistritoMid][0];
            rango[1] = _rangoMesasDistrito[idDistritoMid][1];
            if( rango[1] == idMesa){
                return idDistritoMid + 1; 
            }            
            if( rango[0] <= idMesa && idMesa < rango[1] ){
                return idDistritoMid;
            }else if( rango[0] > idMesa){
                ultIdDistrito = idDistritoMid;
            }else if( rango[1] < idMesa){
                if( primIdDistrito == idDistritoMid){
                    return ultIdDistrito;
                }
                primIdDistrito = idDistritoMid;
            }
        }        
        return primIdDistrito;
    }

    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {//Complejidad : O( P + log D)
        
        int idDistrito = busquedaBinaria(idMesa); //O(log D)
        _calculadoResDiputados[idDistrito] = false;
        for(int idPartido = 0; idPartido < _nombresPartidos.length; idPartido++){//O(P)
            _votosPresidenciales[idPartido]        = _votosPresidenciales[idPartido] + actaMesa[idPartido].presidente;
            _votosDiputados[idDistrito][idPartido] = _votosDiputados[idDistrito][idPartido] + actaMesa[idPartido].diputados;
            _votosPresidencialesTotales    = _votosPresidencialesTotales + actaMesa[idPartido].presidente;
            _votosDiputadosTotales[idDistrito]      = _votosDiputadosTotales[idDistrito] + actaMesa[idPartido].diputados;    

            if(_votosPresidenciales[_masVotado] < _votosPresidenciales[idPartido]){
                 _segundoMasVotado = _masVotado;
                 _masVotado        = idPartido;                
            }else{ if( _masVotado != idPartido && _votosPresidenciales[_segundoMasVotado] < _votosPresidenciales[idPartido]){
                _segundoMasVotado = idPartido;
            }}
            _resDiputadosObtenidos[idDistrito][idPartido] = 0;            
        } 
        
        int[][] votosNombreDiputados = new int[_nombresPartidos.length-1][4];//O(P)
        for(int i = 0; i < votosNombreDiputados.length; i++){//O(P)
            votosNombreDiputados[i][0] = i;            
            votosNombreDiputados[i][2] = i;
            if(superaUmbral(i, idDistrito)){
                votosNombreDiputados[i][1] = _votosDiputados[idDistrito][i];
                votosNombreDiputados[i][3] = _votosDiputados[idDistrito][i];
            }
            
        }
        
        _heapVotosDiputadosPorDistrito[idDistrito] = _heapVotosDiputadosPorDistrito[idDistrito].buildHeap(votosNombreDiputados);//O(P)
        
    }


    public int votosPresidenciales(int idPartido) {
        return _votosPresidenciales[idPartido];
    }

    public int votosDiputados(int idPartido, int idDistrito) {
        return _votosDiputados[idDistrito][idPartido];
    }

    public int[] resultadosDiputados(int idDistrito){//O(D_d * log P)
        if( _calculadoResDiputados[idDistrito] == true){
            return _resDiputadosObtenidos[idDistrito]; 
        }
        int bancasRepartidas = 0;
        _heapVotosDiputadosPorDistrito[idDistrito].revertirHeap(_diputadosPorDistrito[idDistrito], _nombresPartidos.length);//O(D_d)
        while(bancasRepartidas < _diputadosPorDistrito[idDistrito]  ){//O( D_d ), el while O(D_d * log P)  
            int idDelPartido = _heapVotosDiputadosPorDistrito[idDistrito].arrHeap()[0][0];
            if(superaUmbral(idDelPartido, idDistrito)){//O(1)
                repartirBanca(_resDiputadosObtenidos[idDistrito], _heapVotosDiputadosPorDistrito[idDistrito], idDistrito, bancasRepartidas);//O(log P)
                bancasRepartidas ++;
            }            
        }
        _calculadoResDiputados[idDistrito] = true;

        return _resDiputadosObtenidos[idDistrito];
    }
    
    
    private void repartirBanca(int[] bancasAPartidos, heap heapVotosDiputados, int idDistrito, int bancasRepartidas){//O(log P)
        int idPartidoMasVotado = heapVotosDiputados.arrHeap()[0][0];
        if(superaUmbral(idPartidoMasVotado, idDistrito)){  //O(1)     
            bancasAPartidos[idPartidoMasVotado] += 1;
            heapVotosDiputados.arrHeap()[0][1] = _votosDiputados[idDistrito][idPartidoMasVotado]/(bancasAPartidos[idPartidoMasVotado]+1);                                
            heapVotosDiputados.ordenarHeap(0);//O(log P)               
        }
    }
    



    public boolean superaUmbral(int idPartido, int idDistrito){//O(1)
        if(_votosDiputadosTotales[idDistrito] == 0){
            return false;
        }
        int votosDelPartido = _votosDiputados[idDistrito][idPartido]; 
        int votTot = _votosDiputadosTotales[idDistrito]; 
        int porcentaje = (votosDelPartido*100)/votTot;
        return porcentaje>=3;      
    }

    public boolean hayBallotage(){//O(1)
        boolean pasaDel45 = false;
        boolean supera40DifMasIgual10 = false;
        float porcentajeMasVotado = _votosPresidenciales[_masVotado]*100/_votosPresidencialesTotales;
        float porcentajeSegundoMasVotado = _votosPresidenciales[_segundoMasVotado]*100/_votosPresidencialesTotales;
        pasaDel45 = (porcentajeMasVotado >= 45);
        supera40DifMasIgual10 = (porcentajeMasVotado >= 40) && (porcentajeMasVotado < 45) && ((porcentajeMasVotado - porcentajeSegundoMasVotado) >= 10);
        return !pasaDel45 && !supera40DifMasIgual10;
    }
}

