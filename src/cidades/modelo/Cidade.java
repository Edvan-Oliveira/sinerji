package cidades.modelo;

import java.util.List;

public class Cidade {
    public final int X;
    public final int Y;
    public final String ID;
    public final List<String> vizinhos;

    public Cidade(String id, int x, int y, List<String> vizinhos) {
        this.X = x;
        this.Y = y;
        this.ID = id;
        this.vizinhos = vizinhos;
    }

    public boolean isMeuVizinho(String idVizinho) {
        return vizinhos.contains(idVizinho);
    }
}
